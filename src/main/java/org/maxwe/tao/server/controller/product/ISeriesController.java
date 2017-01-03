package org.maxwe.tao.server.controller.product;

import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-09-22 16:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 产品系列控制层接口
 * 使用m标记的接口为后台接口，需要检测相关权限才能访问数据层接口
 */
public interface ISeriesController {
    /**
     * 导向404
     */
    void index();

    /**
     * 用户接口
     * 用户根据系列的ID读取系列信息
     */
    void retrieve();

    /**
     * 用户接口
     * 读取系列进行显示
     * 应用于首页，系列等页面
     */
    void retrieves();

    /**
     * 用户接口
     * 根据系列的ID读取产品树信息
     */
    void tree();

    /**
     * 用户接口
     * 根据系列的ID读取倒置的产品树信息
     */
    void treeInversion();

    /**
     * 管理员接口
     * 创建新系列
     */
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mCreate();

    /**
     * 管理员接口接口
     * 更新系列名称
     */
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mUpdate();

    /**
     * 管理员接口接口
     * 更新系列状态
     * 有启用状态向禁用状态修改时候要修改旗下的全部类型为禁用，反向不做修改
     * 有启用/禁用状态向删除状态做修改时候要删除旗下的全部的类型为删除状态
     */
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mMark();

    /**
     * 管理员接口
     * 读取系列集合
     */
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mRetrieves();
}
