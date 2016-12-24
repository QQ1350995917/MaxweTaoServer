package org.maxwe.tao.server;

import org.maxwe.tao.server.controller.cart.CartController;
import org.maxwe.tao.server.controller.file.FileController;
import org.maxwe.tao.server.controller.link.LinkController;
import org.maxwe.tao.server.controller.manager.ManagerController;
import org.maxwe.tao.server.controller.menu.MenuController;
import org.maxwe.tao.server.controller.meta.MetaController;
import org.maxwe.tao.server.controller.order.OrderController;
import org.maxwe.tao.server.controller.page.PageController;
import org.maxwe.tao.server.controller.poster.PosterController;
import org.maxwe.tao.server.controller.product.FormatController;
import org.maxwe.tao.server.controller.product.SeriesController;
import org.maxwe.tao.server.controller.product.TypeController;
import org.maxwe.tao.server.controller.receiver.ReceiverController;
import org.maxwe.tao.server.controller.system.SystemController;
import org.maxwe.tao.server.controller.user.AccountController;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Pengwei Ding on 2016-07-27 17:55.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 配置管理器
 */
public class ApplicationConfigure extends JFinalConfig {
    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    static {
        try {
            Properties prop = new Properties();
            prop.load(ApplicationConfigure.class.getClassLoader().getResourceAsStream("db.properties"));
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            driver = prop.getProperty("driver");
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
                inv.getController().getResponse().setHeader("Access-Control-Allow-Origin", "*");
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
        me.add("/meta", MetaController.class);
        me.add("/menu", MenuController.class);
        me.add("/manager", ManagerController.class);
        me.add("/series", SeriesController.class);
        me.add("/type", TypeController.class);
        me.add("/format", FormatController.class);
        me.add("/link", LinkController.class);
        me.add("/poster", PosterController.class);
        me.add("/account", AccountController.class);
        me.add("/cart", CartController.class);
        me.add("/order", OrderController.class);
        me.add("/receiver", ReceiverController.class);
        me.add("/file", FileController.class);
        me.add("/system", SystemController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = new C3p0Plugin(url, username, password, driver);
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

