package org.maxwe.tao.server.controller.business;

/**
 * Created by Pengwei Ding on 2017-01-05 16:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IBusinessController {
    /**
     * 获取自己的代理集合
     */
    void agents();

    /**
     * 通过电话号码精确查找代理
     */
    void agent();

    /**
     * 对电话号码对应的账户进行授权
     */
    void grant();

    /**
     * 对电话号码对应的账户转卖授权码
     */
    void trade();
}
