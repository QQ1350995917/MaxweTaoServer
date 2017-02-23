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

    public String getGoodsId() {
        String goodsId = null;
        String[] split = this.promotionURL.split("\\?");
        if (split.length > 1) {
            String[] split1 = split[1].split("&");//id=535933437297
            for (String sp : split1) {
                if (sp.startsWith("id=")) {
                    goodsId = sp.replace("id=", "");
                    break;
                }
            }
        }
        if (goodsId == null){
            goodsId = split[0].substring(split[0].lastIndexOf("/") + 1, split[0].lastIndexOf("."));
        }
        return goodsId;
    }
}
