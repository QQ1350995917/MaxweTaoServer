package org.maxwe.tao.server.service.tao.alimama.shop;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Pengwei Ding on 2017-03-24 17:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 加入店铺推广活动的请求模型
 */
public class ShopCodeRequestModel {
    @JSONField(serialize = false)
    private String url = "http://pub.alimama.com/common/code/getShopCode.json?";

    private long orimemberid;//	2829087341 活动的原始发起人ID
    private long adzoneid;//	73766584 推广活动ID
    private long siteid;//	22170286 推广位ID
    private long t = System.currentTimeMillis();//	1490341696842
    private String _tb_token_;//	w0b91ikuiPTq
    private String _input_charset =	"utf-8";
    private String cookie;

    public ShopCodeRequestModel() {
        super();
    }

    public long getOrimemberid() {
        return orimemberid;
    }

    public void setOrimemberid(long orimemberid) {
        this.orimemberid = orimemberid;
    }

    public long getAdzoneid() {
        return adzoneid;
    }

    public void setAdzoneid(long adzoneid) {
        this.adzoneid = adzoneid;
    }

    public long getSiteid() {
        return siteid;
    }

    public void setSiteid(long siteid) {
        this.siteid = siteid;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
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
        return url + "&orimemberid=" + this.getOrimemberid() +
                "&adzoneid=" + this.getAdzoneid() +
                "&siteid=" + this.getSiteid() +
                "&_input_charset=" + this.get_input_charset() +
                "&t=" + System.currentTimeMillis() +
                "&_tb_token_=" + this.get_tb_token_();
    }
}
