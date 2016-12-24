package org.maxwe.tao.server.service.poster;

/**
 * Created by Pengwei Ding on 2016-08-15 15:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class PosterEntity {
    private String posterId;
    private String name;
    private int status;
    private int clickable;
    private String href;
    private String fileId;
    private int weight;
    private String start;
    private String end;
    private String updateTime;
    private String createTime;

    public PosterEntity() {
        super();
    }

    public PosterEntity(String posterId, int weight) {
        this.posterId = posterId;
        this.weight = weight;
    }

    public PosterEntity(String posterId, String name,int status, int clickable, String href,String fileId, int weight,String start, String end) {
        this.posterId = posterId;
        this.name = name;
        this.status = status;
        this.clickable = clickable;
        this.href = href;
        this.fileId = fileId;
        this.weight = weight;
        this.start = start;
        this.end = end;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getClickable() {
        return clickable;
    }

    public void setClickable(int clickable) {
        this.clickable = clickable;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
