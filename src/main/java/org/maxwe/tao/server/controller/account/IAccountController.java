package org.maxwe.tao.server.controller.account;

/**
 * Created by Pengwei Ding on 2017-01-09 17:30.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public interface IAccountController {
    /**
     * 用户接口
     * 检测账号的手机号码是否已经注册
     */
    void exist();

    /**
     * 用户接口
     * 注册接口
     * 用户创建账户接口
     */
    void signup();

    /**
     * 用户接口
     * 用户登录接口
     */
    void signin();

    /**
     * 用户接口
     * 找回密码
     */
    void lost();

    /**
     * 用户接口
     * 修改密码接口
     */
    void password();

    /**
     * 用户接口
     * 用户退出登录接口
     */
    void signout();

    /**
     * 我的信息
     */
    void mine();


}
