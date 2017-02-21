package org.maxwe.tao.server.controller.system;

/**
 * Created by Pengwei Ding on 2017-02-14 21:48.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ISystemController {

    /**
     * 系统信息
     */
    void system();

    /**
     * 系统中授权码数量
     */
    void money();

    /**
     * 追加系统中授权码数量
     */
    void append();

    /**
     * 备份信息
     */
    void backups();

    /**
     * 备份动作
     */
    void backup();

    /**
     * 文件下载
     */
    void download();

    /**
     * 初始化第三方数据
     */
    void initThird();
    /**
     * 第三方数据概要
     */
    void summaryThird();

}
