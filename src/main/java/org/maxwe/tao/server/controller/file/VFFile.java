package org.maxwe.tao.server.controller.file;

import org.maxwe.tao.server.service.file.FFile;

/**
 * Created by Pengwei Ding on 2016-11-02 10:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VFFile extends FFile {
    private String cs;

    public VFFile() {
        super();
    }


    public VFFile(String path) {
        this.setPath(path);
    }

    public VFFile(FFile fFile) {
        this.setFileId(fFile.getFileId());
        this.setPath(fFile.getPath());
        this.setType(fFile.getType());
        this.setStatus(fFile.getStatus());
        this.setTrunkId(fFile.getTrunkId());
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public boolean checkCreateParams() {
        if (this.getTrunkId() == null || this.getTrunkId().trim().equals("")) {
            return false;
        }
        return true;
    }
}
