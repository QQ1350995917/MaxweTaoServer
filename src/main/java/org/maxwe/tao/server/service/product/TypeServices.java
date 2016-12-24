package org.maxwe.tao.server.service.product;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-09-22 17:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TypeServices implements ITypeServices {

    @Override
    public LinkedList<TypeEntity> retrievesInSeries(SeriesEntity seriesEntity) {
        LinkedList<TypeEntity> typeEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM product_type WHERE seriesId = ? AND status = 2", seriesEntity.getSeriesId());
        for (Record record : records) {
            Map<String, Object> typeMap = record.getColumns();
            typeEntities.add(JSON.parseObject(JSON.toJSONString(typeMap), TypeEntity.class));
        }
        return typeEntities;
    }

    @Override
    public TypeEntity retrieveById(String typeId) {
        List<Record> records = Db.find("SELECT * FROM product_type WHERE typeId = ?", typeId);
        if (records.size() == 1) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), TypeEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public boolean mExistInSeries(String typeName, String seriesId) {
        List<Record> records = Db.find("SELECT * FROM product_type WHERE label = ? AND seriesId = ? AND status != -1", typeName, seriesId);
        if (records.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TypeEntity mCreate(TypeEntity typeEntity) {
        Db.update("UPDATE product_type SET queue = queue + 1");
        Record record = new Record().set("seriesId", typeEntity.getSeriesId()).set("typeId", typeEntity.getTypeId()).set("label", typeEntity.getLabel());
        boolean save = Db.save("product_type", record);
        if (save) {
            return typeEntity;
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity mUpdate(TypeEntity typeEntity) {
        int update = Db.update("UPDATE product_type SET label = ? WHERE typeId = ? ", typeEntity.getLabel(), typeEntity.getTypeId());
        if (update == 1) {
            return typeEntity;
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity mBlock(TypeEntity typeEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int update = Db.update("UPDATE product_type SET status = 1 WHERE typeId = ? ", typeEntity.getTypeId());
                Db.update("UPDATE product_format SET status = 1 WHERE status = 2 AND typeId = ? ", typeEntity.getTypeId());
                return update == 1;
            }
        });

        if (succeed) {
            return typeEntity;
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity mUnBlock(TypeEntity typeEntity) {
        int update = Db.update("UPDATE product_type SET status = 2 WHERE typeId = ? ", typeEntity.getTypeId());
        if (update == 1) {
            return typeEntity;
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity mDelete(TypeEntity typeEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int update = Db.update("UPDATE product_type SET status = -1 WHERE typeId = ? ", typeEntity.getTypeId());
                Db.update("UPDATE product_format SET status = -1 WHERE typeId = ? ", typeEntity.getTypeId());
                return update == 1;
            }
        });

        if (succeed) {
            return typeEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<TypeEntity> mRetrievesInSeries(SeriesEntity seriesEntity) {
        LinkedList<TypeEntity> typeEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM product_type WHERE seriesId = ? AND status != -1", seriesEntity.getSeriesId());
        for (Record record : records) {
            Map<String, Object> typeMap = record.getColumns();
            typeEntities.add(JSON.parseObject(JSON.toJSONString(typeMap), TypeEntity.class));
        }
        return typeEntities;
    }


    @Override
    public TypeEntity mRetrieveById(String typeId) {
        List<Record> records = Db.find("SELECT * FROM product_type WHERE typeId = ? AND status != -1", typeId);
        if (records.size() == 1) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), TypeEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity mUpdateSummary(TypeEntity typeEntity) {
        int update = Db.update("UPDATE product_type SET summary = ? where typeId = ? AND status != -1", typeEntity.getSummary(), typeEntity.getTypeId());
        if (update == 1) {
            return typeEntity;
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity mUpdateDirections(TypeEntity typeEntity) {
        int update = Db.update("UPDATE product_type SET directions = ? where typeId = ? AND status != -1", typeEntity.getDirections(), typeEntity.getTypeId());
        if (update == 1) {
            return typeEntity;
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity mUpdateImage(TypeEntity typeEntity) {
        return null;
    }

    @Override
    public TypeEntity mImageDelete(TypeEntity typeEntity) {
        return null;
    }
}
