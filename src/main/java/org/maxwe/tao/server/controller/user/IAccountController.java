package org.maxwe.tao.server.controller.user;

import com.jfinal.aop.Before;
import org.maxwe.tao.server.interceptor.TokenInterceptor;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAccountController {

    /**
     * 用户接口
     * 检测账号的手机号码是否已经注册
     */
    void exist();

    /**
     * 用户接口
     * 获取短信验证码
     */
    void smsCode();

    /**
     * 用户接口
     * 注册接口
     * 用户创建账户接口
     */
    void create();

    /**
     * 用户接口
     * 找回密码
     */
    void lost();

    /**
     * 用户接口
     * 用户登录接口
     */
    void login();

    /**
     * 用户接口
     * 用户退出登录接口
     */
    @Before(TokenInterceptor.class)
    void logout();

    /**
     * 获取自己的信息
     */
    void agent();

    /**
     * 用户接口
     * 忘记密码接口
     */
    void password();
}
