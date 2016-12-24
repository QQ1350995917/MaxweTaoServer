package org.maxwe.init;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Pengwei Ding on 2016-07-30 11:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class InitBase {
    static{
        try {
            Properties prop = new Properties();
            prop.load(InitBase.class.getClassLoader().getResourceAsStream("db.properties"));
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            String driver = prop.getProperty("driver");
            DruidPlugin druidPlugin = new DruidPlugin(url, username, password,driver);

            ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
            druidPlugin.start();
            activeRecordPlugin.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
