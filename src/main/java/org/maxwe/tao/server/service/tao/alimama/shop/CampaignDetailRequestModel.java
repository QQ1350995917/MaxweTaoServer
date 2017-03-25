package org.maxwe.tao.server.service.tao.alimama.shop;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Pengwei Ding on 2017-03-24 17:02.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 查询推广活动详情的请求模型
 */
public class CampaignDetailRequestModel {
    @JSONField(serialize = false)
    private String url = "http://pub.alimama.com/campaign/campaignDetail.json?";

    private long campaignId;//40239885 推广活动的ID
    private long shopkeeperId;//115538439 推广商家的ID
    private String _tb_token_;//
    private String _input_charset = "utf-8";
    private String cookie;

    public CampaignDetailRequestModel() {
        super();
    }

    public CampaignDetailRequestModel(long campaignId, long shopkeeperId, String cookie) {
        this.campaignId = campaignId;
        this.shopkeeperId = shopkeeperId;
        this.setCookie(cookie);
    }

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    public long getShopkeeperId() {
        return shopkeeperId;
    }

    public void setShopkeeperId(long shopkeeperId) {
        this.shopkeeperId = shopkeeperId;
    }

    public String get_tb_token_() {
        return _tb_token_;
    }

    public void set_tb_token_(String _tb_token_) {
        this._tb_token_ = _tb_token_;
    }

    public String get_input_charset() {
        return _input_charset;
    }

    public void set_input_charset(String _input_charset) {
        this._input_charset = _input_charset;
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
        return url + "campaignId=" + this.getCampaignId() +
                "&shopkeeperId=" + this.getShopkeeperId() +
                "&t=" + System.currentTimeMillis() +
                "&_input_charset=" + get_input_charset() +
                "&_tb_token_=" + this.get_tb_token_().trim();
    }
}
