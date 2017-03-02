package org.maxwe.tao.server.service.level;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ILevelServices {

    LevelEntity create(LevelEntity levelEntity);

    LinkedList<LevelEntity> retrieveAll(int pageIndex,int pageSize);

    int retrieveAllNumber();

    LinkedList<LevelEntity> retrieveTop();

    LevelEntity retrieveByNum(int codeNum);


}
