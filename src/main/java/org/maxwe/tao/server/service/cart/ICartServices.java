package org.maxwe.tao.server.service.cart;

/**
 * Created by Pengwei Ding on 2016-09-05 11:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ICartServices {

//    /**
//     * 用户接口
//     * 判断用户添加的商品是否已经存在于购物车中
//     *
//     * @param cartEntity 购物车产品对象
//     * @return success 购物车产品对象 fail null
//     */
//    CartEntity exist(CartEntity cartEntity);
//
//    /**
//     * 用户接口
//     * 向购物车中添加一个产品
//     *
//     * @param cartEntity 购物车产品对象
//     * @return 购物车产品对象 fail null
//     */
//    CartEntity create(CartEntity cartEntity);
//
//    /**
//     * 用户接口
//     * 用户修改购物车产品的数量
//     *
//     * @param cartEntity 购物车产品对象
//     * @return 购物车产品对象 fail null
//     */
//    CartEntity updateAmount(CartEntity cartEntity);
//
//    /**
//     * 用户接口
//     * 用户删除购物车中的产品
//     *
//     * @param accountEntities 账户对象集合
//     * @param cartEntity 购物车产品对象
//     * @return 购物车产品对象 fail null
//     */
//    CartEntity deleteById(LinkedList<? extends AccountEntity> accountEntities, CartEntity cartEntity);
//
//    /**
//     * 用户接口
//     * 用户读取购物车产品集合
//     *
//     * @param accountId 账号ID
//     * @return 购物车产品对象集合 fail null
//     */
//    LinkedList<CartEntity> retrievesByAccountId(String accountId);
//
//    /**
//     * 用户接口
//     * 用户读取购物车产品集合
//     *
//     * @param accountEntities 用户名下的账号集合
//     * @return 购物车产品对象集合 fail null
//     */
//    LinkedList<CartEntity> retrievesByAccounts(LinkedList<? extends AccountEntity> accountEntities,int pageIndex,int counter);
//
//    /**
//     * 用户接口
//     * 通过购物车ID查找
//     *
//     * @param accountId 账户ID
//     * @param mappingId 购物车产品ID
//     * @return 购物车产品对象 fail null
//     */
//    CartEntity retrieveById(String accountId, String mappingId);
//
//    /**
//     * 用户接口
//     * 根据账户和购物车映射IDs批量查找
//     *
//     * @param accountEntities 用户下的账户集合
//     * @param mappingIds      产品购物车映射ID
//     * @return 购物车产品对象集合 fail null
//     */
//    LinkedList<CartEntity> retrievesByIds(LinkedList<? extends AccountEntity> accountEntities, String[] mappingIds);
//
//    /**
//     * 用户接口
//     * 根据用户IDs和订单IDs查找
//     *
//     * @param accountEntities 账户集合
//     * @param orderId    订单ID
//     * @return 购物车产品对象集合 fail null
//     */
//    LinkedList<CartEntity> retrievesByOrderId(LinkedList<? extends AccountEntity> accountEntities, String orderId);
//
//    /**
//     * 用户接口
//     * 用户下单时把购物车中的对应关系绑定到订单上
//     *
//     * @param orderEntity 订单对象
//     * @param mappingIds  映射IDs
//     * @return success true fail false
//     */
//    boolean attachToOrder(OrderEntity orderEntity, String[] mappingIds);
//
//    /**
//     * 管理员接口
//     * 根据账号读取购物车总数量
//     * @return
//     */
//    int countByAccounts(LinkedList<? extends AccountEntity> accountEntities);
//
//    /**
//     * 管理员接口
//     * 根据用户IDs和订单IDs分页查找对应关系
//     *
//     * @param accountEntities 账户集合
//     * @param orderId    订单ID
//     * @return 购物车产品对象集合 fail null
//     */
//    LinkedList<CartEntity> mRetrievesByOrderId(LinkedList<? extends AccountEntity> accountEntities, String orderId);
//
//    /**
//     * 管理员接口
//     * 根据用户IDs和订单IDs分页查找对应关系
//     *
//     * @param orderId 订单ID
//     * @return 购物车产品对象集合 fail null
//     */
//    LinkedList<CartEntity> mRetrievesByOrderId(String orderId);
}
