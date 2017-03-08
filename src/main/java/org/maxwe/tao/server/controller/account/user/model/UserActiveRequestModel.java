package org.maxwe.tao.server.controller.account.user.model;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.controller.account.model.AuthenticateModel;

/**
 * Created by Pengwei Ding on 2017-03-07 17:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户激活请求模型
 */
public class UserActiveRequestModel extends AuthenticateModel {
    private String actCode;

    public UserActiveRequestModel() {
        super();
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    @JSONField(serialize=false)
    public boolean isUserActiveRequestParamsOk(){
        if (StringUtils.isEmpty(actCode)){
            return false;
        }
        if (this.getActCode().length() != 8){
            return false;
        }
        return super.isAuthenticateParamsOk();
    }
}
