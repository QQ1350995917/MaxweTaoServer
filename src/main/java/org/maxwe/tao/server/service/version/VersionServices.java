package org.maxwe.tao.server.service.version;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-01-06 18:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VersionServices implements IVersionServices {

    @Override
    public List<VersionEntity> retrieveTopVersion() {
        List<VersionEntity> topVersion = new LinkedList<>();
        List<Record> androidSellerList = Db.find("SELECT * FROM version WHERE platform = ? AND type = ? ORDER BY versionCode DESC LIMIT 0,1", "Android", 1);
        if (androidSellerList != null && androidSellerList.size() > 0) {
            Map<String, Object> versionMap = androidSellerList.get(0).getColumns();
            VersionEntity versionEntity = JSON.parseObject(JSON.toJSONString(versionMap), VersionEntity.class);
            topVersion.add(versionEntity);
        } else {
            topVersion.add(new VersionEntity("Android","Seller"));
        }
        List<Record> androidAgentList = Db.find("SELECT * FROM version WHERE platform = ? AND type = ? ORDER BY versionCode DESC LIMIT 0,1", "Android", 2);
        if (androidAgentList != null && androidAgentList.size() > 0) {
            Map<String, Object> versionMap = androidAgentList.get(0).getColumns();
            VersionEntity versionEntity = JSON.parseObject(JSON.toJSONString(versionMap), VersionEntity.class);
            topVersion.add(versionEntity);
        } else {
            topVersion.add(new VersionEntity("Android","Agent"));
        }
        List<Record> iOSSellerList = Db.find("SELECT * FROM version WHERE platform = ? AND type = ? ORDER BY versionCode DESC LIMIT 0,1", "iOS", 1);
        if (iOSSellerList != null && iOSSellerList.size() > 0) {
            Map<String, Object> versionMap = iOSSellerList.get(0).getColumns();
            VersionEntity versionEntity = JSON.parseObject(JSON.toJSONString(versionMap), VersionEntity.class);
            topVersion.add(versionEntity);
        } else {
            topVersion.add(new VersionEntity("iOS","Seller"));
        }
        List<Record> iOSAgentList = Db.find("SELECT * FROM version WHERE platform = ? AND type = ? ORDER BY versionCode DESC LIMIT 0,1", "iOS", 2);
        if (iOSAgentList != null && iOSAgentList.size() > 0) {
            Map<String, Object> versionMap = iOSAgentList.get(0).getColumns();
            VersionEntity versionEntity = JSON.parseObject(JSON.toJSONString(versionMap), VersionEntity.class);
            topVersion.add(versionEntity);
        } else {
            topVersion.add(new VersionEntity("iOS","Agent"));
        }
        return topVersion;
    }

    @Override
    public List<VersionEntity> retrieveAll(int pageIndex, int pageSize) {
        LinkedList<VersionEntity> versionEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM version ORDER BY createTime limit ? , ?",pageIndex * pageSize, pageSize);
        for (Record record : records) {
            versionEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), VersionEntity.class));
        }
        return versionEntities;
    }

    @Override
    public int retrieveCounter() {
        return Db.find("SELECT * FROM version").size();
    }

    @Override
    public List<VersionEntity> reversion() {
        List<Record> records = Db.find("SELECT * FROM version");
        LinkedList<VersionEntity> versionEntities = new LinkedList<>();
        for (Record agentRecord : records) {
            Map<String, Object> accountMap = agentRecord.getColumns();
            VersionEntity versionEntity = JSON.parseObject(JSON.toJSONString(accountMap), VersionEntity.class);
            versionEntities.add(versionEntity);
        }
        return versionEntities;
    }
}
