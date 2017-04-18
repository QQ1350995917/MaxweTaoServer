package org.maxwe.tao.client.auction;

import org.apache.http.util.TextUtils;

/**
 * Created by Pengwei Ding on 2017-04-17 11:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AuctionRequestModel {
    private static final String URL = "http://pub.alimama.com/common/code/getAuctionCode.json?";
    private long auctionid;  // 521477762631; 商品ID
    private long scenes = 1;  // 1 固定值
    private long siteid;  // 21454360 推广ID
    private long adzoneid;  // 72044740 推广位ID
    private String _tb_token_;  // O7cwOCmlZCq;
    private String cookie;

    public AuctionRequestModel() {
        super();
    }

    public String getURL() {
        return URL + "siteid=" + this.siteid +
                "&scenes=" + this.scenes +
                "&auctionid=" + this.auctionid +
                "&adzoneid=" + this.adzoneid;
    }

    public long getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(long auctionid) {
        this.auctionid = auctionid;
    }

    public long getScenes() {
        return scenes;
    }

    public void setScenes(long scenes) {
        this.scenes = scenes;
    }

    public long getSiteid() {
        return siteid;
    }

    public void setSiteid(long siteid) {
        this.siteid = siteid;
    }

    public long getAdzoneid() {
        return adzoneid;
    }

    public void setAdzoneid(long adzoneid) {
        this.adzoneid = adzoneid;
    }

    public String get_tb_token_() {
        return _tb_token_;
    }

    public void set_tb_token_(String _tb_token_) {
        this._tb_token_ = _tb_token_;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
        if (TextUtils.isEmpty(this.cookie)) {
            return;
        } else {
            String[] split = this.getCookie().split(";");
            for (String looper : split) {
                if (looper.contains("_tb_token_")) {
                    this.set_tb_token_(looper.replace("_tb_token_=", ""));
                    break;
                }
            }
        }
    }

    public boolean isParamsOk() {
        if (siteid < 0 || auctionid < 0 || adzoneid < 0) {
            return false;
        }

        if (TextUtils.isEmpty(this.getCookie()) || TextUtils.isEmpty(this.get_tb_token_())) {
            return false;
        }
        return true;
    }
}
