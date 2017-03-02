package org.maxwe.tao.server.controller.meta;

import org.maxwe.tao.server.common.utils.CellPhoneUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-10 18:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class SMSModel implements Serializable {
    private String cellphone;

    public SMSModel() {
        super();
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public boolean isParamsOk(){
        if (CellPhoneUtils.isCellphone(this.getCellphone())){
            return true;
        }
        return false;
    }
}
