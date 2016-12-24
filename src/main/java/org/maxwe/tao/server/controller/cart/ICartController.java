package org.maxwe.tao.server.controller.cart;

import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-09-05 11:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 购物车接口
 */
public interface ICartController {

    /**
     * 404
     */
    void index();

    /**
     * 用户接口
     * 添加产品到购物车
     */
    @Before(SessionInterceptor.class)
    void create();

    /**
     * 用户接口
     * 更新购物车中的产品数量
     */
    @Before(SessionInterceptor.class)
    void update();

    /**
     * 用户接口
     * 读取购物车
     */
    @Before(SessionInterceptor.class)
    void retrieves();

    /**
     * 用户接口
     * 删除购物车产品
     */
    @Before(SessionInterceptor.class)
    void delete();

    /**
     * 管理员接口
     * 管理员后台根据用户信息读取用户购物车
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mRetrieve();
}

