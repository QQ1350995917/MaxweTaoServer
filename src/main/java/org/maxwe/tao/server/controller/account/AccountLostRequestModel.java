package org.maxwe.tao.server.controller.account;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;
import org.maxwe.tao.server.common.utils.PasswordUtils;
import org.maxwe.tao.server.controller.account.model.TokenModel;

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
