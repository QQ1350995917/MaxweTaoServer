package org.maxwe.tao.server.controller.page;

/**
 * Created by Pengwei Ding on 2016-08-19 16:44.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 前端页面接口，所有有关页面的跳转分发接口
 */
public interface IPageController {
    /**
     * 默认情况下的页面跳转
     * 指定跳转页面异常情况下的跳转
     */
    void index();

    /**
     * short of Page Series
     * 显示添加管理账号的面板
     */
    void addm();

    /**
     * short of Page Detail
     * 跳转到详情页面
     */
    void password();

    /**
     * short of Page Billing
     * 跳转到系统码量页面
     */
    void money();

    /**
     * short of Page Look
     * 跳转到订单查询页面
     */
    void grant();

    /**
     * short of Page Mine
     * 跳转到级别界面
     */
    void level();

    /**
     * short of Page Protocol
     *
     */
    void users();

    /**
     * 管理员登录界面
     */
    void ml();

    /**
     * 管理员界面
     * 访问该页面时候要进行Session类型的检测，管理员类型的Session才允许访问该页面
     */
    void frame();


}
