package org.maxwe.tao.server.service.tao.goods;

import com.taobao.api.TaobaoResponse;

/**
 * Created by Pengwei Ding on 2017-01-18 15:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class GoodsResponseModel extends TaobaoResponse {
    private String nick;

    public GoodsResponseModel() {
        super();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
