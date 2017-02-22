package org.maxwe.tao.server.service.tao.mami;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-02-22 21:17.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class URLTransEntity implements Serializable {
    private String sclick;
    private String taoToken;
    private String qrCodeUrl;
    private String shortLinkUrl;

    public URLTransEntity() {
        super();
    }

    public String getSclick() {
        return sclick;
    }

    public void setSclick(String sclick) {
        this.sclick = sclick;
    }

    public String getTaoToken() {
        return taoToken;
    }

    public void setTaoToken(String taoToken) {
        this.taoToken = taoToken;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getShortLinkUrl() {
        return shortLinkUrl;
    }

    public void setShortLinkUrl(String shortLinkUrl) {
        this.shortLinkUrl = shortLinkUrl;
    }
}
