package org.maxwe.tao.server.controller.user.agent;

import org.maxwe.tao.server.controller.user.IAccountController;

/**
 * Created by Pengwei Ding on 2016-12-25 14:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAgentController extends IAccountController {
    void retrieveAgentsByPId(String pid);
}
