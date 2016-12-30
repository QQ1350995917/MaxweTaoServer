package org.maxwe.tao.server.service.user.agent;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-12-25 15:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAgentServices {
    boolean existProxy(String cellphone);
    AgentEntity createProxy(AgentEntity proxyEntity);
    AgentEntity updateProxyPassword(AgentEntity proxyEntity);
    AgentEntity retrieveProxy(AgentEntity proxyEntity);
    AgentEntity retrieveProxy(String proxyId);
    LinkedList<AgentEntity> retrieveProxyByPId(String proxyPId);
    AgentEntity retrieveProxyByCellphone(String cellphone);
}
