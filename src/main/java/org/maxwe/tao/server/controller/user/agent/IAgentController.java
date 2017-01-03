package org.maxwe.tao.server.controller.user.agent;

import org.maxwe.tao.server.controller.user.IAccountController;

/**
 * Created by Pengwei Ding on 2016-12-25 14:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAgentController extends IAccountController {
    /**
     * 获取自己的信息
     */
    void retrieveAgent();

    /**
     * 获取自己的代理集合
     */
    void retrieveSubAgents();

    /**
     * 通过电话号码查找代理
     */
    void retrieveAgentByCellphone();

    /**
     * 对电话号码对应的账户进行授权
     */
    void grantToCellphone();

    /**
     * 对电话号码对应的账户转卖授权码
     */
    void tradeCodesToCellphone();
}
