package org.maxwe.tao.server.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.common.utils.CryptionUtils;
import org.maxwe.tao.server.controller.account.model.TokenModel;

import java.nio.charset.Charset;

/**
 * Created by Pengwei Ding on 2016-09-20 16:48.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 参数解密拦截器
 * 对提价的参数做整体解密，解密后方能进入后续拦截和参数解析
 */
public class AppInterceptor implements Interceptor {
    private final Logger logger = Logger.getLogger(AppInterceptor.class.getName());

    @Override
    public void intercept(Invocation inv) {
        String params = inv.getController().getPara("p");
        if (params != null) {
            try {
                byte[] decrypt = CryptionUtils.decryptDefault(CryptionUtils.parseHexStr2Byte(params));
//                String content = new String(new BASE64Decoder().decodeBuffer(new String(decrypt)));
                String content = new String(Base64.decodeBase64(decrypt), Charset.forName("UTF-8"));
                inv.getController().setAttr("p", content);
                inv.invoke();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("params decrypt error : " + e.getMessage());
                ResponseModel<TokenModel> tokenModelResponseModel = new ResponseModel<>();
                tokenModelResponseModel.setCode(ResponseModel.RC_ACCEPTED);
                tokenModelResponseModel.setMessage("params decrypt error");
                inv.getController().renderJson(tokenModelResponseModel);
            }
        } else {
            logger.info("hello params");
            ResponseModel<TokenModel> tokenModelResponseModel = new ResponseModel<>();
            tokenModelResponseModel.setCode(ResponseModel.RC_NON_PARAMS);
            tokenModelResponseModel.setMessage("hello params");
            inv.getController().renderJson(tokenModelResponseModel);
        }
    }
}
