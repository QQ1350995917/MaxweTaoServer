package org.maxwe.tao.server.service.file;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-11-02 10:21.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FileServices implements IFileServices {

    @Override
    public LinkedList<FFile> retrieveByTrunkId(String trunkId) {
        LinkedList<FFile> fFiles = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM file WHERE trunkId = ? AND status = 2", trunkId);
        for (Record record:records){
            Map<String, Object> columns = record.getColumns();
            FFile fFile = JSON.parseObject(JSON.toJSONString(columns), FFile.class);
            fFiles.add(fFile);
        }
        return fFiles;
    }

    @Override
    public FFile mCreate(FFile fFile) {
        Record record = new Record().set("fileId", fFile.getFileId())
                .set("path", fFile.getPath())
                .set("type", fFile.getType())
                .set("status", fFile.getStatus())
                .set("trunkId", fFile.getTrunkId());
        boolean save = Db.save("file", "fileId", record);
        if (save){
            return fFile;
        }
        return null;
    }

    @Override
    public FFile mUpdate(FFile fFile) {
        int update = Db.update("UPDATE file SET path = ? WHERE fileId = ? AND status != -1", fFile.getPath(), fFile.getFileId());
        if (update == 1){
            return fFile;
        }
        return null;
    }

    @Override
    public FFile mDelete(FFile fFile) {
        int update = Db.update("UPDATE file SET status = -1 WHERE fileId = ? AND status != -1", fFile.getFileId());
        if (update == 1){
            return fFile;
        }
        return null;
    }

    @Override
    public FFile mBindTrunk(FFile fFile) {
        int update = Db.update("UPDATE file SET trunkId = ? WHERE fileId = ? AND status != -1", fFile.getTrunkId(), fFile.getFileId());
        if (update == 1){
            return fFile;
        }
        return null;
    }

    @Override
    public LinkedList<FFile> mRetrieveByTrunkId(String trunkId) {
        LinkedList<FFile> fFiles = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM file WHERE trunkId = ? AND status != -1", trunkId);
        for (Record record:records){
            Map<String, Object> columns = record.getColumns();
            FFile fFile = JSON.parseObject(JSON.toJSONString(columns), FFile.class);
            fFiles.add(fFile);
        }
        return fFiles;
    }


    public static boolean fileCopy(File sourceFile, File targetFile) {
        boolean result = false;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileInChannel = null;
        FileChannel fileOutChannel = null;
        try {
            if (!targetFile.exists()){
                targetFile.createNewFile();
            }
            fileInputStream = new FileInputStream(sourceFile);
            fileOutputStream = new FileOutputStream(targetFile);
            fileInChannel = fileInputStream.getChannel();// 得到对应的文件通道
            fileOutChannel = fileOutputStream.getChannel();// 得到对应的文件通道
            fileInChannel.transferTo(0, fileInChannel.size(), fileOutChannel);// 连接两个通道，并且从in通道读取，然后写入out通道
            result = true;
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileInChannel.close();
                fileOutputStream.close();
                fileOutChannel.close();
            } catch (IOException e) {}
        }
        return result;
    }
}
