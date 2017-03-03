package org.maxwe.tao.server.controller.mate.model;

import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.level.LevelEntity;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-03-03 16:06.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理和级别的组合模型
 */
public class MateModel implements Serializable {
    private AgentEntity agent;
    private LevelEntity level;

    public MateModel() {
        super();
    }

    public MateModel(AgentEntity agent, LevelEntity level) {
        this.agent = agent;
        this.level = level;
    }

    public AgentEntity getAgent() {
        return agent;
    }

    public void setAgent(AgentEntity agent) {
        this.agent = agent;
    }

    public LevelEntity getLevel() {
        return level;
    }

    public void setLevel(LevelEntity level) {
        this.level = level;
    }
}
