package org.maxwe.tao.server.service.tao.alimama.shop;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Pengwei Ding on 2017-03-24 16:02.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 根据商品的ID信息查询推广活动的请求模型
 */
public class GoodsCampaignQueryRequestModel {
    @JSONField(serialize = false)
    private String url = "http://pub.alimama.com/pubauc/getCommonCampaignByItemId.json?";

    private long itemId;//商品的ID
    private String _tb_token_;
    private String cookie;

    public GoodsCampaignQueryRequestModel() {
        super();
    }

    public GoodsCampaignQueryRequestModel(long itemId, String cookie) {
        this.itemId = itemId;
        this.setCookie(cookie);
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
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
        return url + "itemId=" + this.getItemId() +
                "&t=" + System.currentTimeMillis() +
                "&_tb_token_=" + this.get_tb_token_().trim();
    }
}
