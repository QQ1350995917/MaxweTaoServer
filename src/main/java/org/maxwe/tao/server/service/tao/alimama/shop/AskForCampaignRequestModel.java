package org.maxwe.tao.server.service.tao.alimama.shop;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Pengwei Ding on 2017-03-24 16:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 申请推广活动的请求模型
 */
public class AskForCampaignRequestModel {
    @JSONField(serialize = false)
    private String url = "http://pub.alimama.com/pubauc/applyForCommonCampaign.json?";

    private long campId;//推广活动的ID
    private long keeperid;//推广商家的ID
    private String applyreason;//申请理由
    private String _tb_token_;
    private String cookie;

    public AskForCampaignRequestModel() {
        super();
    }

    public long getCampId() {
        return campId;
    }

    public void setCampId(long campId) {
        this.campId = campId;
    }

    public long getKeeperid() {
        return keeperid;
    }

    public void setKeeperid(long keeperid) {
        this.keeperid = keeperid;
    }

    public String getApplyreason() {
        return applyreason;
    }

    public void setApplyreason(String applyreason) {
        this.applyreason = applyreason;
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
        String[] split = this.getCookie().split(";");
        for (String looper : split) {
            if (looper.contains("_tb_token_")) {
                this.set_tb_token_(looper.replace("_tb_token_=", ""));
                break;
            }
        }
    }

    public String getUrl() {
        return url + "&campId=" + this.getCampId() +
                "&keeperid=" + this.getKeeperid() +
                "&applyreason=" + this.getApplyreason() +
                "&t=" + System.currentTimeMillis() +
                "&_tb_token_=" + this.get_tb_token_();
    }
}
