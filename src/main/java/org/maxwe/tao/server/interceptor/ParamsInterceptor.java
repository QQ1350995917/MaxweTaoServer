package org.maxwe.tao.server.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.utils.CryptionUtils;
import sun.misc.BASE64Decoder;

/**
 * Created by Pengwei Ding on 2016-09-20 16:48.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 参数与加解密拦截器
 */
public class ParamsInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        String params = inv.getController().getPara("p");
        IResultSet iResultSet = new ResultSet();
        if (params != null) {
            try {
                byte[] decrypt = CryptionUtils.decryptDefault(CryptionUtils.parseHexStr2Byte(params));
                String content = new String(new BASE64Decoder().decodeBuffer(new String(decrypt)));
                System.out.println(content);
                inv.getController().setAttr("p", content);
                inv.invoke();
            } catch (Exception e) {
                e.printStackTrace();
                iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
                iResultSet.setMessage("参数解析错误");
                inv.getController().renderJson(iResultSet);
            }
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setMessage("hello params");
            inv.getController().renderJson(iResultSet);
        }
    }
}
