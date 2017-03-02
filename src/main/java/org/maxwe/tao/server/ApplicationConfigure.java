package org.maxwe.tao.server;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.maxwe.tao.server.controller.account.agent.AgentController;
import org.maxwe.tao.server.controller.account.manager.ManagerController;
import org.maxwe.tao.server.controller.account.user.UserController;
import org.maxwe.tao.server.controller.history.HistoryController;
import org.maxwe.tao.server.controller.level.LevelController;
import org.maxwe.tao.server.controller.mate.MateController;
import org.maxwe.tao.server.controller.meta.MetaController;
import org.maxwe.tao.server.controller.page.PageController;
import org.maxwe.tao.server.controller.system.SystemController;
import org.maxwe.tao.server.controller.tao.TaoController;
import org.maxwe.tao.server.controller.trade.TradeController;
import org.maxwe.tao.server.controller.version.VersionController;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Pengwei Ding on 2016-07-27 17:55.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 配置管理器
 */
public class ApplicationConfigure extends JFinalConfig {
    public static String APP_KEY = "";
    public static String APP_SECRET = "";
    public static String APP_SMS_KEY = "";
    public static String APP_SMS_SECRET = "";
    public static String APP_SMS_MODEL = "";
    public static String DATABASE_BACKUP_FILE_DIR = "/";
    public static String DATABASE_BACKUP_COMMAND = "";
    public static String DATABASE_MYSQL_COMMAND = "";

    private static String url;
    public static String username;
    public static String password;
    private static String driver;
    public static String databaseName;


    static {
        try {
            LogManager.resetConfiguration();
            Properties log4jProperties = new Properties();
            log4jProperties.load(ApplicationConfigure.class.getClassLoader().getResourceAsStream("log4j.properties"));
            PropertyConfigurator.configure(log4jProperties);
            Properties smsProperties = new Properties();
            smsProperties.load(ApplicationConfigure.class.getClassLoader().getResourceAsStream("properties.properties"));
            APP_KEY = smsProperties.getProperty("APP_KEY");
            APP_SECRET = smsProperties.getProperty("APP_SECRET");
            APP_SMS_KEY = smsProperties.getProperty("APP_SMS_KEY");
            APP_SMS_SECRET = smsProperties.getProperty("APP_SMS_SECRET");
            APP_SMS_MODEL = smsProperties.getProperty("APP_SMS_MODEL");
            DATABASE_BACKUP_FILE_DIR = smsProperties.getProperty("SYSTEM_DATABASE_BACKUP_FILE_DIR");
            DATABASE_BACKUP_COMMAND = smsProperties.getProperty("SYSTEM_DATABASE_BACKUP_COMMAND");
            DATABASE_MYSQL_COMMAND = smsProperties.getProperty("SYSTEM_DATABASE_MYSQL_COMMAND");
            Properties dbProperties = new Properties();
            dbProperties.load(ApplicationConfigure.class.getClassLoader().getResourceAsStream("db.properties"));
            url = dbProperties.getProperty("url");
            username = dbProperties.getProperty("username");
            password = dbProperties.getProperty("password");
            driver = dbProperties.getProperty("driver");
            databaseName = dbProperties.getProperty("databaseName");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setViewType(ViewType.FREE_MARKER);
        me.setFreeMarkerViewExtension(".html");
        me.setEncoding("utf-8");
        me.setError401View("");
        me.setError403View("");
        me.setError404View("");
        me.setError500View("");
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.addGlobalActionInterceptor(new Interceptor() {
            @Override
            public void intercept(Invocation inv) {
//                inv.getController().getResponse().setHeader("Access-Control-Allow-Origin", "http://localhost:63343");
//                inv.getController().getResponse().setHeader("Access-Control-Allow-Origin", "*");
                inv.invoke();
            }
        });
    }

    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("basePath"));
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", PageController.class);
        me.add("/agent", AgentController.class);
        me.add("/user", UserController.class);
        me.add("/history", HistoryController.class);
        me.add("/mate", MateController.class);
        me.add("/meta", MetaController.class);
        me.add("/trade", TradeController.class);
        me.add("/level", LevelController.class);
        me.add("/version", VersionController.class);
        me.add("/tao", TaoController.class);
        me.add("/view", PageController.class);
        me.add("/manager", ManagerController.class);
        me.add("/system", SystemController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = new C3p0Plugin(url + File.separator + databaseName, username, password, driver);
        me.add(c3p0Plugin);
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        activeRecordPlugin.setShowSql(true);
        me.add(activeRecordPlugin);
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();
    }

    @Override
    public void beforeJFinalStop() {
        super.beforeJFinalStop();
    }
}

