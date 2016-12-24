package org.maxwe.tao.server.service.system;

/**
 * Created by Pengwei Ding on 2016-11-13 16:24.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SystemEntity {
    /**
     * 系统名
     */
    private String osName;
    /**
     * 系统版本号
     */
    private String osVersion;
    /**
     * 系统架构
     */
    private String osArch;
    /**
     * 系统IP
     */
    private String osIP;
    /**
     * 系统MAC地址
     */
    private String osMAC;
    /**
     * 系统时间
     */
    private long osDate;
    /**
     * java版本*
     */
    private String javaVersion;
    /**
     * 服务器名
     */
    private String serverName;
    /**
     * 服务版本
     */
    private String serverVersion;
    /**
     * 数据库名称*
     */
    private String dbName;
    /**
     * 数据库版本*
     */
    private String dbVersion;
    /**
     * cpu核数*
     */
    private int CPUS;
    /**
     * cpu利用率*
     */
    private float CPURatio;
    /**
     * 内存*
     */
    private long memoryTotal;
    /**
     * 内存使用量*
     */
    private long memoryUsed;

    private long memoryFree;

    private long diskTotal;
    private long diskFree;
    private long diskUsable;

    public SystemEntity() {
        super();
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getOsIP() {
        return osIP;
    }

    public void setOsIP(String osIP) {
        this.osIP = osIP;
    }

    public String getOsMAC() {
        return osMAC;
    }

    public void setOsMAC(String osMAC) {
        this.osMAC = osMAC;
    }

    public long getOsDate() {
        return osDate;
    }

    public void setOsDate(long osDate) {
        this.osDate = osDate;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public int getCPUS() {
        return CPUS;
    }

    public void setCPUS(int CPUS) {
        this.CPUS = CPUS;
    }

    public float getCPURatio() {
        return CPURatio;
    }

    public void setCPURatio(float CPURatio) {
        this.CPURatio = CPURatio;
    }

    public long getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(long memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public long getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(long memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public long getMemoryFree() {
        return memoryFree;
    }

    public void setMemoryFree(long memoryFree) {
        this.memoryFree = memoryFree;
    }

    public long getDiskTotal() {
        return diskTotal;
    }

    public void setDiskTotal(long diskTotal) {
        this.diskTotal = diskTotal;
    }

    public long getDiskFree() {
        return diskFree;
    }

    public void setDiskFree(long diskFree) {
        this.diskFree = diskFree;
    }

    public long getDiskUsable() {
        return diskUsable;
    }

    public void setDiskUsable(long diskUsable) {
        this.diskUsable = diskUsable;
    }
}
