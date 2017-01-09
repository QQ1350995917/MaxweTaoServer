package org.maxwe.tao.server.service.account.agent;

import org.maxwe.tao.server.service.history.HistoryEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-12-25 15:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAgentServices {
    AgentEntity create(AgentEntity agentEntity);
    AgentEntity[] createReach(AgentEntity agentMasterEntity,AgentEntity agentSubEntity);
    AgentEntity updatePassword(AgentEntity agentEntity);
    AgentEntity updateName(AgentEntity agentEntity);
    AgentEntity updateNamed(AgentEntity agentEntity);
    AgentEntity updateGrant(AgentEntity agentEntity,HistoryEntity historyEntity);
    AgentEntity[] updateReach(AgentEntity agentMasterEntity,AgentEntity agentSubEntity);
    AgentEntity[] updateTrade(AgentEntity agentMasterEntity,AgentEntity agentSubEntity,int codes,HistoryEntity historyEntity);
    AgentEntity updateBank(AgentEntity agentEntity);
    AgentEntity retrieveById(String id);
    AgentEntity retrieveByMark(String mark);
    AgentEntity retrieveByCellphone(String cellphone);
    LinkedList<AgentEntity> retrieveByPid(String pId,int pageIndex,int pageSize);
}
