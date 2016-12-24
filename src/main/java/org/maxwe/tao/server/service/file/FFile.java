package org.maxwe.tao.server.service.file;

/**
 * Created by Pengwei Ding on 2016-11-02 10:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FFile {
    private String fileId;
    private String path;
    private String type;
    private int status;
    private String trunkId;

    public FFile() {
        super();
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTrunkId() {
        return trunkId;
    }

    public void setTrunkId(String trunkId) {
        this.trunkId = trunkId;
    }
}
