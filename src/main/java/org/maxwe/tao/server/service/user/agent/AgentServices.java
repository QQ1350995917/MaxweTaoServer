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
                .set("cellphone", agentEntity.getCellphone());
        if (agentEntity.getType() == 1) {
            agentRecord.set("password1", agentEntity.getPassword1());
        } else if (agentEntity.getType() == 2) {
            agentRecord.set("password2", agentEntity.getPassword2());
        }
        boolean account = Db.save("agent", agentRecord);
        if (account) {
            return agentEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity updateAgentPassword(AgentEntity agentEntity) {
        int count = 0;
        if (agentEntity.getType() == 1) {
            count = Db.update("UPDATE agent SET password1 = ? WHERE agentId = ? ", agentEntity.getPassword1(), agentEntity.getAgentId());
        } else if (agentEntity.getType() == 2) {
            count = Db.update("UPDATE agent SET password2 = ? WHERE agentId = ? ", agentEntity.getPassword2(), agentEntity.getAgentId());
        }
        if (count == 1) {
            return agentEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity updateAgentType(AgentEntity agentEntity) {
        int count = Db.update("UPDATE agent SET password1 = ? , password2 = ? WHERE agentId = ? ", agentEntity.getPassword1(),agentEntity.getPassword2(), agentEntity.getAgentId());
        if (count == 1) {
            return agentEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity retrieveAgentById(String agentId) {
        List<Record> agentRecords = Db.find("SELECT * FROM agent WHERE agentId = ? ", agentId);
        if (agentRecords.size() < 1) {
            return null;
        }
        Map<String, Object> accountMap = agentRecords.get(0).getColumns();
        AgentEntity agentEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
        return agentEntity;
    }

    @Override
    public AgentEntity retrieveAgentByCellphone(String cellphone) {
        List<Record> agentRecords = Db.find("SELECT * FROM agent WHERE cellphone = ? ", cellphone);
        if (agentRecords.size() < 1) {
            return null;
        }
        Map<String, Object> accountMap = agentRecords.get(0).getColumns();
        AgentEntity agentEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
        return agentEntity;
    }

    @Override
    public AgentEntity retrieveAgent(AgentEntity agentEntity) {
        List<Record> agentRecords = null;
        if (agentEntity.getType() == 1) {
            agentRecords = Db.find("SELECT * FROM agent WHERE password1 = ? AND cellphone = ? ", agentEntity.getPassword1(), agentEntity.getCellphone());
        } else if (agentEntity.getType() == 2) {
            agentRecords = Db.find("SELECT * FROM agent WHERE password2 = ? AND cellphone = ? ", agentEntity.getPassword1(), agentEntity.getCellphone());
        }
        if (agentRecords == null || agentRecords.size() < 1) {
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
