package org.maxwe.tao.server.controller.account.user.model;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-25 14:08.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户更新申请加入推广计划的理由的响应模型
 */
public class UpdateReasonResponseModel extends ResponseModel<UpdateReasonRequestModel> {
    private String reason;
    public UpdateReasonResponseModel() {
        super();
    }

    public UpdateReasonResponseModel(UpdateReasonRequestModel requestModel) {
        super(requestModel);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
