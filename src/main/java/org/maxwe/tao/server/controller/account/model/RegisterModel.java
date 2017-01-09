package org.maxwe.tao.server.controller.account.model;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-09 18:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class RegisterModel implements Serializable {

    private String cellphone;
    private String smsCode;
    private String password;

    public RegisterModel() {
        super();
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
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

    public boolean isParamsOK(){
        if (CellPhoneUtils.isCellphone(this.getCellphone())
                && !StringUtils.isEmpty(this.getPassword())
                && this.getPassword().length() >= 6
                && this.getPassword().length() <= 12) {
            return true;
        } else {
            return false;
        }
    }
}
