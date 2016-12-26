package org.maxwe.tao.server.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * Created by Pengwei Ding on 2016-10-17 17:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员权限拦截
 */
public class MenuInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
//        String actionKey = inv.getActionKey();
//        String params = inv.getController().getPara("p");
//        Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);
//        String cs = paramsMap.get("cs").toString();
//        VManagerEntity vManagerEntity = SessionContext.getSessionManager(cs);//菜单拦截器是在执行完session拦截器和manager拦截器后执行的，参数已经得到保证
//        LinkedList<String> actionKeys = vManagerEntity.getActionKeys();
//        if (actionKeys.contains(actionKey)) {
//            inv.invoke();
//            return;
//        }
//
//        IResultSet iResultSet = new ResultSet();
//        iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
//        iResultSet.setData(paramsMap);
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
//        inv.getController().renderJson(iResultSet);

        inv.invoke();

    }
}
