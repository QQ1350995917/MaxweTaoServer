package org.maxwe.tao.server.controller.mate.model;

import org.maxwe.tao.server.common.response.ResponseModel;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-03 16:04.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 获取枝干下所有分支的响应模型
 */
public class BranchesResponseModel extends ResponseModel<BranchesRequestModel> {
    private int total;
    private List<MateModel> branches;

    public BranchesResponseModel() {
        super();
    }

    public BranchesResponseModel(BranchesRequestModel requestModel) {
        super(requestModel);
    }

    public BranchesResponseModel(BranchesRequestModel requestModel, int total, List<MateModel> branches) {
        super(requestModel);
        this.total = total;
        this.branches = branches;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MateModel> getBranches() {
        return branches;
    }

    public void setBranches(List<MateModel> branches) {
        this.branches = branches;
    }
}
