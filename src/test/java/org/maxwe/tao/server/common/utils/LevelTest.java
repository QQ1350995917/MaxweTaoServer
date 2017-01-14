package org.maxwe.tao.server.common.utils;

import org.maxwe.tao.server.service.level.LevelEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-14 10:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LevelTest {
    private static LinkedList<LevelEntity> levelEntities = new LinkedList<>();

    static {
        LevelEntity levelEntity1 = new LevelEntity("分销", 3);
        LevelEntity levelEntity2 = new LevelEntity("一级代理", 20);
        LevelEntity levelEntity3 = new LevelEntity("总代", 100);
        LevelEntity levelEntity4 = new LevelEntity("联创", 1000);

        levelEntities.add(levelEntity1);
        levelEntities.add(levelEntity2);
        levelEntities.add(levelEntity3);
        levelEntities.add(levelEntity4);
    }

    private static LevelEntity byMin(int min) {
        LevelEntity result = null;
        for (LevelEntity levelEntity : levelEntities) {
            int currentOffset = min - levelEntity.getMinNum();
            if (currentOffset >= 0) {
                result = levelEntity;
            } else if (currentOffset < 0) {
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1300; i++) {
            LevelEntity levelEntity = byMin(i);
            if (levelEntity == null) {
                System.out.println("数量：" + i + ", 级别：没级别");
            } else {
                System.out.println("数量：" + i + ", 级别：" + levelEntity.getName());
            }
        }
    }
}
