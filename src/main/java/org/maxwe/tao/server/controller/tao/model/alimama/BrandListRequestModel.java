package org.maxwe.tao.server.controller.tao.model.alimama;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-08 21:31.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 导购推广以及其下的推广位列表请求模型
 */
public class BrandListRequestModel extends TokenModel {
    private String cookie;

    public BrandListRequestModel() {
        super();
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @JSONField(serialize=false)
    public boolean isBrandListRequestParamsOk(){
        if (StringUtils.isEmpty(this.getCookie())){
            return false;
        }
        return super.isTokenParamsOk();
    }
}
