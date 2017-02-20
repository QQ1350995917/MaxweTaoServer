package org.maxwe.tao.server.service.tao.jidi;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-20 22:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class JiDiGoodsModel {
    private String status;
    private String msg;
    private List<JiDiGoodsEntity> data;

    public JiDiGoodsModel() {
        super();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<JiDiGoodsEntity> getData() {
        return data;
    }

    public void setData(List<JiDiGoodsEntity> data) {
        this.data = data;
    }
}
