package org.maxwe.tao.server.controller.account.agent.model;

import org.maxwe.tao.server.controller.account.model.SessionModel;
import org.maxwe.tao.server.service.account.agent.AgentEntity;

/**
 * Created by Pengwei Ding on 2017-01-09 18:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AgentModel extends SessionModel {
    private AgentEntity agentEntity; // 响应字段

    public AgentModel() {
        super();
    }

    public AgentEntity getAgentEntity() {
        return agentEntity;
    }

    public void setAgentEntity(AgentEntity agentEntity) {
        this.agentEntity = agentEntity;
    }
}
