package org.maxwe.tao.server.controller.mate;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.controller.account.model.SessionModel;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.level.LevelEntity;

/**
 * Created by Pengwei Ding on 2017-01-12 16:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TrunkModel extends SessionModel {
    private String targetMark;
    private AgentEntity agentEntity;
    private LevelEntity levelEntity;

    public TrunkModel() {
        super();
    }

    public String getTargetMark() {
        return targetMark;
    }

    public void setTargetMark(String targetMark) {
        this.targetMark = targetMark;
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
                "targetMark='" + targetMark + '\'' +
                ", agentEntity=" + agentEntity +
                '}';
    }

    @Override
    public boolean isParamsOk() {
        return super.isParamsOk() && !StringUtils.isEmpty(this.getTargetMark());
    }
}
