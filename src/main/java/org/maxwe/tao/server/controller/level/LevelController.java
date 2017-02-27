package org.maxwe.tao.server.controller.level;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.service.level.ILevelServices;
import org.maxwe.tao.server.service.level.LevelEntity;
import org.maxwe.tao.server.service.level.LevelServices;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Pengwei Ding on 2017-01-14 10:06.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 更新代理等级的控制器
 */
public class LevelController extends Controller implements ILevelController {

    private static long lastRefresh;
    private static final long duration = 1000 * 60 * 60;
    private static ConcurrentLinkedDeque<LevelEntity> levelEntities = new ConcurrentLinkedDeque<>();
    private final Logger logger = Logger.getLogger(LevelController.class.getName());
    private final static ILevelServices iLevelServices = new LevelServices();

    private static void refreshCache() {
        levelEntities.clear();
        LinkedList<LevelEntity> retrieve = iLevelServices.retrieve();
        levelEntities.addAll(retrieve);
    }

    public static LevelEntity levelByMinNumber(int codeNum) {
        if (levelEntities.size() == 0) {
            refreshCache();
        }
        LevelEntity result = null;
        for (LevelEntity levelEntity : levelEntities) {
            int currentOffset = codeNum - levelEntity.getMinNum();
            if (currentOffset >= 0) {
                result = levelEntity;
            } else if (currentOffset < 0) {
                break;
            }
        }
        if (result == null){
            result = levelEntities.getFirst();
        }
        return result;
    }

    @Override
    public void update() {

    }

    @Override
    @Clear({AppInterceptor.class})
    public void refresh() {
        IResultSet iResultSet = new ResultSet();
        if (System.currentTimeMillis() - lastRefresh > duration) {
            this.logger.info("refresh : level缓存正在刷新");
            refreshCache();
            lastRefresh = System.currentTimeMillis();
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        } else {
            this.logger.info("refresh : level读取缓存");
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        }
        iResultSet.setData(levelEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet);
        renderJson(resultJson);
    }

    @Override
    public void list() {

    }

//    @Clear({ParamsInterceptor.class})
//    public void test() {
//        String params = this.getPara("p");
//        IResultSet iResultSet = new ResultSet();
//        LevelEntity levelEntity = levelByMinNumber(Integer.parseInt(params));
//        iResultSet.setData(levelEntity);
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        String resultJson = JSON.toJSONString(iResultSet, new PropertyFilter() {
//            @Override
//            public boolean apply(Object object, String name, Object value) {
//                if ("id".equals(name)) {
//                    return false;
//                }
//                return true;
//            }
//        });
//        renderJson(resultJson);
//    }
}
