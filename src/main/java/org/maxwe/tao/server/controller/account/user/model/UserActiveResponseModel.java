package org.maxwe.tao.server.controller.account.user.model;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-07 17:30.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户激活相应模型
 */
public class UserActiveResponseModel extends ResponseModel<UserActiveRequestModel> {
    private long actTime;

    public UserActiveResponseModel() {
        super();
    }

    public UserActiveResponseModel(UserActiveRequestModel requestModel) {
        super(requestModel);
    }

    public UserActiveResponseModel(UserActiveRequestModel requestModel, long actTime) {
        super(requestModel);
        this.actTime = actTime;
    }

    public long getActTime() {
        return actTime;
    }

    public void setActTime(long actTime) {
        this.actTime = actTime;
    }
}
