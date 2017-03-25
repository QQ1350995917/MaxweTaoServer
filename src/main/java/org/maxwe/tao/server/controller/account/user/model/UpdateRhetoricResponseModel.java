package org.maxwe.tao.server.controller.account.user.model;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-25 14:08.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户更新分享推广说辞的响应模型
 */
public class UpdateRhetoricResponseModel extends ResponseModel<UpdateRhetoricRequestModel> {
    private String rhetoric;
    public UpdateRhetoricResponseModel() {
        super();
    }

    public UpdateRhetoricResponseModel(UpdateRhetoricRequestModel requestModel) {
        super(requestModel);
    }

    public String getRhetoric() {
        return rhetoric;
    }

    public void setRhetoric(String rhetoric) {
        this.rhetoric = rhetoric;
    }
}
