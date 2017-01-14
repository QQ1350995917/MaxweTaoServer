package org.maxwe.tao.server.service.history;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-09 15:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class HistoryEntity implements Serializable {
    private String id; // 业务ID
    private String fromId; // 操作来源ID
    private String toId; // 操作流向ID，如果类型为1，则此ID为后来补充
    private String toMark; // 操作流向的显示ID，如果类型为1，则此ID为后来补充
    private int type; // 1激活码，2批量激活码
    private String actCode; //如果类型为1，则是向单个用激活
    private int codeNum;//如果类型为2，则表示交易为数量
    private long createTime;//创建时间
    private long updateTime;//更新时间

    public HistoryEntity() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getToMark() {
        return toMark;
    }

    public void setToMark(String toMark) {
        this.toMark = toMark;
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

    public int getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
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
        return "HistoryEntity{" +
                "updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", codeNum=" + codeNum +
                ", actCode='" + actCode + '\'' +
                ", type=" + type +
                ", toId='" + toId + '\'' +
                ", toMark='" + toMark + '\'' +
                ", fromId='" + fromId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
