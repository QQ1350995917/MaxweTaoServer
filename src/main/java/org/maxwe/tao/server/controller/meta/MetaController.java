package org.maxwe.tao.server.controller.meta;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.taobao.api.ApiException;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.common.sms.SMSManager;
import org.maxwe.tao.server.common.utils.IPUtils;
import org.maxwe.tao.server.interceptor.AppInterceptor;

/**
 * Created by Pengwei Ding on 2017-01-09 22:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class MetaController extends Controller implements IMetaController {
    private final Logger logger = Logger.getLogger(MetaController.class.getName());

    @Override
    @Before({AppInterceptor.class})
    public void smsCode() {
        String params = this.getAttr("p");
        SMSCodeRequestModel requestModel = JSON.parseObject(params, SMSCodeRequestModel.class);
        if (requestModel == null || !requestModel.isSMSCodeRequestParamsOk()) {
            this.logger.info("smsCode : 请求参数错误 " + params);
            SMSCodeResponseModel smsCodeResponseModel = new SMSCodeResponseModel(requestModel);
            smsCodeResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            smsCodeResponseModel.setMessage("请输入正确的手机号码");
        }

        if (SMSManager.isCacheAddress(IPUtils.getIpAddress(this.getRequest()))) {
            this.logger.info("smsCode : 请求频繁 " + params);
            SMSCodeResponseModel smsCodeResponseModel = new SMSCodeResponseModel(requestModel);
            smsCodeResponseModel.setCode(ResponseModel.RC_TO_MANY);
            smsCodeResponseModel.setMessage("网络忙，请稍后");
            return;
        }

        try {
            SMSManager.sendSMS(requestModel.getCellphone());
            this.logger.info("smsCode : 验证码" + SMSManager.getSMSCode(requestModel.getCellphone()) + "已经发送 " + params);
            SMSCodeResponseModel smsCodeResponseModel = new SMSCodeResponseModel(requestModel);
            smsCodeResponseModel.setCode(ResponseModel.RC_SUCCESS);
            smsCodeResponseModel.setMessage("发送成功");
        } catch (ApiException e) {
            this.logger.error("smsCode : 验证码发送错误 " + params + "\r\n" + e.getMessage());
            e.printStackTrace();
            SMSCodeResponseModel smsCodeResponseModel = new SMSCodeResponseModel(requestModel);
            smsCodeResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            smsCodeResponseModel.setMessage("发送失败");
        }


    }
}
