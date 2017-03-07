package org.maxwe.tao.server.controller.account;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-07 11:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 判断手机号码是否已经存在的请求模型
 */
public class AccountExistResponseModel extends ResponseModel<AccountExistRequestModel> {
    private boolean existence;//存在性

    public AccountExistResponseModel() {
        super();
    }

    public AccountExistResponseModel(AccountExistRequestModel requestModel) {
        super(requestModel);
    }

    public AccountExistResponseModel(AccountExistRequestModel requestModel, boolean existence) {
        super(requestModel);
        this.existence = existence;
    }

    public boolean isExistence() {
        return existence;
    }

    public void setExistence(boolean existence) {
        this.existence = existence;
    }
}
