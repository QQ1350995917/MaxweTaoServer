package org.maxwe.tao.server.common.sms;

/**
 * Created by Pengwei Ding on 2016-12-28 17:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SMSEntity {
    private String cellphone;
    private long genTimestamp;
    private String code;

    public SMSEntity(String cellphone,String code) {
        this.cellphone = cellphone;
        this.code = code;
        this.genTimestamp = System.currentTimeMillis();
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public long getGenTimestamp() {
        return genTimestamp;
    }

    public void setGenTimestamp(long genTimestamp) {
        this.genTimestamp = genTimestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
