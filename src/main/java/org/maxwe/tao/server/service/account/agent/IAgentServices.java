package org.maxwe.tao.server.service.account.agent;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-12-25 15:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAgentServices {
    AgentEntity create(AgentEntity agentEntity);
    AgentEntity updatePassword(AgentEntity agentEntity);
    AgentEntity updateName(AgentEntity agentEntity);
    AgentEntity updateNamed(AgentEntity agentEntity);

    /**
     * 下级请求加入组织结构
     * @param branchEntity 下级
     * @return
     */
    boolean askForReach(AgentEntity branchEntity);

    /**
     * 上级同意或拒绝下级加入组织的请求
     * @param branchEntity 下级
     * @return
     */
    boolean updateReach(AgentEntity branchEntity);

    /**
     * 代理更新自己的银行账号
     * @param agentEntity
     * @return
     */
    AgentEntity updateBank(AgentEntity agentEntity);
    AgentEntity retrieveById(String id);
    AgentEntity retrieveByMark(String mark);
    AgentEntity retrieveByCellphone(String cellphone);

    /**
     * 上级读取自己下边的代理
     * @param pId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    LinkedList<AgentEntity> retrieveByPid(String pId,int pageIndex,int pageSize);

    LinkedList<AgentEntity> retrieveAll(int pageIndex,int pageSize);
    int retrieveAllSum();

    LinkedList<AgentEntity> retrieveByTop();

    boolean appendCodes(AgentEntity agentEntity,int haveCodes);
}
