package org.maxwe.tao.server.common.model;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-09 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public abstract class SessionModel implements Serializable {
    private String t;
    private String mark;
    private String cellphone;

    public SessionModel() {
        super();
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    private boolean isSessionParamsOk() {
        if (!StringUtils.isEmpty(this.getT())
                && !StringUtils.isEmpty(this.getMark())
                && CellPhoneUtils.isCellphone(this.getCellphone())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isParamsOk(){
        return isSessionParamsOk();
    }


}
