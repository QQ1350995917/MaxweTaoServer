package org.maxwe.tao.server.service.tao.alimama.goods;

import org.maxwe.tao.server.service.tao.alimama.common.AliResponseDataEntity;
import org.maxwe.tao.server.service.tao.alimama.common.AliResponseInfoEntity;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-02-24 20:41.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AliGoodsResponseModel implements Serializable {
    private AliResponseDataEntity data;
    private AliResponseInfoEntity info;
    private boolean ok;
    private String invalidKey;

    public AliGoodsResponseModel() {
        super();
    }

    public AliResponseDataEntity getData() {
        return data;
    }

    public void setData(AliResponseDataEntity data) {
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
