package org.maxwe.tao.server.service.account;

import org.maxwe.tao.server.common.utils.DateTimeUtils;

/**
 * Created by Pengwei Ding on 2016-12-25 16:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CSEntity {
    private String cellphone;
    private String token;
    private int apt; // 登录类型
    private long timestamp;

    public CSEntity() {
        super();
    }

    public CSEntity(String cellphone, String token, int apt) {
        super();
        this.cellphone = cellphone;
        this.token = token;
        this.timestamp = DateTimeUtils.getCurrentTimestamp();
        this.apt = apt;
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

    public int getApt() {
        return apt;
    }

    public void setApt(int apt) {
        this.apt = apt;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void resetTimestamp() {
        this.setTimestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "CSEntity{" +
                "timestamp=" + timestamp +
                ", token='" + token + '\'' +
                ", cellphone='" + cellphone + '\'' +
                '}';
    }
}
