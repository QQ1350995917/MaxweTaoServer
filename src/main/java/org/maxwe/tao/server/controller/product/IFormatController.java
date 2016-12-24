package org.maxwe.tao.server.controller.product;

import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-09-22 16:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IFormatController {
    /**
     * 导向404
     */
    void index();

    /**
     * 用户接口
     * 根据类型的ID读取规格的集合
     */
    void retrieves();

    /**
     * 用户接口
     * 根据规格的ID读取规格的详情
     */
    void retrieve();

    /**
     * 用户接口
     * 根据规格的ID读取产品树
     */
    void retrieveTree();

    /**
     * 用户接口
     * 根据规格ID读取倒置产品树
     */
    void retrieveTreeInversion();

    /**
     * 用户接口
     * 读取推荐列表
     */
    void recommends();

    /**
     * 管理员接口
     * 创建新的规格
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mCreate();

    /**
     * 管理员接口
     * 更新规格信息
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mUpdate();

    /**
     * 管理员接口
     * 状态更新规格状态
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mMark();

    /**
     * 管理员接口
     * 标记权重
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mKingWeight();

    /**
     * 管理员接口
     * 交换权重
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mSwapWeight();

    /**
     * 管理员接口
     * 读取权重列表
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mWeights();

    /**
     * 管理员接口
     * 根据类型的ID读取类型下的规格
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mRetrieves();

    /**
     * 管理员接口
     * 根据规格的ID读取规格的详情
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mRetrieve();

}
