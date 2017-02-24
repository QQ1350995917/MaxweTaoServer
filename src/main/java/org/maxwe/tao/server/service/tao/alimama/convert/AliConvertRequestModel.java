package org.maxwe.tao.server.service.tao.alimama.convert;

import com.alibaba.druid.util.StringUtils;

/**
 * Created by Pengwei Ding on 2017-02-24 21:24.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AliConvertRequestModel {
    private static final String URL = "http://pub.alimama.com/common/code/getAuctionCode.json?";
    private long siteid;  // 21454360;
    private long scenes = 1;  // 1;
    private long auctionid;  // 521477762631; 商品ID
//    @JSONField(serialize=false)
//    private transient long t = System.currentTimeMillis();  // 1487937140646;
    private String _tb_token_;  // O7cwOCmlZCq;
    private long adzoneid;  // 72044740;
    private String cookie;

    public AliConvertRequestModel() {
        super();
    }

    public long getSiteid() {
        return siteid;
    }

    public void setSiteid(long siteid) {
        this.siteid = siteid;
    }

    public long getScenes() {
        return scenes;
    }

    public void setScenes(long scenes) {
        this.scenes = scenes;
    }

    public long getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(long auctionid) {
        this.auctionid = auctionid;
    }

//    public long getT() {
//        return t;
//    }
//
//    public void setT(long t) {
//        this.t = t;
//    }

    public String get_tb_token_() {
        return _tb_token_;
    }

    public void set_tb_token_(String _tb_token_) {
        this._tb_token_ = _tb_token_;
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

    public String getUrl() {
        return URL + "siteid=" + getSiteid() +
                "&scenes=" + getScenes() +
                "&auctionid=" + getAuctionid() +
                "&adzoneid=" + getAdzoneid();
    }

    public boolean isParamsOk(){
        if (siteid < 0 || auctionid < 0 || adzoneid < 0){
            return false;
        }

        if (StringUtils.isEmpty(this.getCookie())) {
            return false;
        } else {
            String[] split = this.getCookie().split(";");
            for (String looper : split) {
                if (looper.contains("_tb_token_")) {
                    this.set_tb_token_(looper.replace("_tb_token_=", ""));
                    break;
                }
            }
            if (StringUtils.isEmpty(this.get_tb_token_())) {
                return false;
            }
            return true;
        }
    }
}
