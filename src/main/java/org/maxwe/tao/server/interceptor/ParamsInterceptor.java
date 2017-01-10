package org.maxwe.tao.server.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.maxwe.tao.server.common.utils.CryptionUtils;

import java.util.Base64;

/**
 * Created by Pengwei Ding on 2016-09-20 16:48.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 参数与加解密拦截器
 */
public class ParamsInterceptor implements Interceptor {

    private static final String password = "PollKingTueJan101420";

    @Override
    public void intercept(Invocation inv) {
        String params = inv.getController().getPara("p");
        if (params != null) {
            byte[] decrypt = CryptionUtils.decrypt(params.getBytes(), password);
            byte[] decode = Base64.getDecoder().decode(decrypt);
            String content = new String(decode);
            inv.getController().setAttr("p", content);
            inv.invoke();
        } else {
            inv.getController().renderJson("hello params");
        }
    }
}
