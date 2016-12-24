package org.maxwe.tao.server.controller.user;

import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAccountController {

    void index();

    /**
     * 用户接口
     * 检测账号的标记是否已经注册
     * 账号标记：网站用户指的是电话号码，微信用户指的是微信的ID，QQ用户指的是QQ的ID
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
     * 用户登录接口
     */
    void login();

    /**
     * 用户接口
     * 用户退出登录接口
     */
    @Before(SessionInterceptor.class)
    void logout();

    /**
     * 用户接口
     * 用户读取自身的用户信息读取账号信息
     */
    @Before(SessionInterceptor.class)
    void retrieve();

    /**
     * 用户接口
     * 忘记密码接口
     */
    void password();

    /**
     * 用户接口
     * 更新账户昵称，性别，生日，地址信息
     */
    @Before(SessionInterceptor.class)
    void update();

    /**
     * 用户接口
     * 用户修改头像
     */
    @Before(SessionInterceptor.class)
    void portrait();

    /**
     * 用户接口
     * 变更账户的手机号码
     */
    @Before(SessionInterceptor.class)
    void phone();

    /**
     * 用户接口
     * 绑定账户
     */
    @Before(SessionInterceptor.class)
    void bind();

    /**
     * 用户接口
     * 解绑账户
     */
    @Before(SessionInterceptor.class)
    void unBind();

    /**
     * 管理员接口
     * 管理员分页读取所有的用户信息
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mRetrieves();

    /**
     * 管理员接口
     * 管理员分页搜索用户信息
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mQueryUsers();

    /**
     * 管理员修改用户状态
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mMark();

    /**
     * 管理员接口
     * 管理员根据用户信息读取其下的账户信息
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mRetrieveAccounts();
}
