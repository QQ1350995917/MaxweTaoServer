package org.maxwe.tao.server.controller.meta;

import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-01-09 22:13.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 获取验证码的请求模型
 */
public class SMSCodeRequestModel extends TokenModel {
    public SMSCodeRequestModel() {
        super();
    }

    @JSONField(serialize = false)
    public boolean isSMSCodeRequestParamsOk(){
        return super.isCellphoneParamsOk();
    }
}
