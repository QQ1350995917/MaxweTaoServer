package org.maxwe.tao.server.service.user;

/**
 * Created by Pengwei Ding on 2016-12-25 16:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CSEntity {
    private String csId;
    private String token;
    private String mappingId;
    private int type;
    private String createTime;
    private String updateTime;

    public CSEntity() {
        super();
    }

    public CSEntity(String token) {
        super();
        this.token = token;
    }


    public CSEntity(String mappingId,int type) {
        this.mappingId = mappingId;
        this.type = type;
    }

    public String getCsId() {
        return csId;
    }

    public void setCsId(String csId) {
        this.csId = csId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
