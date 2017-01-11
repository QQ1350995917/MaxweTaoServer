package org.maxwe.tao.server.controller.account.model;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-09 18:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class RegisterModel implements Serializable {
    public static final PropertyFilter propertyFilter = new PropertyFilter() {
        @Override
        public boolean apply(Object object, String name, Object value) {
            if ("password".equals(name) || "smsCode".equals(name)) {
                return false;
            }
            return true;
        }
    };

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

    @JSONField(serialize=false)
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
