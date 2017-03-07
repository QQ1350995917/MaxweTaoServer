package org.maxwe.tao.server.controller.account.agent.model;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-07 13:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 个人信息相应模型
 */
public class AgentMineResponseModel extends ResponseModel<AgentMineRequestModel> {
    private AgentModel agent;

    public AgentMineResponseModel() {
        super();
    }

    public AgentMineResponseModel(AgentMineRequestModel requestModel) {
        super(requestModel);
    }

    public AgentMineResponseModel(AgentMineRequestModel requestModel, AgentModel agent) {
        super(requestModel);
        this.agent = agent;
    }

    public AgentModel getAgent() {
        return agent;
    }

    public void setAgent(AgentModel agent) {
        this.agent = agent;
    }
}
