package org.maxwe.tao.server.service.level;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
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
                return Db.save("level", "id", record);
            }
        });

        if (succeed) {
            return levelEntity;
        } else {
            return null;
        }

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
        List<Record> level1s = Db.find("SELECT * FROM level WHERE level = ? ORDER BY weight ASC LIMIT 0,1", 1);
        if (level1s != null && level1s.size() > 0) {
            Map<String, Object> levelMap = level1s.get(0).getColumns();
            LevelEntity levelEntity = JSON.parseObject(JSON.toJSONString(levelMap), LevelEntity.class);
            topLevels.add(levelEntity);
        } else {
            topLevels.add(new LevelEntity("联合创始人", 0, 0f, 1, 0));
        }


        List<Record> level2s = Db.find("SELECT * FROM level WHERE level = ? ORDER BY weight ASC LIMIT 0,1", 2);
        if (level2s != null && level2s.size() > 0) {
            Map<String, Object> levelMap = level2s.get(0).getColumns();
            LevelEntity levelEntity = JSON.parseObject(JSON.toJSONString(levelMap), LevelEntity.class);
            topLevels.add(levelEntity);
        } else {
            topLevels.add(new LevelEntity("总代", 0, 0f, 1, 0));
        }


        List<Record> level3s = Db.find("SELECT * FROM level WHERE level = ? ORDER BY weight ASC LIMIT 0,1", 3);
        if (level3s != null && level3s.size() > 0) {
            Map<String, Object> levelMap = level3s.get(0).getColumns();
            LevelEntity levelEntity = JSON.parseObject(JSON.toJSONString(levelMap), LevelEntity.class);
            topLevels.add(levelEntity);
        } else {
            topLevels.add(new LevelEntity("一级代理", 0, 0f, 1, 0));
        }


        List<Record> level4s = Db.find("SELECT * FROM level WHERE level = ? ORDER BY weight ASC LIMIT 0,1", 4);
        if (level4s != null && level4s.size() > 0) {
            Map<String, Object> levelMap = level4s.get(0).getColumns();
            LevelEntity levelEntity = JSON.parseObject(JSON.toJSONString(levelMap), LevelEntity.class);
            topLevels.add(levelEntity);
        } else {
            topLevels.add(new LevelEntity("分销商", 0, 0f, 1, 0));
        }

        return topLevels;
    }

    @Override
    public LevelEntity retrieveByNum(int codeNum) {
        LevelEntity result = null;
        LinkedList<LevelEntity> levelEntities = this.retrieveTop();
        for (LevelEntity levelEntity : levelEntities) {
            int currentOffset = codeNum - levelEntity.getMinNum();
            if (currentOffset >= 0) {
                result = levelEntity;
            } else if (currentOffset < 0) {
                break;
            }
        }
        if (result == null) {
            result = levelEntities.getFirst();
        }
        return result;
    }
}
