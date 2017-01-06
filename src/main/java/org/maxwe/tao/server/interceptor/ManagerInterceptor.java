package org.maxwe.tao.server.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * Created by Pengwei Ding on 2016-10-24 14:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 区分是管理员的Session对象
 */
public class ManagerInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
//        String params = inv.getController().getPara("p");
//        Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);
//        String cs = paramsMap.get("cs").toString();
//        IResultSet iResultSet = new ResultSet();
//        if (SessionContext.getSession(cs) instanceof VManagerEntity) {
//            iResultSet.setGrantCode(IResultSet.ResultCode.RC_ACCESS_BAD.getGrantCode());
//            iResultSet.setData(paramsMap);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
//            inv.getController().renderJson(JSON.toJSONString(iResultSet));
//            return;
//        }
        inv.invoke();
    }
}
