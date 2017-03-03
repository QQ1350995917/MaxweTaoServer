package org.maxwe.tao.server.controller.mate.model;

import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-03 16:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 向下级授权的请求模型
 */
public class GrantBranchRequestModel extends TokenModel {
    private int branchId;

    public GrantBranchRequestModel() {
        super();
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public boolean isGrantBranchParamsOk(){
        return super.isTokenParamsOk() && this.getBranchId() > 0;
    }
}
