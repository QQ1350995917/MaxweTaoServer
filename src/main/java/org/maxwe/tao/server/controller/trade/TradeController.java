package org.maxwe.tao.server.controller.trade;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.TokenContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.utils.GrantCodeUtils;
import org.maxwe.tao.server.common.utils.PasswordUtils;
import org.maxwe.tao.server.controller.level.LevelController;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.CSEntity;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;
import org.maxwe.tao.server.service.history.HistoryEntity;
import org.maxwe.tao.server.service.level.LevelEntity;
import org.maxwe.tao.server.service.trade.ITradeServices;
import org.maxwe.tao.server.service.trade.TradeServices;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2017-01-09 18:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TradeController extends Controller implements ITradeController {
    private final Logger logger = Logger.getLogger(TradeController.class.getName());
    private IAgentServices iAgentServices = new AgentServices();
    private ITradeServices iTradeServices = new TradeServices();

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void grant() {
        String params = this.getAttr("p");
        TradeModel requestModel = JSON.parseObject(params, TradeModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("grant : 参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        CSEntity agentCS = new CSEntity(null, requestModel.getCellphone(), requestModel.getT(), requestModel.getApt());
        CSEntity csEntity = TokenContext.getCSEntity(agentCS);

        // 这里查询授权码的数量是否足够
        AgentEntity agentEntity = iAgentServices.retrieveById(csEntity.getId());
        if (agentEntity == null) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        if (!StringUtils.equals(PasswordUtils.enPassword(requestModel.getCellphone(),requestModel.getVerification()), agentEntity.getPassword())) {
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

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(UUID.randomUUID().toString());
        historyEntity.setFromId(agentEntity.getId());
        historyEntity.setType(1);
        historyEntity.setActCode(GrantCodeUtils.genGrantCode());
        historyEntity.setCodeNum(1);

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
            requestModel.setActCode(historyEntity.getActCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void trade() {
        String params = this.getAttr("p");
        TradeModel requestModel = JSON.parseObject(params, TradeModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("trade : 参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        CSEntity agentCS = new CSEntity(null, requestModel.getCellphone(), requestModel.getT(), requestModel.getApt());
        CSEntity csEntity = TokenContext.getCSEntity(agentCS);

        // 这里查询授权码的数量是否足够
        AgentEntity trunkEntity = iAgentServices.retrieveById(csEntity.getId());
        if (trunkEntity == null) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        if (trunkEntity.getLeftCodes() < requestModel.getCodeNum()) {
            this.logger.info("grant : 账户的授权数量不足 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        // 检测流向账户信息
        AgentEntity branchEntity = iAgentServices.retrieveByMark(requestModel.getTargetMark());
        if (branchEntity == null) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LevelEntity levelEntity = LevelController.levelByMinNumber(branchEntity.getHaveCodes());
        // 查询级别ID下对应的数量
        if (requestModel.getCodeNum() < levelEntity.getMinNum()) {
            this.logger.info("grant : 当前转让数量小于最小转让数量 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(UUID.randomUUID().toString());
        historyEntity.setFromId(trunkEntity.getId());
        historyEntity.setToId(branchEntity.getId());
        historyEntity.setToMark(branchEntity.getMark());
        historyEntity.setType(2);
        historyEntity.setCodeNum(requestModel.getCodeNum());
        boolean grant = iTradeServices.trade(trunkEntity, branchEntity, historyEntity);
        if (!grant) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        this.logger.info("trade : 交易成功 " + requestModel.toString());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestModel);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet);
        renderJson(resultJson);
    }
}
