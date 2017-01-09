package org.maxwe.tao.server.controller.account.model;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-09 14:14.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LoginModel implements Serializable {

    private String cellphone;
    private String password;

    public LoginModel() {
        super();
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isParamsOK() {
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
