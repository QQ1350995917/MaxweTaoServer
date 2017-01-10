package org.maxwe.tao.server.service.trade;

import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.history.HistoryEntity;

/**
 * Created by Pengwei Ding on 2017-01-10 10:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ITradeServices {

    /**
     * 对用户授权激活
     *
     * @param agentEntity 上级
     * @param historyEntity 历史备注
     * @return
     */
    boolean grant(AgentEntity agentEntity, HistoryEntity historyEntity);

    /**
     * 对代理进行授权交易
     *
     * @param fromAgentEntity 上级
     * @param toAgentEntity 下级
     * @param historyEntity 历史备注
     * @return
     */
    boolean trade(AgentEntity fromAgentEntity, AgentEntity toAgentEntity, HistoryEntity historyEntity);

}
