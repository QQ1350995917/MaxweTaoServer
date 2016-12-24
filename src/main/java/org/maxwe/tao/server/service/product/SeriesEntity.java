package org.maxwe.tao.server.service.product;

/**
 * Created by Pengwei Ding on 2016-07-30 21:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SeriesEntity {
    private String seriesId;
    private String label;
    private int queue;
    private int status; // -1标示删除，1标示禁用，2标示正常
    private String createTime;
    private String updateTime;

    public SeriesEntity() {
        super();
    }

    public SeriesEntity(String label) {
        this.label = label;
    }

    public SeriesEntity(String seriesId, String label) {
        this.seriesId = seriesId;
        this.label = label;
    }

    public SeriesEntity(String seriesId, String label, int queue, int status) {
        this.seriesId = seriesId;
        this.label = label;
        this.queue = queue;
        this.status = status;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
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
