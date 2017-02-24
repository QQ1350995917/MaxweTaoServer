package org.maxwe.tao.server.service.tao.alimama.common;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-24 20:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AliResponseDataEntity {
    private AliResponseHeadEntity head;
    private AliResponseConditionEntity condition;
    private AliResponsePaginatorEntity paginator;
    private List<AliResponsePageEntity> pageList;
    private List<AliResponseNavigator> navigator;
    private String extraInfo;

    public AliResponseDataEntity() {
        super();
    }

    public AliResponseHeadEntity getHead() {
        return head;
    }

    public void setHead(AliResponseHeadEntity head) {
        this.head = head;
    }

    public AliResponseConditionEntity getCondition() {
        return condition;
    }

    public void setCondition(AliResponseConditionEntity condition) {
        this.condition = condition;
    }

    public AliResponsePaginatorEntity getPaginator() {
        return paginator;
    }

    public void setPaginator(AliResponsePaginatorEntity paginator) {
        this.paginator = paginator;
    }

    public List<AliResponsePageEntity> getPageList() {
        return pageList;
    }

    public void setPageList(List<AliResponsePageEntity> pageList) {
        this.pageList = pageList;
    }

    public List<AliResponseNavigator> getNavigator() {
        return navigator;
    }

    public void setNavigator(List<AliResponseNavigator> navigator) {
        this.navigator = navigator;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
