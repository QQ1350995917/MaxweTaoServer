package org.maxwe.tao.server.controller.trade;

/**
 * Created by Pengwei Ding on 2017-01-09 18:48.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public interface ITradeController {
    /**
     * 生成一个授权码
     * 授权给普通用户使用
     */
    void grant();

    /**
     * 交易授权码
     * 交易给代理使用
     */
    void trade();
}
