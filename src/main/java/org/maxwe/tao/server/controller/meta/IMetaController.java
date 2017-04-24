package org.maxwe.tao.server.controller.meta;

/**
 * Created by Pengwei Ding on 2017-01-09 22:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public interface IMetaController {
    /**
     * 获取手机验证码
     */
    void smsCode();

    void createLink();

    void deleteLink();

    void listLinks();
}
