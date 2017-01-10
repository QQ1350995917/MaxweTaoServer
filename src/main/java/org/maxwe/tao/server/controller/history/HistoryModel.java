package org.maxwe.tao.server.controller.history;

import org.maxwe.tao.server.common.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-09 18:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class HistoryModel extends SessionModel {
    private String fromId; // 操作来源ID
    private String toId; // 操作流向ID，如果类型为1，则此ID为后来补充
    private int type; // 1激活码，2批量激活码
    private String actCode; //如果类型为1，则是向单个用激活
    private int numCode;//如果类型为2，则表示交易为数量
    private long createTime;//创建时间
    private long updateTime;//更新时间

    public HistoryModel() {
        super();
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public int getNumCode() {
        return numCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return super.toString() + "HistoryModel{" +
                "updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", numCode=" + numCode +
                ", actCode='" + actCode + '\'' +
                ", type=" + type +
                ", toId='" + toId + '\'' +
                ", fromId='" + fromId + '\'' +
                '}';
    }
}
