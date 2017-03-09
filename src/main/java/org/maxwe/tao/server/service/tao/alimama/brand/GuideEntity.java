package org.maxwe.tao.server.service.tao.alimama.brand;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-08 21:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 导购推广模型
 */
public class GuideEntity implements Serializable {
    private String siteId;
    private String name;
    private List<AdZoneEntity> adZones;

    public GuideEntity() {
        super();
    }

    public GuideEntity(String siteId,String name) {
        super();
        this.siteId = siteId;
        this.name = name;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AdZoneEntity> getAdZones() {
        return adZones;
    }

    public void setAdZones(List<AdZoneEntity> adZones) {
        this.adZones = adZones;
    }
}
