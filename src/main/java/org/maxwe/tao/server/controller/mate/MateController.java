package org.maxwe.tao.server.controller.mate;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.common.utils.PasswordUtils;
import org.maxwe.tao.server.controller.account.model.TokenModel;
import org.maxwe.tao.server.controller.mate.model.*;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;
import org.maxwe.tao.server.service.level.ILevelServices;
import org.maxwe.tao.server.service.level.LevelEntity;
import org.maxwe.tao.server.service.level.LevelServices;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-09 18:52.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理之间的关系
 */
public class MateController extends Controller implements IMateController {
    private final Logger logger = Logger.getLogger(MateController.class.getName());
    private IAgentServices iAgentServices = new AgentServices();
    private ILevelServices iLevelServices = new LevelServices();

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void beg() {
        String params = this.getAttr("p");
        BranchBegRequestModel requestModel = JSON.parseObject(params, BranchBegRequestModel.class);
        if (!requestModel.isBranchBegParamsOk()) {
            this.logger.info("beg : 请求参数错误 " + requestModel.toString());
            BranchBegResponseModel responseModel = new BranchBegResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 通过id查找上级
        AgentEntity trunkEntity = this.iAgentServices.retrieveById(requestModel.getTrunkId());
        if (trunkEntity == null) {
            this.logger.info("beg : 没有找到 " + requestModel.toString());
            BranchBegResponseModel responseModel = new BranchBegResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_NOT_FOUND);
            responseModel.setMessage("该上级不存在，请确认ID");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 上级也没有经过审核通过
        if (trunkEntity.getReach() != 1) {
            this.logger.info("beg : 查找到的上级尚未通过授权 " + requestModel.toString());
            BranchBegResponseModel responseModel = new BranchBegResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_FORBIDDEN);
            responseModel.setMessage("您的上级不具备对您的授权权限");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        AgentEntity agentEntity = this.iAgentServices.retrieveByCellphone(requestModel.getCellphone());
        if (!StringUtils.equals(agentEntity.getPassword(), PasswordUtils.enPassword(agentEntity.getCellphone(), requestModel.getAuthenticatePassword()))) {
            this.logger.info("beg : 密码错误 " + requestModel.toString());
            BranchBegResponseModel responseModel = new BranchBegResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_CONFLICT);
            responseModel.setMessage("密码错误");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        AgentEntity branchAgentEntity = this.iAgentServices.retrieveById(requestModel.getId());
        if (branchAgentEntity.getpId() != 0) {
            this.logger.info("beg : 已经处于等待授权确认 " + requestModel.toString());
            BranchBegResponseModel responseModel = new BranchBegResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_NOT_ACCEPTABLE);
            responseModel.setMessage("您已经处于待授权确认状态");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        branchAgentEntity.setpId(trunkEntity.getId());
        branchAgentEntity.setWechat(requestModel.getWeChat());
        boolean askForReach = iAgentServices.askForReach(branchAgentEntity);
        if (!askForReach) {
            this.logger.info("beg : 申请加入-服务器内部错误 " + requestModel.toString());
            BranchBegResponseModel responseModel = new BranchBegResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            responseModel.setMessage("发生错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        } else {
            this.logger.info("beg : 申请成功 " + requestModel.toString());
            LevelEntity trunkLevelEntity = iLevelServices.retrieveByLevel(trunkEntity.getLevel());
            MateModel trunkMateModel = new MateModel(trunkEntity, trunkLevelEntity);
            BranchBegResponseModel branchBegResponseModel = new BranchBegResponseModel(requestModel);
            branchBegResponseModel.setTrunk(trunkMateModel);
            branchBegResponseModel.setBranch(branchAgentEntity);
            branchBegResponseModel.setCode(ResponseModel.RC_SUCCESS);
            branchBegResponseModel.setMessage("申请成功");
            renderJson(JSON.toJSONString(branchBegResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void grant() {
        String params = this.getAttr("p");
        GrantBranchRequestModel requestModel = JSON.parseObject(params, GrantBranchRequestModel.class);
        if (!requestModel.isGrantBranchParamsOk()) {
            this.logger.info("grant : 请求参数错误 " + requestModel.toString());
            GrantBranchResponseModel grantBranchResponseModel = new GrantBranchResponseModel(requestModel);
            grantBranchResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            grantBranchResponseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(grantBranchResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 通过ID查找下级
        AgentEntity branchAgentEntity = this.iAgentServices.retrieveById(requestModel.getBranchId());
        if (branchAgentEntity == null) {
            this.logger.info("grant : 没找到该ID " + requestModel.toString());
            GrantBranchResponseModel grantBranchResponseModel = new GrantBranchResponseModel(requestModel);
            grantBranchResponseModel.setCode(ResponseModel.RC_NOT_FOUND);
            grantBranchResponseModel.setMessage("没找到该ID，请重试");
            renderJson(JSON.toJSONString(grantBranchResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (branchAgentEntity.getReach() == 1) {
            this.logger.info("grant : 不要重复授权 " + requestModel.toString());
            GrantBranchResponseModel grantBranchResponseModel = new GrantBranchResponseModel(requestModel);
            grantBranchResponseModel.setCode(ResponseModel.RC_NOT_ACCEPTABLE);
            grantBranchResponseModel.setMessage("不要重复授权哦");
            renderJson(JSON.toJSONString(grantBranchResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        branchAgentEntity.setReach(1);
        boolean updateReach = iAgentServices.updateReach(branchAgentEntity);
        if (!updateReach) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            GrantBranchResponseModel grantBranchResponseModel = new GrantBranchResponseModel(requestModel);
            grantBranchResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            grantBranchResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(grantBranchResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        } else {
            this.logger.info("grant : 授权代理加入成功 " + requestModel.toString());
            LevelEntity branchLevelEntity = iLevelServices.retrieveByLevel(branchAgentEntity.getLevel());
            MateModel branchMateModel = new MateModel(branchAgentEntity, branchLevelEntity);
            GrantBranchResponseModel grantBranchResponseModel = new GrantBranchResponseModel(requestModel);
            grantBranchResponseModel.setCode(ResponseModel.RC_SUCCESS);
            grantBranchResponseModel.setBranch(branchMateModel);
            grantBranchResponseModel.setMessage("授权代理加入成功");
            renderJson(JSON.toJSONString(grantBranchResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void reject() {
//        String params = this.getAttr("p");
//        GrantBranchRequestModel requestModel = JSON.parseObject(params, GrantBranchRequestModel.class);
//        IResultSet iResultSet = new ResultSet();
//        if (!requestModel.isGrantBranchParamsOk()) {
//            this.logger.info("reject : 请求参数错误 " + requestModel.toString());
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
//            iResultSet.setData(requestModel);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
//            renderJson(JSON.toJSONString(iResultSet));
//            return;
//        }
//
//        // 通过ID查找下级
//        AgentEntity branchAgentEntity = this.iAgentServices.retrieveById(requestModel.getBranchId());
//        if (branchAgentEntity == null) {
//            this.logger.info("reject : 服务器内部错误 " + requestModel.toString());
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestModel);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet));
//            return;
//        }
//
//        branchAgentEntity.setReach(0);
//        boolean updateReach = iAgentServices.updateReach(branchAgentEntity);
//        if (!updateReach) {
//            this.logger.info("reject : 服务器内部错误 " + requestModel.toString());
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestModel);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet));
//        } else {
//            LevelEntity branchLevelEntity = iLevelServices.retrieveByLevel(branchAgentEntity.getLevel());
//            MateModel mateModel = new MateModel(branchAgentEntity, branchLevelEntity);
//            GrantBranchResponseModel grantBranchResponseModel = new GrantBranchResponseModel(mateModel);
//            this.logger.info("reject : 拒绝代理加入成功 " + requestModel.toString());
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//            iResultSet.setData(grantBranchResponseModel);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//            renderJson(JSON.toJSONString(iResultSet));
//        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void leader() {
        String params = this.getAttr("p");
        TrunkInfoRequestModel requestModel = JSON.parseObject(params, TrunkInfoRequestModel.class);
        if (!requestModel.isTokenParamsOk()) {
            this.logger.info("leader : 请求参数错误 " + requestModel.toString());
            TrunkInfoResponseModel trunkInfoResponseModel = new TrunkInfoResponseModel(requestModel);
            trunkInfoResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            trunkInfoResponseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(trunkInfoResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 首先查找自己的信息
        AgentEntity branchAgent = this.iAgentServices.retrieveById(requestModel.getId());
        if (branchAgent == null) {
            this.logger.info("leader : 服务器内部错误 ,找不到自己的信息 " + requestModel.toString());
            TrunkInfoResponseModel trunkInfoResponseModel = new TrunkInfoResponseModel(requestModel);
            trunkInfoResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            trunkInfoResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(trunkInfoResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        AgentEntity trunkAgent = this.iAgentServices.retrieveById(branchAgent.getpId());
        if (trunkAgent == null) {
            this.logger.info("leader : 服务器内部错误 ,找不到上级信息" + requestModel.toString());
            TrunkInfoResponseModel trunkInfoResponseModel = new TrunkInfoResponseModel(requestModel);
            trunkInfoResponseModel.setCode(ResponseModel.RC_NOT_FOUND);
            trunkInfoResponseModel.setMessage("找不到上级");
            renderJson(JSON.toJSONString(trunkInfoResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect));
            return;
        }

        this.logger.info("leader : 查找上级成功 " + requestModel.toString());
        LevelEntity trunkLevelEntity = iLevelServices.retrieveByLevel(trunkAgent.getLevel());
        MateModel trunkMateModel = new MateModel(trunkAgent, trunkLevelEntity);
        TrunkInfoResponseModel trunkInfoResponseModel = new TrunkInfoResponseModel(requestModel, trunkMateModel, branchAgent);
        trunkInfoResponseModel.setCode(ResponseModel.RC_SUCCESS);
        trunkInfoResponseModel.setTrunk(trunkMateModel);
        trunkInfoResponseModel.setMessage("查找成功");
        renderJson(JSON.toJSONString(trunkInfoResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect));
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void mates() {
        String params = this.getAttr("p");
        BranchesRequestModel requestModel = JSON.parseObject(params, BranchesRequestModel.class);
        if (!requestModel.isParamsOk()) {
            this.logger.info("mates : 请求参数错误 " + requestModel.toString());
            BranchesResponseModel branchesResponseModel = new BranchesResponseModel(requestModel);
            branchesResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            branchesResponseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(branchesResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            LinkedList<AgentEntity> branchesAgents = iAgentServices.retrieveByPid(requestModel.getId(), requestModel.getPageIndex(), requestModel.getPageSize());
            BranchesResponseModel branchesResponseModel = new BranchesResponseModel(requestModel);
            if (branchesAgents == null || branchesAgents.size() == 0) {
                branchesResponseModel.setCode(ResponseModel.RC_EMPTY);
                branchesResponseModel.setMessage("没有数据");
            } else {
                int countByPid = iAgentServices.countByPid(requestModel.getId());
                branchesResponseModel.setCode(ResponseModel.RC_SUCCESS);
                branchesResponseModel.setMessage("查询成功");
                branchesResponseModel.setTotal(countByPid);
            }

            LinkedList<MateModel> branchesMates = new LinkedList<>();
            for (AgentEntity branchAgent : branchesAgents) {
                LevelEntity levelEntity = iLevelServices.retrieveByLevel(branchAgent.getLevel());
                branchesMates.add(new MateModel(branchAgent, levelEntity));
            }
            branchesResponseModel.setBranches(branchesMates);
            renderJson(JSON.toJSONString(branchesResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void mate() {
        String params = this.getAttr("p");
        BranchInfoRequestModel requestModel = JSON.parseObject(params, BranchInfoRequestModel.class);
        if (!requestModel.isBranchInfoParamsOk()) {
            this.logger.info("query : 请求参数错误 " + requestModel.toString());
            BranchInfoResponseModel branchInfoResponseModel = new BranchInfoResponseModel(requestModel);
            branchInfoResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            branchInfoResponseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(branchInfoResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect));
            return;
        }

        AgentEntity branchAgent = iAgentServices.retrieveById(requestModel.getBranchId());
        if (branchAgent == null) {
            this.logger.info("leader : 服务器内部错误 ,找不到下级信息" + requestModel.toString());
            BranchInfoResponseModel branchInfoResponseModel = new BranchInfoResponseModel(requestModel);
            branchInfoResponseModel.setCode(ResponseModel.RC_NOT_FOUND);
            branchInfoResponseModel.setMessage("找不到该ID，请重试");
            renderJson(JSON.toJSONString(branchInfoResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect));
            return;
        }
        LevelEntity branchLevel = iLevelServices.retrieveByLevel(branchAgent.getLevel());
        LinkedList<LevelEntity> levelEntities = iLevelServices.retrieveTop();
        BranchInfoResponseModel branchInfoResponseModel = new BranchInfoResponseModel(requestModel, new MateModel(branchAgent, branchLevel), levelEntities);
        branchInfoResponseModel.setCode(ResponseModel.RC_SUCCESS);
        branchInfoResponseModel.setMessage("查找成功");
        renderJson(JSON.toJSONString(branchInfoResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect));
    }
}
