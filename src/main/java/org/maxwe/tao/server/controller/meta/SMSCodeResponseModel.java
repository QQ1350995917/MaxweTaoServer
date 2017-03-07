package org.maxwe.tao.server.controller.meta;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-07 17:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 获取验证码的响应模型
 */
public class SMSCodeResponseModel extends ResponseModel<SMSCodeRequestModel> {
    public SMSCodeResponseModel() {
        super();
    }

    public SMSCodeResponseModel(SMSCodeRequestModel requestModel) {
        super(requestModel);
    }

}
