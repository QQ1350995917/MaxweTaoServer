package org.maxwe.tao.server.controller.account.user.model;

import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.service.account.user.UserEntity;

/**
 * Created by Pengwei Ding on 2017-03-07 17:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户获取自己信息的响应模型
 */
public class UserMineResponseModel extends ResponseModel<UserMineRequestModel> {
    private UserEntity user;

    public UserMineResponseModel() {
        super();
    }

    public UserMineResponseModel(UserMineRequestModel requestModel) {
        super(requestModel);
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
