package org.maxwe.tao.server.controller.trade;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.CSEntity;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;
import org.maxwe.tao.server.service.history.HistoryEntity;
import org.maxwe.tao.server.service.trade.ITradeServices;
import org.maxwe.tao.server.service.trade.TradeServices;

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
    @Before(TokenInterceptor.class)
    public void grant() {
        String params = this.getAttr("p");
        TradeModel requestModel = JSON.parseObject(params, TradeModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("trade : 参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        // TODO 这里的 mark 为空
        CSEntity agentCS = new CSEntity(null, requestModel.getCellphone(), requestModel.getT());
        CSEntity csEntity = SessionContext.getCSEntity(agentCS);

        // 这里查询授权码的数量是否足够
        AgentEntity agentEntity = iAgentServices.retrieveById(csEntity.getId());
        if (agentEntity == null) {
            this.logger.info("grant : 服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        if (agentEntity.getLeftCodes() < requestModel.getNumCode()) {
            this.logger.info("grant : 账户的授权数量不足 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        // TODO 这里输入参数
        HistoryEntity historyEntity = new HistoryEntity();
        boolean grant = iTradeServices.grant(agentEntity, historyEntity);
        if (!grant) {
            this.logger.info("grant : 服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        this.logger.info("grantToCellphone : 授权成功 " + params);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestModel);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet);
        renderJson(resultJson);
    }

    @Override
    @Before(TokenInterceptor.class)
    public void trade() {
        String params = this.getAttr("p");
        TradeModel requestModel = JSON.parseObject(params, TradeModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("trade : 参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        // TODO 这里的 mark 为空
        CSEntity agentCS = new CSEntity(null, requestModel.getCellphone(), requestModel.getT());
        CSEntity csEntity = SessionContext.getCSEntity(agentCS);

        // 这里查询授权码的数量是否足够
        AgentEntity fromEntity = iAgentServices.retrieveById(csEntity.getId());
        if (fromEntity == null) {
            this.logger.info("grant : 服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        if (fromEntity.getLeftCodes() < requestModel.getNumCode()) {
            this.logger.info("grant : 账户的授权数量不足 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        // 检测流向账户信息
        AgentEntity toEntity = iAgentServices.retrieveById(requestModel.getToAgentEntity().getId());
        if (toEntity == null) {
            // TODO 这里的error code 有些模糊
            this.logger.info("grant : 服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // TODO 这里输入参数
        HistoryEntity historyEntity = new HistoryEntity();
        boolean grant = iTradeServices.trade(fromEntity, toEntity, historyEntity);
        if (!grant) {
            this.logger.info("grant : 服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // TODO 排查返回的数据是否满足客户端使用
        this.logger.info("trade : 交易成功 " + params);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestModel);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet);
        renderJson(resultJson);
    }
}
