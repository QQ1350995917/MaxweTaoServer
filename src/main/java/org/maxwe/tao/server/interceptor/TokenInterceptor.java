package org.maxwe.tao.server.interceptor;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.TokenContext;
import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.common.utils.IPUtils;
import org.maxwe.tao.server.controller.account.model.TokenModel;
import org.maxwe.tao.server.service.account.CSEntity;


/**
 * Created by Pengwei Ding on 2016-09-21 18:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: Token认证拦截器
 * 使用这个拦截器之前要使用AppInterceptor拦截器进行参数校验，这里不再对参数是否存在做校验
 */
public class TokenInterceptor implements Interceptor {
    private final Logger logger = Logger.getLogger(TokenInterceptor.class.getName());

    @Override
    public void intercept(Invocation inv) {
        String params = inv.getController().getAttr("p");

        TokenModel requestModel = JSON.parseObject(params, TokenModel.class);
        if (requestModel == null || !requestModel.isTokenParamsOk()) {
            this.logger.error("TokenInterceptor ->  " + inv.getActionKey() + ": 请求参数不符合TokenModel要求 " + requestModel.toString());
            ResponseModel<TokenModel> tokenModelResponseModel = new ResponseModel<>();
            tokenModelResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            tokenModelResponseModel.setMessage("参数错误，请重试");
            inv.getController().renderJson(tokenModelResponseModel);
            return;
        }

        /**
         * sign解密
         */
        try {
            if (!requestModel.isDecryptSignOK()) {
                this.logger.error("TokenInterceptor -> " + inv.getActionKey() +
                        " ; IP = " + IPUtils.getIpAddress(inv.getController().getRequest()) +
                        " : " + requestModel.toString());
                ResponseModel<TokenModel> tokenModelResponseModel = new ResponseModel<>();
                tokenModelResponseModel.setCode(ResponseModel.RC_CREATED);
                tokenModelResponseModel.setMessage("签名过期，请重试");
                inv.getController().renderJson(tokenModelResponseModel);
                return;
            }
        } catch (Exception e) {
            this.logger.error("TokenInterceptor -> " + inv.getActionKey() + " ; 异常信息：" + e.getMessage() +
                    " ; IP = " + IPUtils.getIpAddress(inv.getController().getRequest()) +
                    " : 试图破解中... " + requestModel.toString());
            ResponseModel<TokenModel> tokenModelResponseModel = new ResponseModel<>();
            tokenModelResponseModel.setCode(ResponseModel.RC_ACCEPTED);
            tokenModelResponseModel.setMessage("签名错误");
            inv.getController().renderJson(tokenModelResponseModel);
            return;
        }

        CSEntity agentCS = new CSEntity(requestModel.getCellphone(), requestModel.getT(), requestModel.getApt());
        if (TokenContext.getCSEntity(agentCS) == null) {
            this.logger.error("TokenInterceptor ->  " + inv.getActionKey() + " : 客户端CS连接过期 " + requestModel.toString());
            ResponseModel responseModel = new ResponseModel();
            responseModel.setCode(ResponseModel.RC_TIMEOUT);
            responseModel.setMessage("登录超时，请重新登录");
            inv.getController().renderJson(responseModel);
            return;
        } else {
            TokenContext.getCSEntity(agentCS).resetTimestamp();
            inv.invoke();
        }
    }
}
