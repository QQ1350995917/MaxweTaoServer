package org.maxwe.tao.server.controller.mate.model;

import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.service.account.agent.AgentEntity;

/**
 * Created by Pengwei Ding on 2017-03-03 15:53.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理向上级发起授权请求的响应模型
 */
public class BranchBegResponseModel extends ResponseModel<BranchBegRequestModel> {
    private MateModel trunk;
    private AgentEntity branch;

    public BranchBegResponseModel() {
        super();
    }

    public BranchBegResponseModel(BranchBegRequestModel requestModel) {
        super(requestModel);
    }

    public BranchBegResponseModel(BranchBegRequestModel requestModel, MateModel trunk, AgentEntity branch) {
        super(requestModel);
        this.trunk = trunk;
        this.branch = branch;
    }

    public MateModel getTrunk() {
        return trunk;
    }

    public void setTrunk(MateModel trunk) {
        this.trunk = trunk;
    }

    public AgentEntity getBranch() {
        return branch;
    }

    public void setBranch(AgentEntity branch) {
        this.branch = branch;
    }
}
