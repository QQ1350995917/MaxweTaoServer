package org.maxwe.tao.server.controller.account.manager;

/**
 * Created by Pengwei Ding on 2017-02-09 20:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IManagerController {
    /**
     * 到登录页面
     */
    void login();
    /**
     * 到登录页面
     */
    void logout();

    /**
     * 登录提交验证
     */
    void access();
    /**
     * 到管理页面
     */
    void manage();

    /**
     * 管理账户列表
     */
    void accounts();
    /**
     * 创建账户
     */
    void create();
    /**
     * 账户授权
     */
    void grant();
    /**
     * 账户修改密码
     */
    void password();
    /**
     * 管理员重置密码
     */
    void reset();
    /**
     * 账户禁用
     */
    void block();
    /**
     * 账户解除禁用
     */
    void unBlock();
    /**
     * 账户删除
     */
    void delete();

    /**
     * 代理列表
     */
    void agents();

    /**
     * 商品列表
     */
    void goods();
    /**
     * 商品发布
     */
    void publish();

}
