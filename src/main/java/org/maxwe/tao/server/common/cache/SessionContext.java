package org.maxwe.tao.server.common.cache;

import org.apache.log4j.Logger;
import org.maxwe.tao.server.service.user.CSEntity;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Pengwei Ding on 2016-10-17 14:55.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SessionContext {
    private final static Logger logger = Logger.getLogger(SessionContext.class.getName());
    /**
     * tokenConcurrentHashMap
     * 可以使用cellphone + type 和 Token作为key
     * cellphone + type 弊端：容易别猜到；利益：数据短，效率高
     * Token 弊端：数据长，效率低，生成新的Token会有旧数据遗留，造成内存浪费；利益：安全
     * 采用：Token作为key
     * 内存解决方式：定期（每周）清理不活跃的Token
     */
    private static ConcurrentHashMap<String, CSEntity> tokenConcurrentHashMap = new ConcurrentHashMap();
    private static final int DURATION = 1000 * 60 * 60 * 24;

    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Iterator<Map.Entry<String, CSEntity>> iterator = tokenConcurrentHashMap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, CSEntity> next = iterator.next();
                            if (System.currentTimeMillis() - next.getValue().getTimestamp() >= DURATION) {
                                CSEntity remove = tokenConcurrentHashMap.remove(next.getKey());
                                logger.info("SessionContext -> 自动删除过期CS链接 : " + remove.toString());
                            }
                        }
                        Thread.sleep(DURATION);
                    } catch (Exception e) {
                        logger.error("SessionContext -> 自动删除过期CS链接 : " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public static void addCSEntity(CSEntity csEntity) {
        tokenConcurrentHashMap.put(csEntity.getToken(), csEntity);
        logger.info("SessionContext -> addCSEntity : " + csEntity.toString());
    }

    public static CSEntity getCSEntity(CSEntity csEntity) {
        CSEntity existCSEntity = tokenConcurrentHashMap.get(csEntity.getToken());
        logger.info("SessionContext -> getCSEntity : " + existCSEntity.toString());
        return existCSEntity;
    }

    public static void delCSEntity(CSEntity csEntity) {
        CSEntity remove = tokenConcurrentHashMap.remove(csEntity.getToken());
        logger.info("SessionContext -> delCSEntity : " + remove.toString());
    }
}
