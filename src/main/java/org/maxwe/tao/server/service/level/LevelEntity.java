package org.maxwe.tao.server.service.level;

import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.common.utils.DateTimeUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LevelEntity implements Serializable {
    private String id;
    private String name;
    private String description;
    private int minNum;
    private float price;
    private int level;
    private int weight;
    private long createTime;
    private long updateTime;

    @JSONField(serialize=false)
    private String createTimeLabel;
    @JSONField(serialize=false)
    private String updateTimeLabel;

    public LevelEntity() {
        super();
    }

    public LevelEntity(String name,int minNum){
        this.name = name;
        this.minNum = minNum;
    }

    public LevelEntity(String name,int minNum,float price,int level,int weight){
        this.name = name;
        this.minNum = minNum;
        this.price = price;
        this.level = level;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTimeLabel() {
        return DateTimeUtils.parseLongToFullTime(this.getCreateTime());
    }


    public String getUpdateTimeLabel() {
        return DateTimeUtils.parseLongToFullTime(this.getUpdateTime());
    }
}
