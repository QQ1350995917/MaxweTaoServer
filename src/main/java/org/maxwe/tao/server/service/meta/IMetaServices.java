package org.maxwe.tao.server.service.meta;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-19 11:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IMetaServices {

    LinkedList<UnitEntity> retrieves();

    LinkedList<SpecialLinkEntity> retrieveSpecialLinks(int pageIndex,int pageSize);

    int specialLinksCount();

    SpecialLinkEntity createSpecialLink(SpecialLinkEntity specialLInkEntity);
}
