package org.maxwe.tao.server.controller.mate;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.TokenContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.utils.PasswordUtils;
import org.maxwe.tao.server.controller.mate.model.*;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.CSEntity;
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
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isBranchBegParamsOk()) {
            this.logger.info("beg : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 通过id查找上级
        AgentEntity trunkEntity = this.iAgentServices.retrieveById(requestModel.getTrunkId());
        if (trunkEntity == null) {
            this.logger.info("beg : 没有找到 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 上级也没有经过审核通过
        if (trunkEntity.getReach() != 1) {
            this.logger.info("beg : 查找到的尚未通过授权 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        CSEntity csEntity = new CSEntity(0, requestModel.getCellphone(), requestModel.getT(), requestModel.getApt());
        CSEntity existCSEntity = TokenContext.getCSEntity(csEntity);
        AgentEntity agentEntity = this.iAgentServices.retrieveByCellphone(existCSEntity.getCellphone());
        if (!StringUtils.equals(agentEntity.getPassword(), PasswordUtils.enPassword(agentEntity.getCellphone(), requestModel.getAuthenticatePassword()))) {
            this.logger.info("beg : 密码错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        AgentEntity branchAgentEntity = this.iAgentServices.retrieveById(existCSEntity.getId());
        if (branchAgentEntity.getpId() != 0) {
            this.logger.info("beg : 已经处于等待授权确认 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        branchAgentEntity.setId(existCSEntity.getId());
        branchAgentEntity.setpId(trunkEntity.getId());
        branchAgentEntity.setWechat(requestModel.getWeChat());
        boolean askForReach = iAgentServices.askForReach(branchAgentEntity);
        if (!askForReach) {
            this.logger.info("beg : 申请加入-服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            this.logger.info("beg : 查找成功 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());

            LevelEntity trunkLevelEntity = iLevelServices.retrieveByLevel(trunkEntity.getLevel());
            MateModel mateModel = new MateModel(trunkEntity, trunkLevelEntity);
            BranchBegResponseModel branchBegResponseModel = requestModel.getBranchBegResponseModel();
            branchBegResponseModel.setTrunk(mateModel);
            branchBegResponseModel.setBranch(branchAgentEntity);
            iResultSet.setData(branchBegResponseModel);

            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String string = JSON.toJSONString(iResultSet, new PropertyFilter() {
                @Override
                public boolean apply(Object object, String name, Object value) {
                    if ("password".equals(name)
                            || "status".equals(name)
                            || "named".equals(name)
                            || "weight".equals(name)
                            ) {
                        return false;
                    }
                    return true;
                }
            });
            renderJson(string);
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void grant() {
        String params = this.getAttr("p");
        GrantBranchRequestModel requestModel = JSON.parseObject(params, GrantBranchRequestModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isGrantBranchParamsOk()) {
            this.logger.info("grant : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 通过ID查找下级
        AgentEntity branchAgentEntity = this.iAgentServices.retrieveById(requestModel.getBranchId());
        if (branchAgentEntity == null) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        branchAgentEntity.setReach(1);
        boolean updateReach = iAgentServices.updateReach(branchAgentEntity);
        if (!updateReach) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            LevelEntity branchLevelEntity = iLevelServices.retrieveByLevel(branchAgentEntity.getLevel());
            MateModel mateModel = new MateModel(branchAgentEntity, branchLevelEntity);
            GrantBranchResponseModel grantBranchResponseModel = new GrantBranchResponseModel(mateModel);
            this.logger.info("grant : 授权代理加入成功 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(grantBranchResponseModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void reject() {
        String params = this.getAttr("p");
        GrantBranchRequestModel requestModel = JSON.parseObject(params, GrantBranchRequestModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isGrantBranchParamsOk()) {
            this.logger.info("reject : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 通过ID查找下级
        AgentEntity branchAgentEntity = this.iAgentServices.retrieveById(requestModel.getBranchId());
        if (branchAgentEntity == null) {
            this.logger.info("reject : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        branchAgentEntity.setReach(0);
        boolean updateReach = iAgentServices.updateReach(branchAgentEntity);
        if (!updateReach) {
            this.logger.info("reject : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            LevelEntity branchLevelEntity = iLevelServices.retrieveByLevel(branchAgentEntity.getLevel());
            MateModel mateModel = new MateModel(branchAgentEntity, branchLevelEntity);
            GrantBranchResponseModel grantBranchResponseModel = new GrantBranchResponseModel(mateModel);
            this.logger.info("reject : 拒绝代理加入成功 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(grantBranchResponseModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void leader() {
        String params = this.getAttr("p");
        TrunkInfoRequestModel requestModel = JSON.parseObject(params, TrunkInfoRequestModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isTokenParamsOk()) {
            this.logger.info("leader : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 首先查找自己的信息
        AgentEntity branchAgent = this.iAgentServices.retrieveById(requestModel.getId());
        if (branchAgent == null) {
            this.logger.info("leader : 服务器内部错误 ,找不到自己的信息 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        AgentEntity trunkAgent = this.iAgentServices.retrieveById(branchAgent.getpId());
        if (trunkAgent == null) {
            this.logger.info("leader : 服务器内部错误 ,找不到上级信息" + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        this.logger.info("leader : 查找上级成功 " + requestModel.toString());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        LevelEntity trunkLevelEntity = iLevelServices.retrieveByLevel(trunkAgent.getLevel());
        MateModel trunkMateModel = new MateModel(trunkAgent, trunkLevelEntity);
        TrunkInfoResponseModel trunkInfoResponseModel = new TrunkInfoResponseModel(trunkMateModel, branchAgent);
        iResultSet.setData(trunkInfoResponseModel);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String string = JSON.toJSONString(iResultSet, new PropertyFilter() {
            @Override
            public boolean apply(Object object, String name, Object value) {
                if ("password".equals(name)
                        || "status".equals(name)
                        || "pId".equals(name)
                        || "named".equals(name)
                        || "weight".equals(name)
                        ) {
                    return false;
                }
                return true;
            }
        });
        renderJson(string);
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void mates() {
        String params = this.getAttr("p");
        BranchesRequestModel requestModel = JSON.parseObject(params, BranchesRequestModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("query : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            LinkedList<AgentEntity> branchesAgents = iAgentServices.retrieveByPid(requestModel.getId(), requestModel.getPageIndex(), requestModel.getPageSize());

            if (branchesAgents == null || branchesAgents.size() == 0) {
                iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            } else {
                iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            }

            LinkedList<MateModel> branchesMates = new LinkedList<>();
            for (AgentEntity branchAgent : branchesAgents) {
                LevelEntity levelEntity = iLevelServices.retrieveByLevel(branchAgent.getLevel());
                branchesMates.add(new MateModel(branchAgent, levelEntity));
            }
            BranchesResponseModel branchesResponseModel = new BranchesResponseModel();
            branchesResponseModel.setBranches(branchesMates);
            iResultSet.setData(branchesResponseModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet, new PropertyFilter() {
                @Override
                public boolean apply(Object object, String name, Object value) {
                    if ("password".equals(name)
                            || "status".equals(name)
                            || "named".equals(name)
                            || "weight".equals(name)
                            ) {
                        return false;
                    }
                    return true;
                }
            });
            renderJson(resultJson);
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void mate() {
        String params = this.getAttr("p");
        BranchInfoRequestModel requestModel = JSON.parseObject(params, BranchInfoRequestModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isBranchInfoParamsOk()) {
            this.logger.info("query : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        AgentEntity branchAgent = iAgentServices.retrieveById(requestModel.getBranchId());
        if (branchAgent == null) {
            this.logger.info("leader : 服务器内部错误 ,找不到下级信息" + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }
        LevelEntity branchLevel = iLevelServices.retrieveByLevel(branchAgent.getLevel());

        BranchInfoResponseModel branchInfoResponseModel = new BranchInfoResponseModel(new MateModel(branchAgent, branchLevel));
        LinkedList<LevelEntity> levelEntities = iLevelServices.retrieveTop();
        branchInfoResponseModel.setLevels(levelEntities);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(branchInfoResponseModel);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet, new PropertyFilter() {
            @Override
            public boolean apply(Object object, String name, Object value) {
                if ("password".equals(name)
                        || "status".equals(name)
                        || "named".equals(name)
                        || "weight".equals(name)
                        ) {
                    return false;
                }
                return true;
            }
        }, SerializerFeature.DisableCircularReferenceDetect);
        renderJson(resultJson);
    }
}
