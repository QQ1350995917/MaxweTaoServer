package org.maxwe.tao.server.controller.account.model;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-07 13:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 密码丢失的相应模型
 */
public class AccountLostResponseModel extends ResponseModel<AccountLostRequestModel> {
    private TokenModel token;

    public AccountLostResponseModel() {
        super();
    }

    public AccountLostResponseModel(AccountLostRequestModel requestModel) {
        super(requestModel);
    }

    public AccountLostResponseModel(AccountLostRequestModel requestModel, TokenModel token) {
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
