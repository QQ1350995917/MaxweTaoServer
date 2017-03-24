package org.maxwe.tao.server.controller.tao;

/**
 * Created by Pengwei Ding on 2017-02-11 12:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 商品控制器
 */
public interface ITaoController {
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
     * 申请最高佣金的接口
     * 精灵使用的转链接口
     * 阿里妈妈接口
     */
    void auction();

    void brands();

    void createBrands();

    /**
     * 站内商品添加
     * 之前生成的查找结果
     */
    void generate();

    /**
     * 站内商品添加
     */
    void publish();

    /**
     * 站内商品查询
     * 供web端后台管理查询使用
     */
    void query();

    /**
     * 删除站内商品
     */
    void delete();

}
