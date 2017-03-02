package org.maxwe.tao.server.controller.version;

/**
 * Created by Pengwei Ding on 2017-01-06 18:05.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public interface IVersionController {

    /**
     * 客户端用接口
     * 查询版本信息
     */
    void version();

    /**
     * 管理端用接口
     * 查询所有信息
     */
    void versions();

    /**
     * 管理端用接口
     * 创建版本信息
     */
    void create();

}
