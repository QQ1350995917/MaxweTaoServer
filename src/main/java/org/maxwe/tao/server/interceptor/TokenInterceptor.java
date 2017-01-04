package org.maxwe.tao.server.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.controller.user.agent.VAgentEntity;
import org.maxwe.tao.server.service.user.CSEntity;


/**
 * Created by Pengwei Ding on 2016-09-21 18:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 会话拦截器
 */
public class TokenInterceptor implements Interceptor {
    private final Logger logger = Logger.getLogger(TokenInterceptor.class.getName());

    @Override
    public void intercept(Invocation inv) {
        String params = inv.getController().getPara("p");
        this.logger.info("TokenInterceptor -> intercept : 请求参数 " + params);
        IResultSet iResultSet = new ResultSet();
        if (StringUtils.isEmpty(params)) {
            this.logger.error("TokenInterceptor -> intercept -> " + inv.getActionKey() + " : 请求参数为空 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(params);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            inv.getController().renderJson(JSON.toJSONString(params));
            return;
        }

        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        if (StringUtils.isEmpty(requestVAgentEntity.getT())
                || StringUtils.isEmpty(requestVAgentEntity.getCellphone())
                || (requestVAgentEntity.getType() != 1 && requestVAgentEntity.getType() != 2)) {
            this.logger.error("TokenInterceptor -> intercept -> " + inv.getActionKey() + ": 请求参数不符合要求 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            inv.getController().renderJson(iResultSet);
            return;
        }

        CSEntity agentCS = new CSEntity(null, requestVAgentEntity.getCellphone(), requestVAgentEntity.getT(), requestVAgentEntity.getType());
        if (SessionContext.getCSEntity(agentCS) == null) {
            this.logger.error("TokenInterceptor -> intercept -> " + inv.getActionKey() + " : 客户端CS连接过期 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_TIMEOUT.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_TIMEOUT);
            inv.getController().renderJson(JSON.toJSONString(requestVAgentEntity));
            return;
        }

        SessionContext.getCSEntity(agentCS).resetTimestamp();

        inv.invoke();
    }
}
