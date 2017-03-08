package org.maxwe.tao.server.service.tao.alimama.brand;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-03-08 21:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 导购推广模型
 */
public class PromotionEntity implements Serializable {
    private String siteId;
    private String name;

    public PromotionEntity() {
        super();
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
}
