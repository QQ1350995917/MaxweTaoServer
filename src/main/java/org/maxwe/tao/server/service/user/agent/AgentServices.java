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
    public boolean existProxy(String cellphone) {
        List<Record> records = Db.find("SELECT * FROM agent WHERE cellphone = ? ", cellphone);
        if (records.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AgentEntity createProxy(AgentEntity proxyEntity) {
        Record proxyRecord = new Record()
                .set("proxyId", proxyEntity.getProxyId())
                .set("cellphone", proxyEntity.getCellphone())
                .set("password", proxyEntity.getPassword());
        boolean account = Db.save("agent", proxyRecord);
        if (account) {
            return proxyEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity updateProxyPassword(AgentEntity proxyEntity) {
        int count = Db.update("UPDATE agent SET password = ? WHERE proxyId = ? ", proxyEntity.getPassword(), proxyEntity.getProxyId());
        if (count == 1) {
            return proxyEntity;
        } else {
            return null;
        }
    }

    @Override
    public AgentEntity retrieveProxy(AgentEntity proxyEntity) {
        return null;
    }

    @Override
    public AgentEntity retrieveProxy(String proxyId) {
        List<Record> proxyRecords = Db.find("SELECT * FROM agent WHERE proxyId = ? ", proxyId);
        Map<String, Object> accountMap = proxyRecords.get(0).getColumns();
        AgentEntity proxyEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
        return proxyEntity;
    }

    @Override
    public LinkedList<AgentEntity> retrieveProxyByPId(String proxyPId) {
        List<Record> proxyRecords = Db.find("SELECT * FROM agent WHERE proxyPId = ? ", proxyPId);
        LinkedList<AgentEntity> proxyEntities = new LinkedList<>();
        for (Record proxyRecord:proxyRecords){
            Map<String, Object> accountMap = proxyRecord.getColumns();
            AgentEntity proxyEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
            proxyEntities.add(proxyEntity);
        }
        return proxyEntities;
    }

    @Override
    public AgentEntity retrieveProxyByCellphone(String cellphone) {
        List<Record> proxyRecords = Db.find("SELECT * FROM agent WHERE cellphone = ? ", cellphone);
        Map<String, Object> accountMap = proxyRecords.get(0).getColumns();
        AgentEntity proxyEntity = JSON.parseObject(JSON.toJSONString(accountMap), AgentEntity.class);
        return proxyEntity;
    }
}
