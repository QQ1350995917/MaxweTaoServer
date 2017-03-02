package org.maxwe.tao.server.controller.mate;

/**
 * Created by Pengwei Ding on 2017-01-09 18:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public interface IMateController {


    /**
     * 请求上级激活我的账号
     */
    void beg();

    /**
     * 批准下级的请求
     */
    void grant();

    /**
     * 拒绝下级的请求
     */
    void reject();

    /**
     * 查找上级信息
     */
    void leader();

    /**
     * 根据自己的id查找
     * 用于获取下级列表
     */
    void mates();

}
