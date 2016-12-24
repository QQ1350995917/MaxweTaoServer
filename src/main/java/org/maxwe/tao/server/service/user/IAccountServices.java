package org.maxwe.tao.server.service.user;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAccountServices {

    /**
     * 用户接口
     * 用户注册、更换身份标记时候查看目标身份是否已经被注册
     *
     * @param identity 目标身份，如电话号码，微信ID或QQ ID
     * @return false 目标可用，true 目标不可用
     */
    boolean existAccount(String identity);

    /**
     * 用户接口
     * 创建新的账户同时创建用户ID
     *
     * @param accountEntity 账户对象
     * @return fail null success 账户对象
     */
    AccountEntity create(AccountEntity accountEntity);

    /**
     * 用户接口
     * 通过账户的身份ID读取账户（登录接口）
     *
     * @param accountEntity 账户身份ID
     * @return fail null success 账户对象
     */
    AccountEntity retrieve(AccountEntity accountEntity);

    /**
     * 用户接口
     * 根据账户的身份更新账户密码
     *
     * @param accountEntity 账户对象
     * @return fail null success 账户对象
     */
    AccountEntity updatePassword(AccountEntity accountEntity);

    /**
     * 用户接口
     * 更新账户昵称性别地址信息
     *
     * @param accountEntity 账户对象
     * @return fail null success 账户对象
     */
    AccountEntity update(AccountEntity accountEntity);

    /**
     * 用户接口
     * 修改账户的头像
     *
     * @param accountEntity 账户对象
     * @return fail null success 账户对象
     */
    AccountEntity portrait(AccountEntity accountEntity);

    /**
     * 用户接口（仅用户手机账户的修改）
     * 修改账户的电话号码
     *
     * @param accountEntity 账户对象
     * @return fail null success 账户对象
     */
    AccountEntity updateIdentity(AccountEntity accountEntity);

    /**
     * 用户接口
     * 把一个账户与当前账户绑定
     *
     * @param targetAccountEntity  目标账户
     * @param currentAccountEntity 当前账户
     * @return fail null success 当前账户的用户对象
     */
    UserEntity bind(AccountEntity targetAccountEntity, AccountEntity currentAccountEntity);

    /**
     * 用户接口
     * 把一个账户与当前账户解绑
     *
     * @param targetAccountEntity  目标账户
     * @param currentAccountEntity 当前账户
     * @return fail null success 当前账户的用户对象
     */
    UserEntity unBind(AccountEntity targetAccountEntity, AccountEntity currentAccountEntity);

    /**
     * 用户接口
     * 通过用户ID读取其下的所有账户
     *
     * @param userId 用户ID
     * @return fail null success 当前用户的账户集合
     */
    LinkedList<AccountEntity> retrieveByUserId(String userId);

    /**
     * 管理员接口
     * 读取总用户数量
     *
     * @return
     */
    int mCount();

    /**
     * 管理员接口
     * 分页读取所有的用户
     *
     * @param page    页码
     * @param counter 每页条数
     * @return fail null success 系统用户集合
     */
    LinkedList<UserEntity> mRetrieveUsers(int page, int counter);

    /**
     * 管理员接口
     * 根据关键字分页搜索
     *
     * @param key     关键字
     * @param page    页码
     * @param counter 每页条数
     * @return fail null success 系统用户集合
     */
    LinkedList<UserEntity> mQueryUsers(String key, int page, int counter);

    /**
     * 管理员接口
     * 把用户以及其下的账户禁用
     *
     * @param userEntity 用户对象
     * @return fail null success 用户对象
     */
    UserEntity mBlock(UserEntity userEntity);

    /**
     * 管理员接口
     * 把用户以及其下的账户启用
     *
     * @param userEntity 用户对象
     * @return fail null success 用户对象
     */
    UserEntity mUnBlock(UserEntity userEntity);


    /**
     * 管理员接口
     * 根据用户的ID读取账户信息
     *
     * @param userId 用户ID
     * @return fail null success 账户对象集合
     */
    LinkedList<AccountEntity> mRetrieveByUserId(String userId);


    /**
     * 通过账户ID读取用户信息
     *
     * @param account
     * @return 返回查询到的用户或null
     */
    UserEntity retrieveUserByAccountId(String account);


}
