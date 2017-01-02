package org.maxwe.tao.server.service.user.agent;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-12-25 15:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AgentServices implements IAgentServices {
    @Override
    public AgentEntity existAgent(AgentEntity agentEntity) {
        List<Record> records = Db.find("SELECT * FROM agent WHERE cellphone = ? ", agentEntity.getCellphone());
        if (records.size() > 0) {
            Map<String, Object> accountMap = records.get(0).getColumns();
            return JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity createAgent(AgentEntity agentEntity) {
        Record agentRecord = new Record()
                .set("agentId", agentEntity.getAgentId())
                .set("cellphone", agentEntity.getCellphone())
                .set("password", agentEntity.getPassword())
                .set("type", agentEntity.getType());
        boolean account = Db.save("agent", agentRecord);
        if (account) {
            return agentEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity updateAgentPassword(AgentEntity agentEntity) {
        int count = Db.update("UPDATE agent SET password = ? WHERE agentId = ? ", agentEntity.getPassword(), agentEntity.getAgentId());
        if (count == 1) {
            return agentEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity updateAgentType(AgentEntity agentEntity) {
        int count = Db.update("UPDATE agent SET type = ? WHERE agentId = ? ", agentEntity.getType(), agentEntity.getAgentId());
        if (count == 1) {
            return agentEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity retrieveAgentById(String agentId) {
        List<Record> agentRecords = Db.find("SELECT * FROM agent WHERE agentId = ? ", agentId);
        Map<String, Object> accountMap = agentRecords.get(0).getColumns();
        AgentEntity agentEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
        return agentEntity;
    }

    @Override
    public AgentEntity retrieveAgentByCellphone(String cellphone) {
        List<Record> agentRecords = Db.find("SELECT * FROM agent WHERE cellphone = ? ", cellphone);
        Map<String, Object> accountMap = agentRecords.get(0).getColumns();
        AgentEntity agentEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
        return agentEntity;
    }

    @Override
    public AgentEntity retrieveAgent(AgentEntity agentEntity) {
        List<Record> agentRecords = Db.find("SELECT * FROM agent WHERE password = ? AND cellphone = ? ", agentEntity.getPassword(), agentEntity.getCellphone());
        if (agentRecords.get(0) == null) {
            return null;
        }
        Map<String, Object> accountMap = agentRecords.get(0).getColumns();
        AgentEntity resultAgentEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
        return resultAgentEntity;
    }

    @Override
    public LinkedList<AgentEntity> retrieveAgentByPId(String agentPId) {
        List<Record> agentRecords = Db.find("SELECT * FROM agent WHERE agentPId = ? ", agentPId);
        LinkedList<AgentEntity> agentEntities = new LinkedList<>();
        for (Record agentRecord : agentRecords) {
            Map<String, Object> accountMap = agentRecord.getColumns();
            AgentEntity agentEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
            agentEntities.add(agentEntity);
        }
        return agentEntities;
    }
}
