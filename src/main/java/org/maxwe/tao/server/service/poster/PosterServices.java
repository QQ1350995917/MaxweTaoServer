package org.maxwe.tao.server.service.poster;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-15 15:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class PosterServices implements IPosterServices {
    @Override
    public LinkedList<PosterEntity> retrieves() {
        LinkedList<PosterEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM poster WHERE status = 2 ORDER BY weight DESC");
        for (Record record : records) {
            Map<String, Object> columns = record.getColumns();
            PosterEntity posterEntity = JSON.parseObject(JSON.toJSONString(columns), PosterEntity.class);
            result.add(posterEntity);
        }
        return result;
    }

    @Override
    public PosterEntity mCreate(PosterEntity posterEntity) {
        Record record = new Record()
                .set("posterId",posterEntity.getPosterId())
                .set("name",posterEntity.getName())
                .set("clickable", posterEntity.getClickable())
                .set("status", posterEntity.getStatus())
                .set("href", posterEntity.getHref())
                .set("fileId", posterEntity.getFileId())
                .set("start",posterEntity.getStart())
                .set("end", posterEntity.getEnd());
        boolean poster = Db.save("poster", "posterId", record);
        if (poster) {
            return posterEntity;
        } else {
            return null;
        }
    }

    @Override
    public PosterEntity mUpdate(PosterEntity posterEntity) {
        Record record = new Record()
                .set("posterId", posterEntity.getPosterId())
                .set("name", posterEntity.getName())
                .set("clickable", posterEntity.getClickable())
                .set("href", posterEntity.getHref())
                .set("fileId", posterEntity.getFileId())
                .set("start", posterEntity.getStart())
                .set("end", posterEntity.getEnd());
        boolean poster = Db.update("poster", "posterId", record);
        if (poster) {
            return posterEntity;
        } else {
            return null;
        }
    }

    @Override
    public PosterEntity mBlock(PosterEntity posterEntity) {
        int update = Db.update("UPDATE poster SET status = 1 WHERE posterId = ?", posterEntity.getPosterId());
        if (update == 1) {
            return posterEntity;
        } else {
            return null;
        }
    }

    @Override
    public PosterEntity mUnBlock(PosterEntity posterEntity) {
        int update = Db.update("UPDATE poster SET status = 2 WHERE posterId = ?", posterEntity.getPosterId());
        if (update == 1) {
            return posterEntity;
        } else {
            return null;
        }
    }

    @Override
    public PosterEntity mDelete(PosterEntity posterEntity) {
        int update = Db.update("UPDATE poster SET status = -1 WHERE posterId = ?", posterEntity.getPosterId());
        if (update == 1) {
            return posterEntity;
        } else {
            return null;
        }
    }

    @Override
    public PosterEntity[] mSwap(PosterEntity posterEntity1, PosterEntity posterEntity2) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int update1 = Db.update("UPDATE poster SET weight = ? WHERE posterId = ? AND status != -1", posterEntity1.getWeight(), posterEntity1.getPosterId());
                int update2 = Db.update("UPDATE poster SET weight = ? WHERE posterId = ? AND status != -1", posterEntity2.getWeight(), posterEntity2.getPosterId());
                return update1 > 0 && update2 == 1;
            }
        });
        if (succeed) {
            PosterEntity[] formatEntities = new PosterEntity[2];
            int weight = posterEntity1.getWeight();
            posterEntity1.setWeight(posterEntity2.getWeight());
            posterEntity2.setWeight(weight);
            formatEntities[0] = posterEntity1;
            formatEntities[1] = posterEntity2;
            return formatEntities;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<PosterEntity> mRetrieves() {
        LinkedList<PosterEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM poster WHERE status != -1 ORDER BY weight DESC");
        for (Record record : records) {
            Map<String, Object> columns = record.getColumns();
            PosterEntity posterEntity = JSON.parseObject(JSON.toJSONString(columns), PosterEntity.class);
            result.add(posterEntity);
        }
        return result;
    }
}
