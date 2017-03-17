package org.maxwe.tao.server.controller.system;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.ApplicationConfigure;
import org.maxwe.tao.server.common.utils.DateTimeUtils;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;
import org.maxwe.tao.server.service.system.BackupEntity;
import org.maxwe.tao.server.service.system.SystemServices;
import org.maxwe.tao.server.service.tao.jidi.JiDiServices;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2017-02-14 21:48.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class SystemController extends Controller implements ISystemController {
    private final Logger logger = Logger.getLogger(SystemController.class.getName());
    private IAgentServices iAgentServices = new AgentServices();

    @Override
    @Before(SessionInterceptor.class)
    public void system() {
        render("/webapp/widgets/systemInfoList.view.html");
    }

    @Override
    @Before(SessionInterceptor.class)
    public void money() {
        LinkedList<AgentEntity> agentEntities = iAgentServices.retrieveByTop();
        this.setAttr("topAgents", agentEntities);
        render("/webapp/widgets/systemMoneyList.view.html");
    }

    @Override
    @Before(SessionInterceptor.class)
    public void append() {
        this.getResponse().setContentType("application/json; charset=utf-8");
        int id = this.getParaToInt("id") == null ? 0 : this.getParaToInt("id");
        int appendCodes = this.getParaToInt("appendCodes");
        if (id == 0 || appendCodes <= 0 || appendCodes > 1000) {
            renderError(400);
        } else {
            AgentEntity agentEntity = new AgentEntity();
            agentEntity.setId(id);
            boolean appendResult = iAgentServices.appendCodes(agentEntity, appendCodes);
            if (appendResult) {
                renderJson("{\"result\":\"ok\"}");
            } else {
                renderError(500);
            }
        }
    }

    @Override
    @Before(SessionInterceptor.class)
    public void backups() {
        int pageIndex = this.getParaToInt("pageIndex") == null ? 0 : this.getParaToInt("pageIndex");
        int pageSize = (this.getParaToInt("pageSize") == null || this.getParaToInt("pageSize") == 0) ? 12 : this.getParaToInt("pageSize");
        LinkedList<BackupEntity> backupEntities = SystemServices.getInstance().retrieveAll(pageIndex, pageSize);
        this.setAttr("databaseBackups", backupEntities);
        render("/webapp/widgets/systemBackups.view.html");
    }

    @Override
    @Before(SessionInterceptor.class)
    public void backup() {
        this.getResponse().setContentType("application/json; charset=utf-8");
        try {
            String filePath = ApplicationConfigure.DATABASE_BACKUP_FILE_DIR + File.separator + DateTimeUtils.getCurrentTimestamp() + ".sql";
            this.logger.info("backup : 路径 " + filePath);
            SystemServices.getInstance().backup(filePath, ApplicationConfigure.username, ApplicationConfigure.password, ApplicationConfigure.databaseName);
            BackupEntity backupEntity = SystemServices.getInstance().createBackup(new BackupEntity(UUID.randomUUID().toString(), null, filePath, 1, 1, 0));
            if (backupEntity == null) {
                renderError(500);
                this.logger.info("backup : 备份失败 ");
            } else {
                this.logger.info("backup : 备份成功 ");
                renderJson("{\"result\":\"ok\"}");
            }
        } catch (Exception e) {
            this.logger.info("backup : 备份错误 " + e.getMessage());
            e.printStackTrace();
            renderError(500);
        }
    }

    @Override
    @Before(SessionInterceptor.class)
    public void download() {
        int type = this.getParaToInt("type") == null ? 0 : this.getParaToInt("type");
        String id = this.getPara("id");
        if (type == 0 || StringUtils.isEmpty(id)) {
            renderError(400);
        } else {
            if (type == 1) {
                BackupEntity backupEntity = SystemServices.getInstance().retrieveById(id);
                this.logger.info("download : 文件路径 " + backupEntity.getFilePath());
                renderFile(new File(backupEntity.getFilePath()));
            } else if (type == 2) {
                renderError(400);
            } else {
                renderError(400);
            }
        }
    }

    @Override
    @Before(SessionInterceptor.class)
    public void initThird() {
        JiDiServices.getInstance().init();
        renderJson(new String[]{"ok"});
    }

    @Override
    @Before(SessionInterceptor.class)
    public void summaryThird() {
        int dataCounter = JiDiServices.getInstance().getDataCounter();
        this.setAttr("dataCounter", dataCounter);
        render("/webapp/widgets/businessThirdData.view.html");
    }

    @Override
    public void logger() {
        try {
            String date = this.getPara("date");
            this.logger.info("logger : 文件名称 " + date);
            if (StringUtils.isEmpty(date)) {
                File file = new File(ApplicationConfigure.LOGGER_REAL_TIME + "/tao");
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes, 0, bytes.length);
                this.setAttr("logger", new String(bytes, Charset.forName("UTF-8")));
                fileInputStream.close();
                render("/webapp/widgets/logger.html");
            } else {
                File file = new File(ApplicationConfigure.LOGGER_REAL_TIME + "/tao." + date + ".log");
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes, 0, bytes.length);
                this.setAttr("logger", new String(bytes, Charset.forName("UTF-8")));
                fileInputStream.close();
                render("/webapp/widgets/logger.html");
            }
        } catch (Exception e) {
            render(e.getMessage());
        }

    }
}
