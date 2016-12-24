package org.maxwe.tao.server.controller.menu;

import org.maxwe.tao.server.interceptor.SessionInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-07-30 14:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IMenuController {
    /**
     * 404
     */
    void index();

    /**
     * 普通管理员接口
     * 查询符合权限的菜单
     */
    @Before({SessionInterceptor.class})
    void mRetrieves();

}
