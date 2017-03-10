package org.maxwe.tao.server.controller.mate.model;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-03 16:35.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 向下级授权的响应模型
 */
public class GrantBranchResponseModel extends ResponseModel<GrantBranchRequestModel> {
    private MateModel branch;

    public GrantBranchResponseModel() {
        super();
    }

    public GrantBranchResponseModel(GrantBranchRequestModel requestModel) {
        super(requestModel);
    }

    public GrantBranchResponseModel(GrantBranchRequestModel requestModel, MateModel branch) {
        super(requestModel);
        this.branch = branch;
    }

    public MateModel getBranch() {
        return branch;
    }

    public void setBranch(MateModel branch) {
        this.branch = branch;
    }
}
