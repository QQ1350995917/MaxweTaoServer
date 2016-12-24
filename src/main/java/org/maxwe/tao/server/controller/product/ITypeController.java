package org.maxwe.tao.server.controller.product;

import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-09-22 16:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ITypeController {
    /**
     * 导向404
     */
    void index();

    /**
     * 用户接口
     * 根据类型的ID读取类型的详情
     */
    void retrieve();

    /**
     * 管理员接口
     * 创建类型
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mCreate();

    /**
     * 管理员接口
     * 更新类型名称
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mUpdate();

    /**
     * 管理员接口
     * 更新状态
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mMark();

    /**
     * 管理员接口
     * 更新类型图片
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mImage();

    /**
     * 管理员接口
     * 删除类型图片
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mImageDelete();

    /**
     * 管理员接口
     * 更新简介
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mSummary();

    /**
     * 管理员接口
     * 更新说明
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mDirections();

    /**
     * 管理员接口
     * 根据产品系列的ID读取简略类型信息集合
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mRetrieves();

    /**
     * 管理员接口
     * 根据产品类型的ID读取类型详情
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class,MenuInterceptor.class})
    void mRetrieve();

}
