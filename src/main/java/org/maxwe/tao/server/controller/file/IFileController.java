package org.maxwe.tao.server.controller.file;

import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-11-01 15:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IFileController {
    void index();

    /**
     * 管理员接口
     * 管理员上传类型图片
     */
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mTypeCover();

    /**
     * 管理员接口
     * 管理员上传类型介绍图片
     */
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mTypeDirectionImage();
}
