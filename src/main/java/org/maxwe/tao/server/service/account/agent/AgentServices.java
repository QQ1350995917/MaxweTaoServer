package org.maxwe.tao.server.service.account.agent;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import org.maxwe.tao.server.service.history.HistoryEntity;

import java.sql.SQLException;
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
                .set("id", agentEntity.getId())
                .set("mark", agentEntity.getMark())
                .set("cellphone", agentEntity.getCellphone())
                .set("password", agentEntity.getPassword());
        boolean isSave = Db.save("agent", agentRecord);
        if (isSave) {
            return agentEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity[] createReach(AgentEntity agentMasterEntity, AgentEntity agentSubEntity) {
        int count = Db.update("UPDATE agent SET pId = ? , pIdTime = ? , weight = 1 , WHERE id = ? ",
                agentMasterEntity.getId(), new Timestamp(agentSubEntity.getpIdTime()), agentSubEntity.getId());
        if (count == 1) {
            return new AgentEntity[]{agentMasterEntity, agentSubEntity};
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
    public AgentEntity updateGrant(AgentEntity agentEntity, HistoryEntity historyEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int grandAgent = Db.update("UPDATE agent SET spendCodes = spendCodes + 1, leftCodes = leftCodes - 1 " +
                        "where agentId = ? ", agentEntity.getId());
                int history = Db.update("INSERT INTO history (id,fromId,type,actCode,numCode) VALUES(?,?,?,?,?)",
                        historyEntity.getId(), historyEntity.getFromId(), 1, historyEntity.getActCode(), 1);
                return grandAgent == 1 && history == 1;
            }
        });
        return agentEntity;
    }

    @Override
    public AgentEntity[] updateReach(AgentEntity agentMasterEntity, AgentEntity agentSubEntity) {
        int count = Db.update("UPDATE agent SET reach = 1 , weight = 0, reachTime = ? , WHERE id = ? ",
                agentMasterEntity.getReachTime(), agentSubEntity.getId());
        if (count == 1) {
            return new AgentEntity[]{agentMasterEntity, agentSubEntity};
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity[] updateTrade(AgentEntity agentMasterEntity, AgentEntity agentSubEntity, int codes, HistoryEntity historyEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int agentMaster = Db.update("UPDATE agent SET spendCodes = spendCodes + ?, leftCodes = leftCodes - ? " +
                        "where agentId = ? ", codes, codes, agentMasterEntity.getId());
                int agentSub = Db.update("UPDATE agent SET haveCodes = haveCodes + ?, leftCodes = leftCodes + ? " +
                        "where agentId = ? ", codes, codes, agentSubEntity.getId());
                int history = Db.update("INSERT INTO history (id,fromId,toId,type,numCode) VALUES(?,?,?,?,?)",
                        historyEntity.getId(), historyEntity.getFromId(), historyEntity.getToId(), 2, historyEntity.getNumCode());
                return agentMaster == 1 && agentSub == 1 && history == 1;
            }
        });
        return new AgentEntity[0];
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
    public AgentEntity retrieveById(String id) {
        List<Record> records = Db.find("SELECT * FROM agent WHERE id = ? ", id);
        if (records != null && records.size() > 0 && records.get(0) != null) {
            Map<String, Object> accountMap = records.get(0).getColumns();
            return JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity retrieveByMark(String mark) {
        List<Record> records = Db.find("SELECT * FROM agent WHERE mark = ? ", mark);
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
    public LinkedList<AgentEntity> retrieveByPid(String pId, int pageIndex, int pageSize) {
        List<Record> agentRecords = Db.find("SELECT * FROM agent WHERE pId = ? ORDER BY weight DESC , createTime DESC limit ? , ?",
                pId, pageIndex * pageSize, pageSize);
        LinkedList<AgentEntity> agentEntities = new LinkedList<>();
        for (Record agentRecord : agentRecords) {
            Map<String, Object> accountMap = agentRecord.getColumns();
            AgentEntity agentEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
            agentEntities.add(agentEntity);
        }
        return agentEntities;
    }
}
