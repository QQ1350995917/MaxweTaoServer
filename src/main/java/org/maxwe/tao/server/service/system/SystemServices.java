package org.maxwe.tao.server.service.system;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-16 18:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SystemServices implements ISystemServices {
    private static SystemServices instance;
    private SystemServices() {
    }

    public static synchronized SystemServices getInstance() {
        if (instance == null) {
            instance = new SystemServices();
        }
        return instance;
    }

    @Override
    public SystemEntity getSystemStatus() {

        return null;
    }

    /**
     * 数据库备份
     * @param filePath
     * @param name
     * @param password
     * @param db
     * @throws Exception
     */
    @Override
    public synchronized void backup(String filePath, String name, String password, String db) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("/usr/local/bin/mysqldump -u" + name + " -p" + password + " " + db + " --lock-all-tables=true");
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        StringBuffer stringBuffer = new StringBuffer();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\r\n");
        }
        line = stringBuffer.toString();
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(line.getBytes());
        fileOutputStream.close();
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
    }

    /**
     * 数据库恢复
     * @param filePath
     * @param name
     * @param password
     * @param db
     * @throws Exception
     */
    @Override
    public synchronized void recover(String filePath, String name, String password, String db) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("mysql -u" + name + " -p" + password + " --default-character-set=utf8 " + db);
        OutputStream outputStream = process.getOutputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String line;
        StringBuffer stringBuffer = new StringBuffer();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\r\n");
        }
        line = stringBuffer.toString();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8");
        writer.write(line);
        writer.flush();
        outputStream.close();
        bufferedReader.close();
        writer.close();
    }


    @Override
    public synchronized BackupEntity createBackup(BackupEntity backupEntity) {
        Record backupRecord = new Record()
                .set("id", backupEntity.getId())
                .set("name", backupEntity.getName())
                .set("filePath", backupEntity.getFilePath())
                .set("type", backupEntity.getType())
                .set("auto", backupEntity.getAuto())
                .set("counter", backupEntity.getCounter());
        boolean isSave = Db.save("backup", backupRecord);
        if (isSave) {
            return backupEntity;
        } else {
            return null;
        }
    }

    @Override
    public BackupEntity retrieveById(String id) {
        List<Record> records = Db.find("SELECT * FROM backup WHERE id = ?", id);
        if (records != null && records.size() > 0) {
            Map<String, Object> backupMap = records.get(0).getColumns();
            BackupEntity backupEntity = JSON.parseObject(JSON.toJSONString(backupMap), BackupEntity.class);
            return backupEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<BackupEntity> retrieveAll(int pageIndex, int pageSize) {
        LinkedList<BackupEntity> backupEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM backup ORDER BY createTime DESC limit ? , ?", pageIndex * pageSize, pageSize);
        for (Record record : records) {
            backupEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), BackupEntity.class));
        }
        return backupEntities;
    }

}
