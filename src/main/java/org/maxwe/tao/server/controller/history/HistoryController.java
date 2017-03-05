package org.maxwe.tao.server.controller.history;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.TokenContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.utils.DateTimeUtils;
import org.maxwe.tao.server.controller.history.model.RebateModel;
import org.maxwe.tao.server.controller.history.model.RebateRequestModel;
import org.maxwe.tao.server.controller.history.model.RebateResponseModel;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.CSEntity;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;
import org.maxwe.tao.server.service.history.HistoryEntity;
import org.maxwe.tao.server.service.history.HistoryServices;
import org.maxwe.tao.server.service.history.IHistoryServices;
import org.maxwe.tao.server.service.level.ILevelServices;
import org.maxwe.tao.server.service.level.LevelEntity;
import org.maxwe.tao.server.service.level.LevelServices;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-01-09 18:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class HistoryController extends Controller implements IHistoryController {
    private final Logger logger = Logger.getLogger(HistoryController.class.getName());
    private IHistoryServices iHistoryServices = new HistoryServices();
    private IAgentServices iAgentServices = new AgentServices();
    private ILevelServices iLevelServices = new LevelServices();

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void history() {
        String params = this.getAttr("p");
        HistoryModel requestModel = JSON.parseObject(params, HistoryModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("history : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            CSEntity csEntity = new CSEntity(0, requestModel.getCellphone(), requestModel.getT(), requestModel.getApt());
            LinkedList<HistoryEntity> historyEntities = iHistoryServices.retrieveByFromId(TokenContext.getCSEntity(csEntity).getId(), requestModel.getPageIndex(), requestModel.getPageSize());
            if (historyEntities == null || historyEntities.size() == 0) {
                iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            } else {
                iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            }
            requestModel.setHistoryEntities(historyEntities);
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void deal() {

    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void rebate() {
        String params = this.getAttr("p");
        RebateRequestModel requestModel = JSON.parseObject(params, RebateRequestModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isRebateParamsOk()) {
            this.logger.info("history : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        AgentEntity agent = iAgentServices.retrieveById(requestModel.getId());
        LevelEntity level = iLevelServices.retrieveByLevel(agent.getLevel());
        int counterByPid = iAgentServices.countByPid(requestModel.getId());
        List<Integer> sameLevelAgentIds = new LinkedList<>();
        LinkedList<AgentEntity> agentEntities = iAgentServices.retrieveByPid(requestModel.getId(), 0, counterByPid);
        for (AgentEntity agentEntity : agentEntities) {
            LevelEntity levelEntity = iLevelServices.retrieveByLevel(agentEntity.getLevel());
            if (level.equals(levelEntity)) {
                sameLevelAgentIds.add(agentEntity.getId());
            }
        }

        if (sameLevelAgentIds.size() < 1) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet);
            renderJson(resultJson);
            return;
        }

        RebateResponseModel rebateResponseModel = new RebateResponseModel();
        List<RebateModel> rebateModels = new LinkedList<>();
        for (int i = requestModel.getMonth(); i >= requestModel.getMonthCounter() - 1; i--) {
            try {
                long[] monthTimestampLine = DateTimeUtils.getMonthTimestampLine(requestModel.getYear(), i);
                int counter = iHistoryServices.countActCodeInFromIdInTime(monthTimestampLine[0], monthTimestampLine[1], sameLevelAgentIds);
                if (counter >= 0) {
                    RebateModel rebateModel = new RebateModel(requestModel.getYear(), i, counter);
                    rebateModels.add(rebateModel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rebateResponseModel.setRebates(rebateModels);

        if (rebateModels == null || rebateModels.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(rebateResponseModel);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet);
        renderJson(resultJson);
    }
}
