package org.maxwe.tao.server.service.order;

import org.maxwe.tao.server.service.user.AccountEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-31 13:53.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IOrderServices {

    /**
     * 用户接口
     * 创建订单
     *
     * @param orderEntity 订单对象
     * @return success 订单对象 fail null
     */
    OrderEntity create(OrderEntity orderEntity);

    /**
     * 用户接口
     * 分页分状态读取名下账户的订单
     *
     * @param accountEntities 账户集合
     * @param status          订单状态
     * @param pageIndex       当前页码
     * @param counter         每页条数
     * @return success 订单对象集合 fail null
     */
    LinkedList<OrderEntity> retrievesByAccounts(LinkedList<? extends AccountEntity> accountEntities, int status, int pageIndex, int counter);

    /**
     * 用户接口
     * 用户确认收货
     *
     * @param accountEntities 账户对象集合
     * @param orderEntity     订单对象
     * @return success 订单对象 fail null
     */
    OrderEntity expressed(LinkedList<? extends AccountEntity> accountEntities, OrderEntity orderEntity);

    /**
     * 用户接口
     * 匿名用户订单查询
     *
     * @param key       关键字
     * @param pageIndex 页码
     * @param counter   每页条数
     * @return success 订单对象集合 fail null
     */
    LinkedList<OrderEntity> query(String key, int pageIndex, int counter);

    /**
     * 管理员接口
     * 获取总的记录数
     *
     * @param status 状态
     * @return
     */
    int mCount(int status);

    /**
     * 管理员接口
     * 管理员读取订单集合
     *
     * @param status    订单状态
     * @param pageIndex 页码
     * @param counter   每页条数
     * @return success 订单对象集合 fail null
     */
    LinkedList<OrderEntity> mRetrieves(int status, int pageIndex, int counter);

    /**
     * 管理员接口
     * 根据用户名统计订单数量
     *
     * @param accountEntities 账户对象集合
     * @param status 订单状态
     * @return success 数量 fail 0
     */
    int mCountByUser(LinkedList<? extends AccountEntity> accountEntities, int status);

    /**
     * 管理员接口
     * 管理员根据用户分页分类读取订单
     *
     * @param accountEntities 账户集合
     * @param status          订单状态
     * @param pageIndex       页码
     * @param counter         每页条数
     * @return success 订单对象集合 fail null
     */
    LinkedList<OrderEntity> mRetrievesByUser(LinkedList<? extends AccountEntity> accountEntities, int status, int pageIndex, int counter);

    /**
     * 管理员接口
     * 后台发货
     *
     * @param orderEntity
     * @return
     */
    OrderEntity mExpressing(OrderEntity orderEntity);
}
