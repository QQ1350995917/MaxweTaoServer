package org.maxwe.tao.server.service.user.agent;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-12-25 15:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAgentServices {
    AgentEntity existAgent(AgentEntity agentEntity);
    AgentEntity createAgent(AgentEntity agentEntity);
    AgentEntity updateAgentPassword(AgentEntity agentEntity);
    AgentEntity updateAgentType(AgentEntity agentEntity);
    AgentEntity retrieveAgentById(String agentId);
    AgentEntity retrieveAgentByCellphone(String cellphone);
    AgentEntity retrieveAgent(AgentEntity agentEntity);
    LinkedList<AgentEntity> retrieveAgentByPId(String agentPId);

}
