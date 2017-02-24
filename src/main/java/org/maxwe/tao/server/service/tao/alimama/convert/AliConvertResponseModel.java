package org.maxwe.tao.server.service.tao.alimama.convert;

import org.maxwe.tao.server.service.tao.alimama.common.AliResponseInfoEntity;

/**
 * Created by Pengwei Ding on 2017-02-24 21:30.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AliConvertResponseModel {
    private AliConvertEntity data;
    private AliResponseInfoEntity info;
    private boolean ok;
    private String invalidKey;

    public AliConvertResponseModel() {
        super();
    }

    public AliConvertEntity getData() {
        return data;
    }

    public void setData(AliConvertEntity data) {
        this.data = data;
    }

    public AliResponseInfoEntity getInfo() {
        return info;
    }

    public void setInfo(AliResponseInfoEntity info) {
        this.info = info;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getInvalidKey() {
        return invalidKey;
    }

    public void setInvalidKey(String invalidKey) {
        this.invalidKey = invalidKey;
    }
}
