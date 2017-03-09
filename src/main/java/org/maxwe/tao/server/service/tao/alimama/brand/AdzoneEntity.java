package org.maxwe.tao.server.service.tao.alimama.brand;

/**
 * Created by Pengwei Ding on 2017-03-08 21:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 导购推广位模型
 */
public class AdZoneEntity {
    private String siteId;
    private String id;
    private String name;

    public AdZoneEntity() {
        super();
    }

    public AdZoneEntity(String siteId,String id,String name) {
        this.siteId = siteId;
        this.id = id;
        this.name = name;
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
