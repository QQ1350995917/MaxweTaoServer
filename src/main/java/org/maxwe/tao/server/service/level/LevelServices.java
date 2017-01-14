package org.maxwe.tao.server.service.level;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LevelServices implements ILevelServices {

    @Override
    public LinkedList<LevelEntity> retrieve() {
        LinkedList<LevelEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM level ORDER BY minNum ASC");
        for (Record record : records) {
            Map<String, Object> columns = record.getColumns();
            LevelEntity levelEntity = JSON.parseObject(JSON.toJSONString(columns), LevelEntity.class);
            result.add(levelEntity);
        }
        return result;
    }


    //    @Override
//    public LinkedList<LevelEntity> retrieves() {
//        LinkedList<LevelEntity> result = new LinkedList<>();
//        List<Record> records = Db.find("SELECT * FROM level WHERE pid = linkId AND status = 2 ORDER BY weight DESC");
//        for (Record record : records) {
//            Map<String, Object> columns = record.getColumns();
//            LevelEntity linkEntity = JSON.parseObject(JSON.toJSONString(columns), LevelEntity.class);
//            result.add(linkEntity);
//        }
//        return result;
//    }
//
//    @Override
//    public LinkedList<LevelEntity> retrievesByPid(String pid) {
//        LinkedList<LevelEntity> result = new LinkedList<>();
//        List<Record> records = Db.find("SELECT * FROM level WHERE pid = ? AND linkId != pid AND status = 2 ORDER BY weight DESC", pid);
//        for (Record record : records) {
//            Map<String, Object> columns = record.getColumns();
//            LevelEntity linkEntity = JSON.parseObject(JSON.toJSONString(columns), LevelEntity.class);
//            result.add(linkEntity);
//        }
//        return result;
//    }
//
//    @Override
//    public boolean mExist(LevelEntity linkEntity) {
//        if (linkEntity.getLinkId().equals(linkEntity.getPid())) {
//            List<Record> records = Db.find("SELECT * FROM level WHERE linkId = pid AND label = ? AND status != -1", linkEntity.getLabel());
//            if (records.size() == 1) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            List<Record> records = Db.find("SELECT * FROM level WHERE pid = ? AND label = ? AND href = ? AND status != -1", linkEntity.getPid(), linkEntity.getLabel(), linkEntity.getHref());
//            if (records.size() == 1) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
//
//    @Override
//    public LevelEntity mCreate(LevelEntity linkEntity) {
//        boolean succeed = Db.tx(new IAtom() {
//            public boolean run() throws SQLException {
//                Db.update("UPDATE level SET weight = weight + 1");
//                Record record = new Record()
//                        .set("linkId", linkEntity.getLinkId())
//                        .set("label", linkEntity.getLabel())
//                        .set("href", linkEntity.getHref())
//                        .set("pid", linkEntity.getPid())
//                        .set("weight", linkEntity.getWeight())
//                        .set("status", linkEntity.getStatus());
//                return Db.save("level", "linkId", record);
//            }
//        });
//
//        if (succeed) {
//            return linkEntity;
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public LevelEntity mUpdate(LevelEntity linkEntity) {
//        int update = Db.update("UPDATE level SET label = ?, href = ? WHERE linkId = ? AND status != -1", linkEntity.getLabel(), linkEntity.getHref(), linkEntity.getLinkId());
//        if (update == 1) {
//            return linkEntity;
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public LevelEntity mBlock(LevelEntity linkEntity) {
//        int update = Db.update("UPDATE level SET status = 1 WHERE linkId = ?", linkEntity.getLinkId());
//        if (update == 1) {
//            return linkEntity;
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public LevelEntity mUnBlock(LevelEntity linkEntity) {
//        int update = Db.update("UPDATE level SET status = 2 WHERE linkId = ?", linkEntity.getLinkId());
//        if (update == 1) {
//            return linkEntity;
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public LevelEntity mDelete(LevelEntity linkEntity) {
//        int update = Db.update("UPDATE level SET status = -1 WHERE linkId = ?", linkEntity.getLinkId());
//        if (update == 1) {
//            return linkEntity;
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public LevelEntity[] mSwap(LevelEntity linkEntity1, LevelEntity linkEntity2) {
//        boolean succeed = Db.tx(new IAtom() {
//            public boolean run() throws SQLException {
//                int update1 = Db.update("UPDATE level SET weight = ? WHERE linkId = ? AND status != -1", linkEntity1.getWeight(), linkEntity2.getLinkId());
//                int update2 = Db.update("UPDATE level SET weight = ? WHERE linkId = ? AND status != -1", linkEntity2.getWeight(), linkEntity1.getLinkId());
//                return update1 == 1 && update2 == 1;
//            }
//        });
//        if (succeed) {
//            LevelEntity[] formatEntities = new LevelEntity[2];
//            int weight = linkEntity1.getWeight();
//            linkEntity1.setWeight(linkEntity2.getWeight());
//            linkEntity2.setWeight(weight);
//            formatEntities[0] = linkEntity1;
//            formatEntities[1] = linkEntity2;
//            return formatEntities;
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public LinkedList<LevelEntity> mRetrieves() {
//        LinkedList<LevelEntity> result = new LinkedList<>();
//        List<Record> records = Db.find("SELECT * FROM level WHERE pid = linkId AND status != -1 ORDER BY weight DESC, createTime ASC ");
//        for (Record record : records) {
//            Map<String, Object> columns = record.getColumns();
//            LevelEntity linkEntity = JSON.parseObject(JSON.toJSONString(columns), LevelEntity.class);
//            result.add(linkEntity);
//        }
//        return result;
//    }
//
//    @Override
//    public LinkedList<LevelEntity> mRetrievesByPid(String pid) {
//        LinkedList<LevelEntity> result = new LinkedList<>();
//        List<Record> records = Db.find("SELECT * FROM level WHERE pid = ? AND linkId != pid AND status != -1 ORDER BY weight DESC, createTime DESC ", pid);
//        for (Record record : records) {
//            Map<String, Object> columns = record.getColumns();
//            LevelEntity linkEntity = JSON.parseObject(JSON.toJSONString(columns), LevelEntity.class);
//            result.add(linkEntity);
//        }
//        return result;
//    }
}
