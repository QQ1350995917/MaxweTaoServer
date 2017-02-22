package org.maxwe.tao.server.service.tao.bao.convert;

import com.alibaba.druid.util.StringUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-02-22 21:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoTransRequestModel implements Serializable {

    private String cookie;
    private String siteid;
    private String adzoneid;
    private String promotionURL;

    public TaoTransRequestModel() {
        super();
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getAdzoneid() {
        return adzoneid;
    }

    public void setAdzoneid(String adzoneid) {
        this.adzoneid = adzoneid;
    }

    public String getPromotionURL() {
        return promotionURL;
    }

    public void setPromotionURL(String promotionURL) {
        this.promotionURL = promotionURL;
    }

    public boolean isParamsOk() {
        if (StringUtils.isEmpty(this.cookie) ||
                StringUtils.isEmpty(this.siteid) ||
                StringUtils.isEmpty(this.adzoneid) ||
                StringUtils.isEmpty(this.promotionURL)) {
            return false;
        }
        return true;
    }
}
