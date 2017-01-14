package org.maxwe.tao.server.controller.mate;

import org.maxwe.tao.server.controller.account.agent.model.AgentModel;
import org.maxwe.tao.server.controller.account.model.SessionModel;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-09 18:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class BranchModel extends SessionModel {
    private int total;
    private int pageIndex;
    private int pageSize;
    private LinkedList<AgentModel> agentEntities; // 相应字段

    public BranchModel() {
        super();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LinkedList<AgentModel> getAgentEntities() {
        return agentEntities;
    }

    public void setAgentEntities(LinkedList<AgentModel> agentEntities) {
        this.agentEntities = agentEntities;
    }

    @Override
    public boolean isParamsOk() {
        return this.getPageIndex() >= 0 && this.getPageSize() > 0 && super.isParamsOk();
    }
}
