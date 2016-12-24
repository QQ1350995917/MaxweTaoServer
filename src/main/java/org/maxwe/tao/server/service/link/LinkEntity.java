package org.maxwe.tao.server.service.link;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LinkEntity {
    private String linkId;
    private String label;
    private String href;
    private String pid;
    private int weight;
    private int status;//-1标示删除，1标示禁用，2标示正常
    private String createTime;
    private String updateTime;

    public LinkEntity() {
        super();
    }

    public LinkEntity(String linkId,int weight) {
        this.linkId = linkId;
        this.weight = weight;
    }

    public LinkEntity(String linkId, String label, String href, String pid, int status) {
        this.linkId = linkId;
        this.label = label;
        this.href = href;
        this.pid = pid;
        this.status = status;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
