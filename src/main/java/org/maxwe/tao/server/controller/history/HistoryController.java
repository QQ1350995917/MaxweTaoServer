package org.maxwe.tao.server.controller.history;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.CSEntity;
import org.maxwe.tao.server.service.history.HistoryEntity;
import org.maxwe.tao.server.service.history.HistoryServices;
import org.maxwe.tao.server.service.history.IHistoryServices;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-09 18:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class HistoryController extends Controller implements IHistoryController {
    private final Logger logger = Logger.getLogger(HistoryController.class.getName());
    private IHistoryServices iHistoryServices = new HistoryServices();

    @Override
    @Before(TokenInterceptor.class)
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
            CSEntity csEntity = new CSEntity(null, requestModel.getCellphone(), requestModel.getT(),requestModel.getApt());
            LinkedList<HistoryEntity> historyEntities = iHistoryServices.retrieveByFromId(SessionContext.getCSEntity(csEntity).getId(), requestModel.getPageIndex(), requestModel.getPageSize());
            if (historyEntities == null || historyEntities.size() == 0) {
                iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            } else {
                iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            }
            requestModel.setHistoryEntities(historyEntities);
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet, new PropertyFilter() {
                @Override
                public boolean apply(Object object, String name, Object value) {
                    if ("id".equals(name) || "fromId".equals(name)|| "toId".equals(name)) {
                        return false;
                    }
                    return true;
                }
            });
            renderJson(resultJson);
        }
    }
}
