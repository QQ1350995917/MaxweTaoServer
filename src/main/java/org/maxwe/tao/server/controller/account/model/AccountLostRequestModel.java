package org.maxwe.tao.server.controller.account.model;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;
import org.maxwe.tao.server.common.utils.PasswordUtils;

/**
 * Created by Pengwei Ding on 2017-03-07 13:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 密码丢失的请求模型
 */
public class AccountLostRequestModel extends TokenModel {
    private String smsCode;
    private String password;

    public AccountLostRequestModel() {
        super();
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JSONField(serialize=false)
    public boolean isAccountLostRequestParamsOk(){
        if (!CellPhoneUtils.isCellphone(this.getCellphone())) {
            return false;
        }
        if (StringUtils.isEmpty(this.getSmsCode())) {
            return false;
        }
        if (!PasswordUtils.isPlainPasswordOk(this.getPassword())) {
            return false;
        }
        return true;
    }

}
