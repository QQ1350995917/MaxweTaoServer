package org.maxwe.tao.server.service.level;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LevelServices implements ILevelServices {
    private static ConcurrentMap<Integer, LevelEntity> topLevelsMap = new ConcurrentHashMap<>();

    static {
        initTopLevel();
//        topLevelsMap.put(1, new LevelEntity("联合创始人", 100, 0f, 1, 0));
//        topLevelsMap.put(2, new LevelEntity("总代", 60, 0f, 2, 0));
//        topLevelsMap.put(3, new LevelEntity("一级代理", 30, 0f, 3, 0));
//        topLevelsMap.put(4, new LevelEntity("分销商", 5, 0f, 4, 0));
    }


    public static void main(String[] args) {
//        int codeNum = 60;
//        Set<Map.Entry<Integer, LevelEntity>> entries = topLevelsMap.entrySet();
//        for (Map.Entry<Integer, LevelEntity> entry : entries) {
//            int currentOffset = codeNum - entry.getValue().getMinNum();
//            System.out.println(currentOffset);
//            if (currentOffset >= 0) {
//                System.out.println(entry.getValue().getName());
//                break;
//            } else if (currentOffset < 0) {
//                continue;
//            }
//        }
    }

    @Override
    public LevelEntity create(LevelEntity levelEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                Db.update("UPDATE level SET weight = weight + 1 WHERE level = ?", levelEntity.getLevel());
                Record record = new Record()
                        .set("id", levelEntity.getId())
                        .set("name", levelEntity.getName())
                        .set("description", levelEntity.getDescription())
                        .set("minNum", levelEntity.getMinNum())
                        .set("price", levelEntity.getPrice())
                        .set("level", levelEntity.getLevel())
                        .set("weight", levelEntity.getWeight());
                boolean save = Db.save("level", "id", record);
                if (save) {
                    levelEntity.setCreateTime(System.currentTimeMillis());
                    levelEntity.setUpdateTime(System.currentTimeMillis());
                    topLevelsMap.put(levelEntity.getLevel(), levelEntity);
                }
                return save;
            }
        });

        if (succeed) {
            return levelEntity;
        } else {
            return null;
        }
    }

    @Override
    public LevelEntity retrieveById(String id) {
        List<Record> records = Db.find("SELECT * FROM level WHERE id = ?", id);
        if (records != null && records.size() > 0) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), LevelEntity.class);
        }
        return null;
    }

    @Override
    public LinkedList<LevelEntity> retrieveAll(int pageIndex, int pageSize) {
        LinkedList<LevelEntity> levelEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM level ORDER BY createTime DESC limit ? , ?", pageIndex * pageSize, pageSize);
        for (Record record : records) {
            levelEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), LevelEntity.class));
        }
        return levelEntities;
    }

    @Override
    public int retrieveAllNumber() {
        return Db.find("SELECT * FROM level").size();
    }

    @Override
    public LinkedList<LevelEntity> retrieveTop() {
        LinkedList<LevelEntity> topLevels = new LinkedList<>();
        Set<Map.Entry<Integer, LevelEntity>> entries = topLevelsMap.entrySet();
        for (Map.Entry<Integer, LevelEntity> entry : entries) {
            topLevels.add(entry.getKey() - 1, entry.getValue());
        }
        return topLevels;
    }

    @Override
    public LevelEntity retrieveByNum(int codeNum) {
        LevelEntity result = null;
        Set<Map.Entry<Integer, LevelEntity>> entries = topLevelsMap.entrySet();
        for (Map.Entry<Integer, LevelEntity> entry : entries) {
            int currentOffset = codeNum - entry.getValue().getMinNum();
            if (currentOffset >= 0) {
                result = entry.getValue();
                break;
            }
        }
        return result;
    }

    private static void initTopLevel() {
        List<Record> level1s = Db.find("SELECT * FROM level WHERE level = ? ORDER BY weight ASC LIMIT 0,1", 1);
        if (level1s != null && level1s.size() > 0) {
            Map<String, Object> levelMap = level1s.get(0).getColumns();
            LevelEntity levelEntity = JSON.parseObject(JSON.toJSONString(levelMap), LevelEntity.class);
            topLevelsMap.put(1, levelEntity);
        } else {
            topLevelsMap.put(1, new LevelEntity("联合创始人", 0, 0f, 1, 0));
        }


        List<Record> level2s = Db.find("SELECT * FROM level WHERE level = ? ORDER BY weight ASC LIMIT 0,1", 2);
        if (level2s != null && level2s.size() > 0) {
            Map<String, Object> levelMap = level2s.get(0).getColumns();
            LevelEntity levelEntity = JSON.parseObject(JSON.toJSONString(levelMap), LevelEntity.class);
            topLevelsMap.put(2, levelEntity);
        } else {
            topLevelsMap.put(2, new LevelEntity("总代", 0, 0f, 2, 0));
        }


        List<Record> level3s = Db.find("SELECT * FROM level WHERE level = ? ORDER BY weight ASC LIMIT 0,1", 3);
        if (level3s != null && level3s.size() > 0) {
            Map<String, Object> levelMap = level3s.get(0).getColumns();
            LevelEntity levelEntity = JSON.parseObject(JSON.toJSONString(levelMap), LevelEntity.class);
            topLevelsMap.put(3, levelEntity);
        } else {
            topLevelsMap.put(3, new LevelEntity("一级代理", 0, 0f, 3, 0));
        }


        List<Record> level4s = Db.find("SELECT * FROM level WHERE level = ? ORDER BY weight ASC LIMIT 0,1", 4);
        if (level4s != null && level4s.size() > 0) {
            Map<String, Object> levelMap = level4s.get(0).getColumns();
            LevelEntity levelEntity = JSON.parseObject(JSON.toJSONString(levelMap), LevelEntity.class);
            topLevelsMap.put(4, levelEntity);
        } else {
            topLevelsMap.put(4, new LevelEntity("分销商", 0, 0f, 4, 0));
        }

        List<Record> level5s = Db.find("SELECT * FROM level WHERE level = ? ORDER BY weight ASC LIMIT 0,1", 5);
        if (level5s != null && level5s.size() > 0) {
            Map<String, Object> levelMap = level5s.get(0).getColumns();
            LevelEntity levelEntity = JSON.parseObject(JSON.toJSONString(levelMap), LevelEntity.class);
            topLevelsMap.put(5, levelEntity);
        } else {
            topLevelsMap.put(5, new LevelEntity("单码", 1, 0f, 5, 0));
        }
    }
}
