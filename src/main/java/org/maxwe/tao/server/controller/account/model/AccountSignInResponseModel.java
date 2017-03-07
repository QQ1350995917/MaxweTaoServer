package org.maxwe.tao.server.controller.account.model;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-07 13:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 登录响应模型
 */
public class AccountSignInResponseModel extends ResponseModel<AccountSignInRequestModel> {
    private TokenModel token;

    public AccountSignInResponseModel() {
        super();
    }

    public AccountSignInResponseModel(AccountSignInRequestModel requestModel) {
        super(requestModel);
    }

    public AccountSignInResponseModel(AccountSignInRequestModel requestModel,TokenModel token) {
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
