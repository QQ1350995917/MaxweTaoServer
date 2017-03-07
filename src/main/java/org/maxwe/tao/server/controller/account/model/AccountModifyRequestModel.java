package org.maxwe.tao.server.controller.account.model;

import org.maxwe.tao.server.controller.account.model.AuthenticateModel;

/**
 * Created by Pengwei Ding on 2017-03-07 13:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 修改密码的请求模型
 */
public class AccountModifyRequestModel extends AuthenticateModel {
    private String password;

    public AccountModifyRequestModel() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private boolean isAccountModifyRequestParamsOk() {
        if (!super.isAuthenticateParamsOk()) {
            return false;
        }
        return true;
    }
}
