package org.maxwe.tao.server.controller.account.user.model;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-07 17:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户获取自己信息的响应模型
 */
public class UserMineResponseModel extends ResponseModel<UserMineRequestModel> {
    public UserMineResponseModel() {
        super();
    }

    public UserMineResponseModel(UserMineRequestModel requestModel) {
        super(requestModel);
    }
}
