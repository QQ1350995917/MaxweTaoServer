package org.maxwe.tao.server.controller.mate.model;

/**
 * Created by Pengwei Ding on 2017-03-03 16:35.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 向下级授权的响应模型
 */
public class GrantBranchResponseModel extends GrantBranchRequestModel {
    private MateModel branch;

    public GrantBranchResponseModel() {
        super();
    }

    public GrantBranchResponseModel(MateModel branch) {
        this.branch = branch;
    }

    public MateModel getBranch() {
        return branch;
    }

    public void setBranch(MateModel branch) {
        this.branch = branch;
    }
}
