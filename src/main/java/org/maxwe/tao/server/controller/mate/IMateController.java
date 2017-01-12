package org.maxwe.tao.server.controller.mate;

/**
 * Created by Pengwei Ding on 2017-01-09 18:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IMateController {
    /**
     * 根据代理的ID查找代理
     * 请求上级激活我的账号
     */
    void query();

    /**
     * 批准下级的请求
     */
    void grant();

    /**
     * 拒绝下级的请求
     */
    void reject();

    /**
     * 下级列表
     */
    void mates();
}
