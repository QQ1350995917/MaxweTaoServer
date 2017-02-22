package org.maxwe.tao.server.service.tao.bao.convert;

/**
 * Created by Pengwei Ding on 2017-02-22 21:20.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoTransResponseInfoModel {
    private String message;
    private boolean ok;

    public TaoTransResponseInfoModel() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
