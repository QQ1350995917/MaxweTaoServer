package org.maxwe.tao.server.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.controller.account.model.SessionModel;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.service.account.CSEntity;


/**
 * Created by Pengwei Ding on 2016-09-21 18:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 会话拦截器
 */
public class TokenInterceptor implements Interceptor {
    private final Logger logger = Logger.getLogger(TokenInterceptor.class.getName());

    @Override
    public void intercept(Invocation inv) {
        String params = inv.getController().getAttr("p");
        IResultSet iResultSet = new ResultSet();
        if (StringUtils.isEmpty(params)) {
            this.logger.error("TokenInterceptor ->  " + inv.getActionKey() + " : 请求参数为空");
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(params);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            inv.getController().renderJson(iResultSet);
            return;
        }

        SessionModel requestModel = JSON.parseObject(params, SessionModel.class);
        if (requestModel == null || !requestModel.isParamsOk()) {
            this.logger.error("TokenInterceptor ->  " + inv.getActionKey() + ": 请求参数不符合要求 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            inv.getController().renderJson(iResultSet);
            return;
        }

        /**
         * sign解密
         */
        try {
            if (!requestModel.isDecryptSignOK()) {
                this.logger.error("TokenInterceptor -> " + inv.getActionKey() + " : 试图破解中... " + requestModel.toString());
                iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR_AAA.getCode());
                iResultSet.setData(requestModel);
                inv.getController().renderJson(iResultSet);
                return;
            }
        } catch (Exception e) {
            this.logger.error("TokenInterceptor -> " + inv.getActionKey() + " : 试图破解中... " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR_AAA.getCode());
            iResultSet.setData(requestModel);
            inv.getController().renderJson(iResultSet);
            return;
        }

        CSEntity agentCS = new CSEntity(null, requestModel.getCellphone(), requestModel.getT());
        if (SessionContext.getCSEntity(agentCS) == null) {
            this.logger.error("TokenInterceptor ->  " + inv.getActionKey() + " : 客户端CS连接过期 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_TIMEOUT.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_TIMEOUT);
            inv.getController().renderJson(iResultSet);
            return;
        }

        SessionContext.getCSEntity(agentCS).resetTimestamp();

        inv.invoke();
    }
}
