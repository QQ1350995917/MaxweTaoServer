package org.maxwe.tao.server.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.maxwe.tao.server.common.utils.RSAUtils;
import org.maxwe.tao.server.service.manager.ManagerEntity;

import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-15 15:10.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class SessionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        ManagerEntity manager = inv.getController().getSessionAttr("manager");
        if (manager == null) {
            Map<String, String> keys = RSAUtils.initKey();
            inv.getController().setSessionAttr(RSAUtils.PRIVATE_KEY, RSAUtils.getPrivateKey(keys));
            inv.getController().setSessionAttr(RSAUtils.PUBLIC_KEY, RSAUtils.getPublicKey(keys));
            inv.getController().setAttr(RSAUtils.PUBLIC_KEY, RSAUtils.getPublicKey(keys));
            inv.getController().setAttr("url", "/manager/login");
            inv.getController().render("/WEB-INF/login.html");
        } else {
            inv.invoke();
        }
    }
}
