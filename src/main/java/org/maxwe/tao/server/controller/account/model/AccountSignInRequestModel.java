package org.maxwe.tao.server.controller.account.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.common.utils.PasswordUtils;

/**
 * Created by Pengwei Ding on 2017-03-07 13:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 登录请求模型
 */
public class AccountSignInRequestModel extends TokenModel {
    private String password;

    public AccountSignInRequestModel() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JSONField(serialize=false)
    public boolean isAccountSignInParamsOk() {
        if (!super.isCellphoneParamsOk()) {
            return false;
        }
        if (!PasswordUtils.isPlainPasswordOk(this.getPassword())) {
            return false;
        }
        return true;
    }
}
