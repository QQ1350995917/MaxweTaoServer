package org.maxwe.tao.server.service.system;

import java.io.*;

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

}
