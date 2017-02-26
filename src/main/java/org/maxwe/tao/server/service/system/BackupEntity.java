package org.maxwe.tao.server.service.system;

/**
 * Created by Pengwei Ding on 2017-02-26 08:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class BackupEntity {
    private String id;
    private String name;
    private String filePath;
    private int type;// 1：数据库，2：日志
    private int auto;// 1：自动，2：手动
    private int counter;//
    private long createTime;
    private long updateTime;

    public BackupEntity() {
        super();
    }

    public BackupEntity(String id, String name, String filePath, int type, int auto, int counter) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.type = type;
        this.auto = auto;
        this.counter = counter;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAuto() {
        return auto;
    }

    public void setAuto(int auto) {
        this.auto = auto;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
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
}
