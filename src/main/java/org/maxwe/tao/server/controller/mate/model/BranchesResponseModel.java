package org.maxwe.tao.server.controller.mate.model;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-03 16:04.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 获取枝干下所有分支的响应模型
 */
public class BranchesResponseModel extends BranchesRequestModel {
    private int total;
    private int totalPages;
    private List<MateModel> branches;

    public BranchesResponseModel() {
        super();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<MateModel> getBranches() {
        return branches;
    }

    public void setBranches(List<MateModel> branches) {
        this.branches = branches;
    }
}
