package org.maxwe.tao.server.controller.account.model;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-07 13:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 注册相应模型
 */
public class AccountSignUpResponseModel extends ResponseModel<AccountSignUpRequestModel> {
    private TokenModel token;

    public AccountSignUpResponseModel() {
        super();
    }

    public AccountSignUpResponseModel(AccountSignUpRequestModel requestModel) {
        super(requestModel);
    }

    public AccountSignUpResponseModel(AccountSignUpRequestModel requestModel, TokenModel token) {
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
