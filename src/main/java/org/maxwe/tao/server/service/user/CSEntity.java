package org.maxwe.tao.server.service.user;

import org.maxwe.tao.server.common.utils.DateTime;

/**
 * Created by Pengwei Ding on 2016-12-25 16:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CSEntity {
    private String agentId;
    private String cellphone;
    private String token;
    private int type;
    private long timestamp;

    public CSEntity() {
        super();
    }

    public CSEntity(String agentId,String cellphone,String token,int type) {
        super();
        this.agentId = agentId;
        this.cellphone = cellphone;
        this.token = token;
        this.type = type;
        this.timestamp = DateTime.getCurrentTimestamp();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void resetTimestamp(){
        this.setTimestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "agentId = " + agentId + "; cellphone = " + cellphone + "; type = " + type + "; token = " + token + "; timestamp = " + DateTime.parseLongToFullTime(timestamp);
    }
}
