package org.maxwe.tao.server.service.tao.bao.convert;

import org.maxwe.tao.server.service.tao.mami.URLTransEntity;

/**
 * Created by Pengwei Ding on 2017-02-22 21:19.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoTransResponseModel {
    private URLTransEntity data;
    private TaoTransResponseInfoModel info;
    private boolean ok;
    private String invalidKey;

    public TaoTransResponseModel() {
        super();
    }

    public URLTransEntity getData() {
        return data;
    }

    public void setData(URLTransEntity data) {
        this.data = data;
    }

    public TaoTransResponseInfoModel getInfo() {
        return info;
    }

    public void setInfo(TaoTransResponseInfoModel info) {
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
