package org.maxwe.tao.server.controller.order;

import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-08-31 13:53.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IOrderController {

    /**
     * 404
     */
    void index();

    /**
     * 用户接口
     * 用户创建一个订单
     */
    @Before(SessionInterceptor.class)
    void create();

    /**
     * 匿名用户接口
     * 用户创建一个订单
     */
    void createAnonymous();

    /**
     * 用户接口
     * 用户确认收货
     */
    @Before(SessionInterceptor.class)
    void expressed();

    /**
     * 用户接口
     * 用户读取自己的订单列表
     */
    @Before(SessionInterceptor.class)
    void retrieves();

    /**
     * 用户接口
     * 匿名用户订单查询
     */
    void query();

    /**
     * 管理员接口
     * 根据状态分页查找所有订单
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mRetrieves();

    /**
     * 管理员接口
     * 根据用户分页分状态查询订单
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mRetrievesByUser();

    /**
     * 管理员接口
     * 根据订单发货
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mExpressing();

    /**
     * 管理员接口
     * 管理员查询订单
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mQuery();

}
