package org.maxwe.tao.server.controller.business;

import org.maxwe.tao.server.controller.user.VAgentEntity;
import org.maxwe.tao.server.service.user.agent.AgentEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-05 16:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SubAgentModel extends VAgentEntity{
    private int pageIndex;
    private int counter;
    private LinkedList<AgentEntity> subAgents;

    public SubAgentModel() {
        super();
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public LinkedList<AgentEntity> getSubAgents() {
        return subAgents;
    }

    public void setSubAgents(LinkedList<AgentEntity> subAgents) {
        this.subAgents = subAgents;
    }
}
