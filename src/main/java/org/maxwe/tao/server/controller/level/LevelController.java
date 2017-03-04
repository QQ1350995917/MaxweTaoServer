package org.maxwe.tao.server.controller.level;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.service.level.ILevelServices;
import org.maxwe.tao.server.service.level.LevelEntity;
import org.maxwe.tao.server.service.level.LevelServices;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2017-01-14 10:06.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 更新代理等级的控制器
 */
public class LevelController extends Controller implements ILevelController {
    private final Logger logger = Logger.getLogger(LevelController.class.getName());
    private ILevelServices iLevelServices = new LevelServices();

    @Override
    public void create() {
        this.getResponse().setContentType("application/json; charset=utf-8");
        String name = this.getPara("name");
        String description = this.getPara("description");
        int minNum = this.getParaToInt("minNum") == null ? 0 : this.getParaToInt("minNum");
        float price = this.getParaToLong("price") == null ? 0L : this.getParaToLong("price");
        int level = this.getParaToInt("level") == null ? 0 : this.getParaToInt("level");
        if (StringUtils.isEmpty(name) ||
                minNum < 1 ||
                minNum > 10000 ||
                price < 0 ||
                price > 10000 ||
                level < 1 ||
                level > 5) {
            renderError(400);
            return;
        } else {
            LevelEntity levelEntity = new LevelEntity();
            levelEntity.setId(UUID.randomUUID().toString());
            levelEntity.setName(name);
            levelEntity.setDescription(description);
            levelEntity.setMinNum(minNum);
            levelEntity.setPrice(price);
            levelEntity.setLevel(level);
            if (level == 5) {
                levelEntity.setMinNum(1);
            }
            LevelEntity saveLevelEntity = iLevelServices.create(levelEntity);
            if (saveLevelEntity == null) {
                renderError(400);
                return;
            } else {
                renderJson("{\"result\":\"ok\"}");
            }
        }
    }

    @Override
    public void levels() {
        int pageIndex = this.getParaToInt("pageIndex") == null ? 0 : this.getParaToInt("pageIndex");
        int pageSize = this.getParaToInt("pageSize") == 0 ? 12 : this.getParaToInt("pageSize");

        LinkedList<LevelEntity> topLevelEntities = iLevelServices.retrieveTop();
        this.setAttr("topLevels", topLevelEntities);

        int levelsSum = iLevelServices.retrieveAllNumber();
        LinkedList<LevelEntity> levelEntities = iLevelServices.retrieveAll(pageIndex, pageSize);
        this.setAttr("historyLevels", levelEntities);
        this.setAttr("pages", levelsSum / pageSize + (levelsSum % pageSize == 0 ? 0 : 1));
        this.setAttr("pageIndex", pageIndex);
        render("/webapp/widgets/managerLevelInfo.view.html");
    }

    /**
     * 客户端使用
     */
    @Override
    public void tops() {
        IResultSet iResultSet = new ResultSet();
        LinkedList<LevelEntity> topLevelEntities = iLevelServices.retrieveTop();
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(topLevelEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet);
        renderJson(resultJson);
    }

    /**
     * 客户端查询级别
     */
    @Override
    public void score() {

    }
}
