package org.maxwe.tao.server.controller.mate;

import org.maxwe.tao.server.common.model.SessionModel;
import org.maxwe.tao.server.controller.account.agent.model.AgentModel;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-09 18:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MateModel extends SessionModel {
    private int pageIndex;
    private int pageSize;
    private LinkedList<AgentModel> mates;

    public MateModel() {
        super();
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

    public LinkedList<AgentModel> getMates() {
        return mates;
    }

    public void setMates(LinkedList<AgentModel> mates) {
        this.mates = mates;
    }

    @Override
    public boolean isParamsOk() {
        if (this.getPageIndex() >= 0 && this.getPageSize() > 0) {
            return true && super.isParamsOk();
        }
        return false;
    }
}
