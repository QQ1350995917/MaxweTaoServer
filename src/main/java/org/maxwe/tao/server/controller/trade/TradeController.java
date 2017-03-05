package org.maxwe.tao.server.controller.trade;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.utils.GrantCodeUtils;
import org.maxwe.tao.server.common.utils.PasswordUtils;
import org.maxwe.tao.server.controller.mate.model.MateModel;
import org.maxwe.tao.server.controller.trade.model.*;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;
import org.maxwe.tao.server.service.history.HistoryEntity;
import org.maxwe.tao.server.service.level.ILevelServices;
import org.maxwe.tao.server.service.level.LevelEntity;
import org.maxwe.tao.server.service.level.LevelServices;
import org.maxwe.tao.server.service.trade.ITradeServices;
import org.maxwe.tao.server.service.trade.TradeServices;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2017-01-09 18:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 交易控制器，包括单码授权交易和多码交易
 */
public class TradeController extends Controller implements ITradeController {
    private final Logger logger = Logger.getLogger(TradeController.class.getName());
    private IAgentServices iAgentServices = new AgentServices();
    private ITradeServices iTradeServices = new TradeServices();
    private ILevelServices iLevelServices = new LevelServices();

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void grant() {
        String params = this.getAttr("p");
        GrantRequestModel requestModel = JSON.parseObject(params, GrantRequestModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isTokenParamsOk()) {
            this.logger.info("grant : 参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        // 这里查询授权码的数量是否足够
        AgentEntity agentEntity = iAgentServices.retrieveById(requestModel.getId());
        if (agentEntity == null) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        if (!StringUtils.equals(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getAuthenticatePassword()), agentEntity.getPassword())) {
            this.logger.info("grant : 认证密码错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        if (agentEntity.getReach() != 1) {
            this.logger.info("grant : 该账户尚未激活不能进行授权 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        if (agentEntity.getLeftCodes() < 1) {
            this.logger.info("grant : 账户的授权数量不足 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

//        LevelEntity levelEntity = iLevelServices.retrieveByLevel(5);
//        if (levelEntity == null) {
//            levelEntity = new LevelEntity("单码", 1, 0f, 5, 0);
//            this.logger.info("grant : 服务器内部错误-没有找到单个码的价格 " + requestModel.toString());
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestModel);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet));
//            return;
//        }

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(UUID.randomUUID().toString());
        historyEntity.setFromId(agentEntity.getId());
        historyEntity.setType(1);
        historyEntity.setActCode(GrantCodeUtils.genGrantCode());
        historyEntity.setCodeNum(1);
        historyEntity.setCodeDeal(1 * 0);

        boolean grant = iTradeServices.grant(agentEntity, historyEntity);
        if (!grant) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            this.logger.info("grant : 授权成功 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            GrantResponseModel grantResponseModel = new GrantResponseModel(historyEntity.getActCode(), historyEntity.getCodeNum(), 0);
            iResultSet.setData(grantResponseModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void trade() {
        String params = this.getAttr("p");
        TradeRequestModel requestModel = JSON.parseObject(params, TradeRequestModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isTradeRequestParamsOk()) {
            this.logger.info("trade : 参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        // 这里查询授权码的数量是否足够
        AgentEntity trunkAgent = iAgentServices.retrieveById(requestModel.getId());
        if (trunkAgent == null) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        if (!StringUtils.equals(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getAuthenticatePassword()), trunkAgent.getPassword())) {
            this.logger.info("grant : 认证密码错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        if (trunkAgent.getLeftCodes() < requestModel.getCodeNum()) {
            this.logger.info("grant : 账户的授权数量不足 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        // 检测流向账户信息
        AgentEntity branchAgent = iAgentServices.retrieveById(requestModel.getBranchAgent().getAgent().getId());
        if (branchAgent == null) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LevelEntity branchLevel = iLevelServices.retrieveById(requestModel.getBranchAgent().getLevel().getId());
        // 查询级别ID下对应的数量
        if (branchLevel == null) {
            this.logger.info("grant : 找不到级别的ID " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        if (branchAgent.getHaveCodes() < 1) {
            if (requestModel.getCodeNum() < branchLevel.getMinNum()) {
                this.logger.info("grant : 当前转让数量小于最小转让数量 " + requestModel.toString());
                iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
                iResultSet.setData(requestModel);
                iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
                String resultJson = JSON.toJSONString(iResultSet);
                renderJson(resultJson);
                return;
            }
        } else {
            if (requestModel.getCodeNum() < 1) {
                this.logger.info("grant : 当前转让数量小于0 " + requestModel.toString());
                iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
                iResultSet.setData(requestModel);
                iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
                String resultJson = JSON.toJSONString(iResultSet);
                renderJson(resultJson);
                return;
            }
        }

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(UUID.randomUUID().toString());
        historyEntity.setFromId(trunkAgent.getId());
        historyEntity.setToId(branchAgent.getId());
        historyEntity.setType(2);
        historyEntity.setCodeNum(requestModel.getCodeNum());
        historyEntity.setCodeDeal(requestModel.getCodeNum() * branchLevel.getPrice());
        branchAgent.setLevel(branchLevel.getLevel());
        boolean grant = iTradeServices.trade(trunkAgent, branchAgent, historyEntity);
        if (!grant) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        this.logger.info("trade : 交易成功 " + requestModel.toString());
        TradeResponseModel tradeResponseModel = new TradeResponseModel();
        tradeResponseModel.setBranchAgent(new MateModel(branchAgent, branchLevel));
        tradeResponseModel.setCodeNum(requestModel.getCodeNum());
        tradeResponseModel.setPrice(branchLevel.getPrice());
        tradeResponseModel.setCodeDeal(requestModel.getCodeNum() * branchLevel.getPrice());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(tradeResponseModel);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet);
        renderJson(resultJson);
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void upgrade() {
        String params = this.getAttr("p");
        UpgradeRequestModel requestModel = JSON.parseObject(params, UpgradeRequestModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isUpgradeRequestParamsOk()) {
            this.logger.info("update : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 这里查询授权码的数量是否足够
        AgentEntity trunkAgent = iAgentServices.retrieveById(requestModel.getId());
        if (trunkAgent == null) {
            this.logger.info("update : 服务器内部错误 找不到主干账户" + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        if (!StringUtils.equals(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getAuthenticatePassword()), trunkAgent.getPassword())) {
            this.logger.info("update : 认证密码错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        if (trunkAgent.getLeftCodes() < requestModel.getCodeNum()) {
            this.logger.info("update : 账户的授权数量不足 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        // 检测流向账户信息
        AgentEntity branchAgent = iAgentServices.retrieveById(requestModel.getBranch().getAgent().getId());
        if (branchAgent == null) {
            this.logger.info("update : 服务器内部错误 找不到分支账户" + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LevelEntity branchLevel = iLevelServices.retrieveById(requestModel.getBranch().getLevel().getId());
        // 查询分支级别ID下对应的数量
        if (branchLevel == null) {
            this.logger.info("update : 找不到分支账户对应级别的ID " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LevelEntity upgradeToLevel = iLevelServices.retrieveById(requestModel.getUpgradeToLevel().getId());
        if (upgradeToLevel == null) {
            this.logger.info("update : 服务器内部错误 找不到要升级到的级别" + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        if (upgradeToLevel.getLevel() > requestModel.getBranch().getLevel().getLevel()){
            this.logger.info("update : 不能给代理降级 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        if (branchLevel.getMinNum() + requestModel.getCodeNum() < upgradeToLevel.getMinNum()) {
            if (requestModel.getCodeNum() < branchLevel.getMinNum()) {
                this.logger.info("update : 当前转让数量不足升级 " + requestModel.toString());
                iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
                iResultSet.setData(requestModel);
                iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
                String resultJson = JSON.toJSONString(iResultSet);
                renderJson(resultJson);
                return;
            }
        } else {
            if (requestModel.getCodeNum() < 1) {
                this.logger.info("update : 当前转让数量小于0 " + requestModel.toString());
                iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
                iResultSet.setData(requestModel);
                iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
                String resultJson = JSON.toJSONString(iResultSet);
                renderJson(resultJson);
                return;
            }
        }

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(UUID.randomUUID().toString());
        historyEntity.setFromId(trunkAgent.getId());
        historyEntity.setToId(branchAgent.getId());
        historyEntity.setType(2);
        historyEntity.setCodeNum(requestModel.getCodeNum());
        historyEntity.setCodeDeal(requestModel.getCodeNum() * branchLevel.getPrice());
        branchAgent.setLevel(upgradeToLevel.getLevel());
        boolean grant = iTradeServices.trade(trunkAgent, branchAgent, historyEntity);
        if (!grant) {
            this.logger.info("update : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        this.logger.info("update : 升级成功 " + requestModel.toString());
        UpgradeResponseModel upgradeResponseModel = new UpgradeResponseModel();
        upgradeResponseModel.setBranch(new MateModel(branchAgent, branchLevel));
        upgradeResponseModel.setCodeNum(requestModel.getCodeNum());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(upgradeResponseModel);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet);
        renderJson(resultJson);


    }
}
