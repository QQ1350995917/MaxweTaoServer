package org.maxwe.tao.server.controller.account.user;


import org.maxwe.tao.server.controller.account.IAccountController;

/**
 * Created by Pengwei Ding on 2017-01-09 14:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public interface IUserController extends IAccountController {

    /**
     * 用户接口
     * 激活账号
     */
    void active();

    /**
     * 用户更新申请加入推广计划的理由
     */
    void updateReason();

    /**
     * 用户更新推广分享说辞
     */
    void updateRhetoric();

    /**
     * 用户推荐信息接口
     */
    void referenceInfo();

    /**
     * 用户推荐注册接口界面
     */
    void reference();

    /**
     * 用户推荐注册接口
     */
    void referenceSignUp();


}
