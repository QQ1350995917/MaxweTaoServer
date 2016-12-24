package org.maxwe.tao.server.service.receiver;

import org.maxwe.tao.server.service.user.AccountEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-31 14:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IReceiverService {

    /**
     * 用户接口
     * 读取账户下的所有状态正常的收货人信息
     *
     * @param accountEntity 用户下的账户
     * @return fail null success 收货人信息集合
     */
    LinkedList<ReceiverEntity> retrieves(AccountEntity accountEntity);

    /**
     * 用户接口
     * 读取账户下的所有状态正常的收货人信息
     *
     * @param accountEntities 用户下的账户集合
     * @return fail null success 收货人信息集合
     */
    LinkedList<ReceiverEntity> retrieves(LinkedList<? extends AccountEntity> accountEntities);

    /**
     * TODO 待处理接口，目前被查询接口调用，应该处理为全文检索
     *
     * @param receiverId
     * @return
     */
    ReceiverEntity retrieveById(String receiverId);

    /**
     * 用户接口
     * 创建一个收货人
     *
     * @param accountEntity  收货人归属的账户对象
     * @param receiverEntity 收货人对象
     * @return fail null success 返回收货人对象
     */
    ReceiverEntity create(AccountEntity accountEntity, ReceiverEntity receiverEntity);

    /**
     * 用户接口
     * 更新一个收货人，更新方式为保留其ID、accountId、status、createTime，更新其余所有属性。
     *
     * @param accountEntities 用户下的账户对象集合
     * @param receiverEntity 收货人对象
     * @return fail null success 返回收货人对象
     */
    ReceiverEntity updateById(LinkedList<? extends AccountEntity> accountEntities,ReceiverEntity receiverEntity);

    /**
     * 用户接口
     * 删除一个收货人，删除方式为将其status变更为-1
     *
     * @param accountEntities 用户名下收货人账户的集合
     * @param receiverId 收货人的ID
     * @return fail null success 返回收货人对象
     */
    ReceiverEntity deleteById(LinkedList<? extends AccountEntity> accountEntities,String receiverId);

    /**
     * 用户接口
     * 设置用户名下的一个收货人为默认收货人，设置方式为将其用户名下的status重置为1，然后将当前收货人status设置为2
     *
     * @param accountEntities 当前用户下账户的集合
     * @param receiverEntity 当前收货人对象
     * @return fail null success 返回要设置的收货人对象
     */
    ReceiverEntity kingReceiverInUser(LinkedList<? extends AccountEntity> accountEntities, ReceiverEntity receiverEntity);

    /**
     * 管理员接口
     * 在后台读取账户名下的收货人信息
     *
     * @param accountEntity 用户账户
     * @return fail null success 收货人信息集合
     */
    LinkedList<ReceiverEntity> mRetrieves(AccountEntity accountEntity);

    /**
     * 管理员接口
     * 在后台读取账户名下的收货人信息
     *
     * @param accountEntities 用户账户
     * @return fail null success 收货人信息集合
     */
    LinkedList<ReceiverEntity> mRetrieves(LinkedList<AccountEntity> accountEntities);

}
