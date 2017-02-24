package org.maxwe.tao.server.service.tao.alimama.common;

/**
 * Created by Pengwei Ding on 2017-02-24 20:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AliResponseInfoEntity {
    private String message;
    private String pvid;
    private boolean ok;

    public AliResponseInfoEntity() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPvid() {
        return pvid;
    }

    public void setPvid(String pvid) {
        this.pvid = pvid;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
