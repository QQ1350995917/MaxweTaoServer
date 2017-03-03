package org.maxwe.tao.server.service.account.agent;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.sql.Timestamp;
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
    public AgentEntity create(AgentEntity agentEntity) {
        Record agentRecord = new Record()
                .set("cellphone", agentEntity.getCellphone())
                .set("password", agentEntity.getPassword());
        boolean isSave = Db.save("agent", agentRecord);
        if (isSave) {
            return retrieveByCellphone(agentEntity.getCellphone());
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity updatePassword(AgentEntity agentEntity) {
        int count = Db.update("UPDATE agent SET password = ? WHERE id = ? ",
                agentEntity.getPassword(), agentEntity.getId());
        if (count == 1) {
            return agentEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity updateName(AgentEntity agentEntity) {
        int count = Db.update("UPDATE agent SET name = ? WHERE id = ? ",
                agentEntity.getName(), agentEntity.getId());
        if (count == 1) {
            return agentEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity updateNamed(AgentEntity agentEntity) {
        int count = Db.update("UPDATE agent SET named = ? WHERE id = ? ",
                agentEntity.getNamed(), agentEntity.getId());
        if (count == 1) {
            return agentEntity;
        } else {
            return null;
        }
    }

    @Override
    public boolean askForReach(AgentEntity agentSubEntity) {
        int count = Db.update("UPDATE agent SET pId = ? , pIdTime = ? ,weChat = ?, weight = 1  WHERE id = ? ",
                agentSubEntity.getpId(), new Timestamp(System.currentTimeMillis()),agentSubEntity.getWechat(), agentSubEntity.getId());
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean updateReach(AgentEntity agentSubEntity) {
        int count = Db.update("UPDATE agent SET pId = ? , reach = ? , weight = 0, reachTime = ?  WHERE id = ? ",
                agentSubEntity.getpId(), agentSubEntity.getReach(), new Timestamp(System.currentTimeMillis()), agentSubEntity.getId());
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AgentEntity updateBank(AgentEntity agentEntity) {
        int count = Db.update("UPDATE agent SET trueName = ? , zhifubao = ? WHERE id = ? ",
                agentEntity.getTrueName(), agentEntity.getZhifubao(), agentEntity.getId());
        if (count == 1) {
            return agentEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity retrieveById(int id) {
        List<Record> records = Db.find("SELECT * FROM agent WHERE id = ? ", id);
        if (records != null && records.size() > 0 && records.get(0) != null) {
            Map<String, Object> accountMap = records.get(0).getColumns();
            return JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity retrieveByCellphone(String cellphone) {
        List<Record> records = Db.find("SELECT * FROM agent WHERE cellphone = ? ", cellphone);
        if (records != null && records.size() > 0 && records.get(0) != null) {
            Map<String, Object> accountMap = records.get(0).getColumns();
            return JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<AgentEntity> retrieveByPid(int pId, int pageIndex, int pageSize) {
        List<Record> agentRecords = Db.find("SELECT * FROM agent WHERE pId = ? AND id != pId ORDER BY weight DESC , createTime DESC limit ? , ?",
                pId, pageIndex * pageSize, pageSize);
        LinkedList<AgentEntity> agentEntities = new LinkedList<>();
        for (Record agentRecord : agentRecords) {
            Map<String, Object> accountMap = agentRecord.getColumns();
            AgentEntity agentEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
            agentEntities.add(agentEntity);
        }
        return agentEntities;
    }

    @Override
    public LinkedList<AgentEntity> retrieveAll(int pageIndex, int pageSize) {
        List<Record> agentRecords = Db.find("SELECT * FROM agent ORDER BY createTime DESC limit ? , ?", pageIndex * pageSize, pageSize);
        LinkedList<AgentEntity> agentEntities = new LinkedList<>();
        for (Record agentRecord : agentRecords) {
            Map<String, Object> accountMap = agentRecord.getColumns();
            AgentEntity agentEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
            agentEntities.add(agentEntity);
        }
        return agentEntities;
    }

    @Override
    public int retrieveAllSum() {
        return Db.find("SELECT * FROM agent ").size();
    }

    @Override
    public LinkedList<AgentEntity> retrieveByTop() {
        List<Record> agentRecords = Db.find("SELECT * FROM agent WHERE id = pId ORDER BY createTime DESC ");
        LinkedList<AgentEntity> agentEntities = new LinkedList<>();
        for (Record agentRecord : agentRecords) {
            Map<String, Object> accountMap = agentRecord.getColumns();
            AgentEntity agentEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
            agentEntities.add(agentEntity);
        }
        return agentEntities;
    }

    @Override
    public boolean appendCodes(AgentEntity agentEntity, int appendCodes) {
        int count = Db.update("UPDATE agent SET haveCodes = haveCodes + ?,leftCodes = leftCodes + ? WHERE id = ? ", appendCodes,appendCodes, agentEntity.getId());
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }
}
