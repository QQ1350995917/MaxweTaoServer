package org.maxwe.tao.server.service.tao.alimama.common;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-02-24 21; //01.
 * Email; // www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description; // @TODO
 */
public class AliResponseNavigator implements Serializable {
    private String name; // "女装",
    private long id; // 50102996,
    private String type; // "category",
    private long level; // 1,
    private String count; // "6571923",
    private String flag; // "qp_commend",
    private LinkedList<AliResponseNavigator> subIds;

    public AliResponseNavigator() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public LinkedList<AliResponseNavigator> getSubIds() {
        return subIds;
    }

    public void setSubIds(LinkedList<AliResponseNavigator> subIds) {
        this.subIds = subIds;
    }
}
