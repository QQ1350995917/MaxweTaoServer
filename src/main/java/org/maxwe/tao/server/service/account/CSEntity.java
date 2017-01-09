package org.maxwe.tao.server.service.account;

import org.maxwe.tao.server.common.utils.DateTime;

/**
 * Created by Pengwei Ding on 2016-12-25 16:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CSEntity {
    private String id;
    private String mark;
    private String cellphone;
    private String token;
    private long timestamp;

    public CSEntity() {
        super();
    }

    public CSEntity(String id,String mark,String cellphone,String token) {
        super();
        this.id = id;
        this.mark = mark;
        this.cellphone = cellphone;
        this.token = token;
        this.timestamp = DateTime.getCurrentTimestamp();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CSEntity{" +
                "timestamp=" + timestamp +
                ", token='" + token + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", mark='" + mark + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
