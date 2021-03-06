package org.maxwe.tao.server.controller.account.agent.model;

import org.maxwe.tao.server.controller.account.model.TokenModel;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.level.LevelEntity;

/**
 * Created by Pengwei Ding on 2017-01-09 18:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class AgentModel extends TokenModel {
    private AgentEntity agentEntity; // 响应字段
    private LevelEntity levelEntity; // 响应字段

    public AgentModel() {
        super();
    }

    public AgentModel(AgentEntity agentEntity, LevelEntity levelEntity) {
        this.agentEntity = agentEntity;
        this.levelEntity = levelEntity;
    }

    public AgentEntity getAgentEntity() {
        return agentEntity;
    }

    public void setAgentEntity(AgentEntity agentEntity) {
        this.agentEntity = agentEntity;
    }

    public LevelEntity getLevelEntity() {
        return levelEntity;
    }

    public void setLevelEntity(LevelEntity levelEntity) {
        this.levelEntity = levelEntity;
    }
}
