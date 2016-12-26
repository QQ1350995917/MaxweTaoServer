package org.maxwe.tao.server.service.user.proxy;

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
public class ProxyServices implements IProxyServices {
    @Override
    public boolean existProxy(String cellphone) {
        List<Record> records = Db.find("SELECT * FROM proxy WHERE cellphone = ? ", cellphone);
        if (records.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ProxyEntity createProxy(ProxyEntity proxyEntity) {
        Record proxyRecord = new Record()
                .set("proxyId", proxyEntity.getProxyId())
                .set("cellphone", proxyEntity.getCellphone())
                .set("password", proxyEntity.getPassword());
        boolean account = Db.save("proxy", proxyRecord);
        if (account) {
            return proxyEntity;
        } else {
            return null;
        }
    }

    @Override
    public ProxyEntity updateProxyPassword(ProxyEntity proxyEntity) {
        int count = Db.update("UPDATE proxy SET password = ? WHERE proxyId = ? ", proxyEntity.getPassword(), proxyEntity.getProxyId());
        if (count == 1) {
            return proxyEntity;
        } else {
            return null;
        }
    }

    @Override
    public ProxyEntity retrieveProxy(ProxyEntity proxyEntity) {
        return null;
    }

    @Override
    public ProxyEntity retrieveProxy(String proxyId) {
        List<Record> proxyRecords = Db.find("SELECT * FROM proxy WHERE proxyId = ? ", proxyId);
        Map<String, Object> accountMap = proxyRecords.get(0).getColumns();
        ProxyEntity proxyEntity = JSON.parseObject(JSON.toJSONString(accountMap), ProxyEntity.class);
        return proxyEntity;
    }

    @Override
    public LinkedList<ProxyEntity> retrieveProxyByPId(String proxyPId) {
        List<Record> proxyRecords = Db.find("SELECT * FROM proxy WHERE proxyPId = ? ", proxyPId);
        LinkedList<ProxyEntity> proxyEntities = new LinkedList<>();
        for (Record proxyRecord:proxyRecords){
            Map<String, Object> accountMap = proxyRecord.getColumns();
            ProxyEntity proxyEntity = JSON.parseObject(JSON.toJSONString(accountMap), ProxyEntity.class);
            proxyEntities.add(proxyEntity);
        }
        return proxyEntities;
    }

    @Override
    public ProxyEntity retrieveProxyByCellphone(String cellphone) {
        List<Record> proxyRecords = Db.find("SELECT * FROM proxy WHERE cellphone = ? ", cellphone);
        Map<String, Object> accountMap = proxyRecords.get(0).getColumns();
        ProxyEntity proxyEntity = JSON.parseObject(JSON.toJSONString(accountMap), ProxyEntity.class);
        return proxyEntity;
    }
}
