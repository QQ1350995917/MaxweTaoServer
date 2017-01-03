package org.maxwe.tao.server.controller.link;

import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ILinkController {

    /**
     * 404
     */
    void index();

    /**
     * 用户接口
     * 读取链接树
     */
    void retrieves();

    /**
     * 管理员接口
     * 创建链接分类
     */
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mCreate();

    /**
     * 管理员接口
     * 更新链接分类
     */
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mUpdate();

    /**
     * 管理员接口
     * 更新链接分类状态
     */
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mMark();

    /**
     * 管理员接口
     * 交换链接分类的顺序
     */
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mSwap();

    /**
     * 管理员接口
     * 读取链接集合
     */
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mRetrieves();

}
