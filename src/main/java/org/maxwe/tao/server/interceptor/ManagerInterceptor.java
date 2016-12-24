package org.maxwe.tao.server.interceptor;

import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.controller.manager.VManagerEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-10-24 14:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 区分是管理员的Session对象
 */
public class ManagerInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        String params = inv.getController().getPara("p");
        Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);
        String cs = paramsMap.get("cs").toString();
        IResultSet iResultSet = new ResultSet();
        if (SessionContext.getSession(cs) instanceof VManagerEntity) {
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(paramsMap);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            inv.getController().renderJson(JSON.toJSONString(iResultSet));
            return;
        }
        inv.invoke();
    }
}
