package org.maxwe.tao.server.service.link;

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
public class LinkServices implements ILinkServices {

    @Override
    public LinkedList<LinkEntity> retrieves() {
        LinkedList<LinkEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM link WHERE pid = linkId AND status = 2 ORDER BY weight DESC");
        for (Record record : records) {
            Map<String, Object> columns = record.getColumns();
            LinkEntity linkEntity = JSON.parseObject(JSON.toJSONString(columns), LinkEntity.class);
            result.add(linkEntity);
        }
        return result;
    }

    @Override
    public LinkedList<LinkEntity> retrievesByPid(String pid) {
        LinkedList<LinkEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM link WHERE pid = ? AND linkId != pid AND status = 2 ORDER BY weight DESC", pid);
        for (Record record : records) {
            Map<String, Object> columns = record.getColumns();
            LinkEntity linkEntity = JSON.parseObject(JSON.toJSONString(columns), LinkEntity.class);
            result.add(linkEntity);
        }
        return result;
    }

    @Override
    public boolean mExist(LinkEntity linkEntity) {
        if (linkEntity.getLinkId().equals(linkEntity.getPid())) {
            List<Record> records = Db.find("SELECT * FROM link WHERE linkId = pid AND label = ? AND status != -1", linkEntity.getLabel());
            if (records.size() == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            List<Record> records = Db.find("SELECT * FROM link WHERE pid = ? AND label = ? AND href = ? AND status != -1", linkEntity.getPid(), linkEntity.getLabel(), linkEntity.getHref());
            if (records.size() == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public LinkEntity mCreate(LinkEntity linkEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                Db.update("UPDATE link SET weight = weight + 1");
                Record record = new Record()
                        .set("linkId", linkEntity.getLinkId())
                        .set("label", linkEntity.getLabel())
                        .set("href", linkEntity.getHref())
                        .set("pid", linkEntity.getPid())
                        .set("weight", linkEntity.getWeight())
                        .set("status", linkEntity.getStatus());
                return Db.save("link", "linkId", record);
            }
        });

        if (succeed) {
            return linkEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkEntity mUpdate(LinkEntity linkEntity) {
        int update = Db.update("UPDATE link SET label = ?, href = ? WHERE linkId = ? AND status != -1", linkEntity.getLabel(), linkEntity.getHref(), linkEntity.getLinkId());
        if (update == 1) {
            return linkEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkEntity mBlock(LinkEntity linkEntity) {
        int update = Db.update("UPDATE link SET status = 1 WHERE linkId = ?", linkEntity.getLinkId());
        if (update == 1) {
            return linkEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkEntity mUnBlock(LinkEntity linkEntity) {
        int update = Db.update("UPDATE link SET status = 2 WHERE linkId = ?", linkEntity.getLinkId());
        if (update == 1) {
            return linkEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkEntity mDelete(LinkEntity linkEntity) {
        int update = Db.update("UPDATE link SET status = -1 WHERE linkId = ?", linkEntity.getLinkId());
        if (update == 1) {
            return linkEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkEntity[] mSwap(LinkEntity linkEntity1, LinkEntity linkEntity2) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int update1 = Db.update("UPDATE link SET weight = ? WHERE linkId = ? AND status != -1", linkEntity1.getWeight(), linkEntity2.getLinkId());
                int update2 = Db.update("UPDATE link SET weight = ? WHERE linkId = ? AND status != -1", linkEntity2.getWeight(), linkEntity1.getLinkId());
                return update1 == 1 && update2 == 1;
            }
        });
        if (succeed) {
            LinkEntity[] formatEntities = new LinkEntity[2];
            int weight = linkEntity1.getWeight();
            linkEntity1.setWeight(linkEntity2.getWeight());
            linkEntity2.setWeight(weight);
            formatEntities[0] = linkEntity1;
            formatEntities[1] = linkEntity2;
            return formatEntities;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<LinkEntity> mRetrieves() {
        LinkedList<LinkEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM link WHERE pid = linkId AND status != -1 ORDER BY weight DESC, createTime ASC ");
        for (Record record : records) {
            Map<String, Object> columns = record.getColumns();
            LinkEntity linkEntity = JSON.parseObject(JSON.toJSONString(columns), LinkEntity.class);
            result.add(linkEntity);
        }
        return result;
    }

    @Override
    public LinkedList<LinkEntity> mRetrievesByPid(String pid) {
        LinkedList<LinkEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM link WHERE pid = ? AND linkId != pid AND status != -1 ORDER BY weight DESC, createTime DESC ", pid);
        for (Record record : records) {
            Map<String, Object> columns = record.getColumns();
            LinkEntity linkEntity = JSON.parseObject(JSON.toJSONString(columns), LinkEntity.class);
            result.add(linkEntity);
        }
        return result;
    }
}
