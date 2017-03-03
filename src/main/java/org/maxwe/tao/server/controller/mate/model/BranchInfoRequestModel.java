package org.maxwe.tao.server.controller.mate.model;

import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-03 17:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理查看下级信息的请求模型
 */
public class BranchInfoRequestModel extends TokenModel {
    private int branchId;

    public BranchInfoRequestModel() {
        super();
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public boolean isBranchInfoParamsOk(){
        return super.isTokenParamsOk() && this.getBranchId() > 0;
    }

}
