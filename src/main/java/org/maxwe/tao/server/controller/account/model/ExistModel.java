package org.maxwe.tao.server.controller.account.model;

import com.alibaba.druid.util.StringUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-09 19:08.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ExistModel implements Serializable {
    private String cellphone;

    public ExistModel() {
        super();
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public boolean isParamsOk(){
        if (!StringUtils.isEmpty(this.getCellphone())){
            return true;
        }
        return false;
    }
}
