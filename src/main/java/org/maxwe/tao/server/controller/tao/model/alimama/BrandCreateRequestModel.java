package org.maxwe.tao.server.controller.tao.model.alimama;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-08 21:31.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 新建一个导购推广以及其下的一个推广位请求模型
 */
public class BrandCreateRequestModel extends TokenModel {
    private String cookie;
    private String guideName;
    private String adZoneName;
    private String weChat;//微信号

    public BrandCreateRequestModel() {
        super();
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public String getAdZoneName() {
        return adZoneName;
    }

    public void setAdZoneName(String adZoneName) {
        this.adZoneName = adZoneName;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    @JSONField(serialize=false)
    public boolean isBrandCreateRequestParamsOk() {
        if (StringUtils.isEmpty(this.getCookie())) {
            return false;
        }
        if (StringUtils.isEmpty(this.getWeChat())) {
            return false;
        }
        if (StringUtils.isEmpty(this.getGuideName())) {
            return false;
        }
        if (StringUtils.isEmpty(this.getAdZoneName())) {
            return false;
        }
        return super.isTokenParamsOk();
    }

}
