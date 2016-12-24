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
 * Created by Pengwei Ding on 2016-09-22 17:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FormatServices implements IFormatServices {


    @Override
    public LinkedList<FormatEntity> retrievesInType(TypeEntity typeEntity) {
        LinkedList<FormatEntity> formatEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM product_format WHERE status = 2 AND typeId = ? order by weight ASC, updateTime DESC", typeEntity.getTypeId());
        for (Record record : records) {
            Map<String, Object> formatMap = record.getColumns();
            formatEntities.add(JSON.parseObject(JSON.toJSONString(formatMap), FormatEntity.class));
        }
        return formatEntities;
    }

    @Override
    public FormatEntity retrieveById(String formatId) {
        List<Record> records = Db.find("SELECT * FROM product_format WHERE formatId = ? order by weight ASC, updateTime DESC", formatId);
        if (records.size() == 1) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), FormatEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<FormatEntity> retrievesByWeight(int min, int max) {
        LinkedList<FormatEntity> result = new LinkedList<>();
        List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE status = 2 order by weight ASC limit 0 , 12 ");
        for (Record formatRecord : formatRecords) {
            FormatEntity formatEntity = JSON.parseObject(JSON.toJSONString(formatRecord.getColumns()), FormatEntity.class);
            result.add(formatEntity);
        }
        return result;
    }

    @Override
    public int mCount() {
        List<Record> records = Db.find("SELECT COUNT(formatId) AS counter FROM product_format WHERE status = 2 ");
        if (records.size() == 1) {
            return Integer.parseInt(records.get(0).getColumns().get("counter").toString());
        } else {
            return 0;
        }
    }

    @Override
    public boolean mExist(FormatEntity formatEntity) {
        List<Record> records = Db.find("SELECT * FROM product_format WHERE label = ? AND typeId = ? AND formatId != ? AND status != -1 ", formatEntity.getLabel(), formatEntity.getTypeId(),formatEntity.getFormatId());
        if (records.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FormatEntity mCreate(FormatEntity formatEntity) {
        Db.update("UPDATE product_type SET queue = queue + 1");
        Record record = new Record()
                .set("formatId", formatEntity.getFormatId())
                .set("label", formatEntity.getLabel())
                .set("meta", formatEntity.getMeta())
                .set("amount", formatEntity.getAmount())
                .set("amountMeta", formatEntity.getAmountMeta())
                .set("price", formatEntity.getPricing())
//                .set("priceMeta", formatEntity.getPriceMeta())
                .set("postage", formatEntity.getPostage())
//                .set("postageMeta", formatEntity.getPostageMeta())
                .set("pricing", formatEntity.getPrice())
                .set("pricingDiscount", formatEntity.getPricingDiscount())
                .set("pricingStart", formatEntity.getPricingStart())
                .set("pricingEnd", formatEntity.getPricingEnd())
                .set("pricingStatus", formatEntity.getPricingStatus())
                .set("expressCount", formatEntity.getExpressCount())
                .set("expressName", formatEntity.getExpressName())
                .set("expressStart", formatEntity.getExpressStart())
                .set("expressEnd", formatEntity.getExpressEnd())
                .set("expressStatus", formatEntity.getExpressStatus())
//                .set("giftCount", formatEntity.getGiftCount())
//                .set("giftLabel", formatEntity.getGiftLabel())
//                .set("giftId", formatEntity.getGiftId())
//                .set("giftStart", formatEntity.getGiftStart())
//                .set("giftEnd", formatEntity.getGiftEnd())
//                .set("giftStatus", formatEntity.getGiftStatus())
                .set("queue", formatEntity.getQueue())
                .set("status", formatEntity.getStatus())
                .set("typeId", formatEntity.getTypeId());
        boolean save = Db.save("product_format", record);
        if (save) {
            return formatEntity;
        } else {
            return null;
        }
    }

    @Override
    public FormatEntity mUpdate(FormatEntity formatEntity) {
        Record record = new Record()
                .set("formatId", formatEntity.getFormatId())
                .set("label", formatEntity.getLabel())
                .set("meta", formatEntity.getMeta())
                .set("amount", formatEntity.getAmount())
                .set("amountMeta", formatEntity.getAmountMeta())
                .set("price", formatEntity.getPrice())
//                .set("priceMeta", formatEntity.getPriceMeta())
                .set("postage", formatEntity.getPostage())
//                .set("postageMeta", formatEntity.getPostageMeta())
                .set("pricing", formatEntity.getPricing())
                .set("pricingDiscount", formatEntity.getPricingDiscount())
                .set("pricingStart", formatEntity.getPricingStart())
                .set("pricingEnd", formatEntity.getPricingEnd())
                .set("pricingStatus", formatEntity.getPricingStatus())
                .set("expressCount", formatEntity.getExpressCount())
                .set("expressName", formatEntity.getExpressName())
                .set("expressStart", formatEntity.getExpressStart())
                .set("expressEnd", formatEntity.getExpressEnd())
                .set("expressStatus", formatEntity.getExpressStatus())
//                .set("giftCount", formatEntity.getGiftCount())
//                .set("giftLabel", formatEntity.getGiftLabel())
//                .set("giftId", formatEntity.getGiftId())
//                .set("giftStart", formatEntity.getGiftStart())
//                .set("giftEnd", formatEntity.getGiftEnd())
//                .set("giftStatus", formatEntity.getGiftStatus())
                .set("queue", formatEntity.getQueue())
                .set("status", formatEntity.getStatus())
                .set("typeId", formatEntity.getTypeId());
        boolean update = Db.update("product_format", "formatId", record);
        if (update) {
            return formatEntity;
        } else {
            return null;
        }
    }

    @Override
    public FormatEntity mBlock(FormatEntity formatEntity) {
        int update = Db.update("UPDATE product_format SET status = 1 WHERE formatId = ? ", formatEntity.getFormatId());
        if (update == 1) {
            return formatEntity;
        } else {
            return null;
        }
    }

    @Override
    public FormatEntity mUnBlock(FormatEntity formatEntity) {
        int update = Db.update("UPDATE product_format SET status = 2 WHERE formatId = ? ", formatEntity.getFormatId());
        if (update == 1) {
            return formatEntity;
        } else {
            return null;
        }
    }

    @Override
    public FormatEntity mDelete(FormatEntity formatEntity) {
        int update = Db.update("UPDATE product_format SET status = -1 WHERE formatId = ? ", formatEntity.getFormatId());
        if (update == 1) {
            return formatEntity;
        } else {
            return null;
        }
    }

    @Override
    public FormatEntity mKingWeight(FormatEntity formatEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int updateAll = Db.update("UPDATE product_format SET weight = weight + 1");
                int update = Db.update("UPDATE product_format SET weight = 0 WHERE formatId = ?", formatEntity.getFormatId());
                return updateAll > 0 && update == 1;
            }
        });
        if (succeed) {
            return formatEntity;
        } else {
            return null;
        }
    }

    @Override
    public FormatEntity[] mSwapWeight(FormatEntity formatEntity1, FormatEntity formatEntity2) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int update1 = Db.update("UPDATE product_format SET weight = ? WHERE formatId = ?", formatEntity1.getWeight(), formatEntity2.getFormatId());
                int update2 = Db.update("UPDATE product_format SET weight = ? WHERE formatId = ?", formatEntity2.getWeight(), formatEntity1.getFormatId());
                return update1 > 0 && update2 == 1;
            }
        });
        if (succeed) {
            FormatEntity[] formatEntities = new FormatEntity[2];
            int weight = formatEntity1.getWeight();
            formatEntity1.setWeight(formatEntity2.getWeight());
            formatEntity2.setWeight(weight);
            formatEntities[0] = formatEntity1;
            formatEntities[1] = formatEntity2;
            return formatEntities;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<FormatEntity> mRetrieveByWeight(int index,int counter) {
        LinkedList<FormatEntity> result = new LinkedList<>();
        List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE status = 2 order by weight ASC limit ? , ? ",index * counter,counter);
        for (Record formatRecord : formatRecords) {
            FormatEntity formatEntity = JSON.parseObject(JSON.toJSONString(formatRecord.getColumns()), FormatEntity.class);
            result.add(formatEntity);
        }
        return result;
    }


    @Override
    public LinkedList<FormatEntity> mRetrievesInType(TypeEntity typeEntity) {
        LinkedList<FormatEntity> formatEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM product_format WHERE status != -1 AND typeId = ? order by weight ASC, updateTime DESC", typeEntity.getTypeId());
        for (Record record : records) {
            Map<String, Object> formatMap = record.getColumns();
            formatEntities.add(JSON.parseObject(JSON.toJSONString(formatMap), FormatEntity.class));
        }
        return formatEntities;
    }

    @Override
    public FormatEntity mRetrieveById(String formatId) {
        List<Record> records = Db.find("SELECT * FROM product_format WHERE status != -1 AND formatId = ? order by weight ASC, updateTime DESC", formatId);
        if (records.size() == 1) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), FormatEntity.class);
        } else {
            return null;
        }
    }
}
