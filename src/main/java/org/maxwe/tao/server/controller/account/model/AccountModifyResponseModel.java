package org.maxwe.tao.server.controller.account.model;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-07 13:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 修改密码的响应模型
 */
public class AccountModifyResponseModel extends ResponseModel<AccountModifyRequestModel> {
    private TokenModel token;

    public AccountModifyResponseModel() {
        super();
    }

    public AccountModifyResponseModel(AccountModifyRequestModel requestModel) {
        super(requestModel);
    }

    public AccountModifyResponseModel(AccountModifyRequestModel requestModel, TokenModel token) {
        super(requestModel);
        this.token = token;
    }

    public TokenModel getToken() {
        return token;
    }

    public void setToken(TokenModel token) {
        this.token = token;
    }
}
