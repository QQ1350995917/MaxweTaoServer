package org.maxwe.tao.server.controller.mate.model;

import org.maxwe.tao.server.service.account.agent.AgentEntity;

/**
 * Created by Pengwei Ding on 2017-03-03 15:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 获取上级代理的响应模型
 */
public class TrunkInfoResponseModel extends TrunkInfoRequestModel {

    private MateModel trunk;
    private AgentEntity branch;

    public TrunkInfoResponseModel() {
        super();
    }

    public TrunkInfoResponseModel(MateModel trunk,AgentEntity branch){
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
