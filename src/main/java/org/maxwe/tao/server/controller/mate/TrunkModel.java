package org.maxwe.tao.server.controller.mate;

import org.maxwe.tao.server.controller.account.model.SessionModel;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.level.LevelEntity;

/**
 * Created by Pengwei Ding on 2017-01-12 16:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TrunkModel extends SessionModel {
    private int targetId;
    private AgentEntity agentEntity;
    private LevelEntity levelEntity;

    public TrunkModel() {
        super();
    }


    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
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

    @Override
    public String toString() {
        return super.toString() + "TrunkModel{" +
                ", agentEntity=" + agentEntity +
                '}';
    }

    @Override
    public boolean isParamsOk() {
        return super.isParamsOk() && this.getTargetId() != 0;
    }
}
