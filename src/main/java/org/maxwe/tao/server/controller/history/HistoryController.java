package org.maxwe.tao.server.controller.history;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.common.utils.DateTimeUtils;
import org.maxwe.tao.server.controller.account.model.TokenModel;
import org.maxwe.tao.server.controller.history.model.*;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
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
        HistoryListRequestModel requestModel = JSON.parseObject(params, HistoryListRequestModel.class);
        if (requestModel == null || !requestModel.isListRequestParamsOk()) {
            this.logger.info("history : 请求参数错误 " + requestModel.toString());
            HistoryListResponseModel listRequestModel = new HistoryListResponseModel(requestModel);
            listRequestModel.setCode(ResponseModel.RC_BAD_PARAMS);
            listRequestModel.setMessage("请求参数错误");
            renderJson(JSON.toJSONString(listRequestModel,new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            HistoryListResponseModel listRequestModel = new HistoryListResponseModel(requestModel);
            LinkedList<HistoryEntity> historyEntities = iHistoryServices.retrieveByFromId(requestModel.getId(), requestModel.getPageIndex(), requestModel.getPageSize());
            if (historyEntities == null || historyEntities.size() == 0) {
                this.logger.info("history : 查询为空 " + requestModel.toString());
                listRequestModel.setCode(ResponseModel.RC_EMPTY);
                listRequestModel.setMessage("没有数据了");
                renderJson(JSON.toJSONString(listRequestModel,new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            } else {
                this.logger.info("history : 查询数据量 " + historyEntities.size());
                listRequestModel.setCode(ResponseModel.RC_SUCCESS);
                listRequestModel.setHistories(historyEntities);
                listRequestModel.setMessage("查询成功");
                renderJson(JSON.toJSONString(listRequestModel,new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            }
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void rebate() {
        String params = this.getAttr("p");
        RebateRequestModel requestModel = JSON.parseObject(params, RebateRequestModel.class);
        if (!requestModel.isRebateParamsOk()) {
            this.logger.info("rebate : 请求参数错误 " + requestModel.toString());
            RebateResponseModel responseModel = new RebateResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(responseModel,new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
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
            this.logger.info("rebate : 没有同等级的下级 " + requestModel.toString());
            RebateResponseModel responseModel = new RebateResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_NOT_FOUND);
            responseModel.setMessage("亲，您还没有同等级的下级，要努力哟");
            renderJson(JSON.toJSONString(responseModel,new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 激活码的列表
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
                this.logger.error("rebate : 错误了 " + requestModel.toString() + " exception :" + e.getMessage());
            }
        }

        this.logger.info("rebate : 查询成功 " + requestModel.toString() + " 数据量：" + rebateModels.size());
        RebateResponseModel responseModel = new RebateResponseModel(requestModel);
        responseModel.setRebates(rebateModels);
        responseModel.setCode(ResponseModel.RC_SUCCESS);
        responseModel.setMessage("查询成功");
        renderJson(JSON.toJSONString(responseModel,new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
    }
}
