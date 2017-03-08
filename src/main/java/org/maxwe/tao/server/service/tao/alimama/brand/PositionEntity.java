package org.maxwe.tao.server.service.tao.alimama.brand;

/**
 * Created by Pengwei Ding on 2017-03-08 21:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 导购推广位模型
 */
public class PositionEntity {
    private String siteId;
    private String id;
    private String name;

    public PositionEntity() {
        super();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
