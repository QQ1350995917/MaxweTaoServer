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
            topVersion.add(new VersionEntity("Android", "Seller",1,0));
        }
        List<Record> androidAgentList = Db.find("SELECT * FROM version WHERE platform = ? AND type = ? ORDER BY versionCode DESC LIMIT 0,1", "Android", 2);
        if (androidAgentList != null && androidAgentList.size() > 0) {
            Map<String, Object> versionMap = androidAgentList.get(0).getColumns();
            VersionEntity versionEntity = JSON.parseObject(JSON.toJSONString(versionMap), VersionEntity.class);
            topVersion.add(versionEntity);
        } else {
            topVersion.add(new VersionEntity("Android", "Agent",2,0));
        }
        List<Record> iOSSellerList = Db.find("SELECT * FROM version WHERE platform = ? AND type = ? ORDER BY versionCode DESC LIMIT 0,1", "iOS", 1);
        if (iOSSellerList != null && iOSSellerList.size() > 0) {
            Map<String, Object> versionMap = iOSSellerList.get(0).getColumns();
            VersionEntity versionEntity = JSON.parseObject(JSON.toJSONString(versionMap), VersionEntity.class);
            topVersion.add(versionEntity);
        } else {
            topVersion.add(new VersionEntity("iOS", "Seller",1,0));
        }
        List<Record> iOSAgentList = Db.find("SELECT * FROM version WHERE platform = ? AND type = ? ORDER BY versionCode DESC LIMIT 0,1", "iOS", 2);
        if (iOSAgentList != null && iOSAgentList.size() > 0) {
            Map<String, Object> versionMap = iOSAgentList.get(0).getColumns();
            VersionEntity versionEntity = JSON.parseObject(JSON.toJSONString(versionMap), VersionEntity.class);
            topVersion.add(versionEntity);
        } else {
            topVersion.add(new VersionEntity("iOS", "Agent",2,0));
        }
        return topVersion;
    }

    @Override
    public List<VersionEntity> retrieveAll(int pageIndex, int pageSize) {
        LinkedList<VersionEntity> versionEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM version ORDER BY createTime DESC limit ? , ?", pageIndex * pageSize, pageSize);
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
    public VersionEntity create(VersionEntity versionEntity) {
        Record versionRecord = new Record()
                .set("versionId", versionEntity.getVersionId())
                .set("platform", versionEntity.getPlatform())
                .set("type", versionEntity.getType())
                .set("versionCode", versionEntity.getVersionCode())
                .set("versionName", versionEntity.getVersionName())
                .set("appName", versionEntity.getAppName())
                .set("information", versionEntity.getInformation())
                .set("url", versionEntity.getUrl())
                .set("upgrade", versionEntity.getUpgrade());
        boolean isSave = Db.save("version", versionRecord);
        if (isSave) {
            return versionEntity;
        } else {
            return null;
        }
    }

    @Override
    public VersionEntity retrieveByPlatformAndType(String platform, int type) {
        List<Record> androidSellerList = Db.find("SELECT * FROM version WHERE platform = ? AND type = ? ORDER BY versionCode DESC LIMIT 0,1", platform, type);
        if (androidSellerList != null && androidSellerList.size() > 0) {
            Map<String, Object> versionMap = androidSellerList.get(0).getColumns();
            VersionEntity topVersion = JSON.parseObject(JSON.toJSONString(versionMap), VersionEntity.class);
            return topVersion;
        } else {
            return null;
        }
    }
}
