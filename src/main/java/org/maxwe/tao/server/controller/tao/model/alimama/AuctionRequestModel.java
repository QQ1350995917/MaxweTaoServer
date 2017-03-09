package org.maxwe.tao.server.controller.tao.model.alimama;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-08 20:52.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 生成优惠券的请求模型
 */
public class AuctionRequestModel extends TokenModel {
    private long siteid;  // 21454360;
    private long auctionid;  // 521477762631; 商品ID
    private long adzoneid;  // 72044740;
    private String cookie;

    public AuctionRequestModel() {
        super();
    }

    public long getSiteid() {
        return siteid;
    }

    public void setSiteid(long siteid) {
        this.siteid = siteid;
    }

    public long getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(long auctionid) {
        this.auctionid = auctionid;
    }

    public long getAdzoneid() {
        return adzoneid;
    }

    public void setAdzoneid(long adzoneid) {
        this.adzoneid = adzoneid;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @JSONField(serialize=false)
    public boolean isAuctionRequestParamsOk() {
        if (this.getSiteid() <= 0 ||
                this.getAuctionid() <= 0 ||
                this.getAdzoneid() <= 0) {
            return false;
        }

        if (StringUtils.isEmpty(this.getCookie())) {
            return false;
        }
        return true;
    }
}
