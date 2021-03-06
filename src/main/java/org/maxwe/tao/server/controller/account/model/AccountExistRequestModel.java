package org.maxwe.tao.server.controller.account.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;

/**
 * Created by Pengwei Ding on 2017-03-07 11:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 判断手机号码是否已经存在的请求模型
 */
public class AccountExistRequestModel extends TokenModel {
    private String cellphone;

    public AccountExistRequestModel() {
        super();
    }

    @Override
    public String getCellphone() {
        return cellphone;
    }

    @Override
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @Override
    public String toString() {
        return "AccountExistRequestModel{" +
                "cellphone='" + cellphone + '\'' +
                '}';
    }

    @JSONField(serialize=false)
    public boolean isAccountExistRequestParamsOk() {
        if (CellPhoneUtils.isCellphone(cellphone)) {
            return true;
        } else {
            return false;
        }
    }

}
