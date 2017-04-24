package org.maxwe.tao.server.service.meta;

import org.maxwe.tao.server.common.utils.DateTimeUtils;

/**
 * Created by Pengwei Ding on 4/24/17 4:53 PM.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class SpecialLinkEntity {
    private int id;
    private String url;
    private long createTime;
    private String createTimeLabel;

    public SpecialLinkEntity() {
        super();
    }


    public SpecialLinkEntity(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeLabel() {
        return DateTimeUtils.parseLongToFullTime(this.getCreateTime());
    }

    public void setCreateTimeLabel(String createTimeLabel) {
        this.createTimeLabel = createTimeLabel;
    }
}
