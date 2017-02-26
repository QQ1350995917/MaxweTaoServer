package org.maxwe.tao.server.service.version;

import com.alibaba.druid.util.StringUtils;

/**
 * Created by Pengwei Ding on 2017-01-06 18:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VersionEntity {

    private String versionId;
    private String platform;
    private int type;
    private int versionCode;
    private String versionName;
    private String appName;
    private String information;
    private String url;
    private int upgrade; // 0不强制升级 其他强制
    private long createTime;
    private long updateTime;

    public VersionEntity() {
        super();
    }

    public VersionEntity(String platform,String appName,int type,int versionCode) {
        this.platform = platform;
        this.appName = appName;
        this.type = type;
        this.versionCode = versionCode;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VersionEntity) {
            VersionEntity other = (VersionEntity) obj;
            if (StringUtils.equals(this.getPlatform(), other.getPlatform()) &&
                    this.getType() != 0 &&
                    other.getType() != 0 &&
                    this.getType() == other.getType()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "platform = " + platform + " , type = " + type + " , versionCode = " + versionCode + " , versionName = " +
                versionName + " , information = " + information + " , url = " + url + " , upgrade = " + upgrade +
                " , createTime = " + createTime + " , updateTime = " + updateTime;
    }
}
