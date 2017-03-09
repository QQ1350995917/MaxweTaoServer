package org.maxwe.tao.server.controller.tao;

/**
 * Created by Pengwei Ding on 2017-02-11 12:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public interface ITaoController {
//    /**
//     * 商品查询
//     */
//    void query();
//    /**
//     * 后去淘口令
//     */
//    void pwd();
//    /**
//     * 转链
//     */
//    void convert();
//
//    /**
//     * 通过ID寻找商品详情
//     */
//    void find();

    /**
     * 陶精灵使用的商品接口
     * 阿里妈妈接口
     */
    void search();

    /**
     * 陶精灵使用的转链接口
     * 阿里妈妈接口
     */
    void auction();

    void brands();

    void createBrands();
}
