package org.maxwe.tao.server.controller.meta;

import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.service.meta.IMetaServices;
import org.maxwe.tao.server.service.meta.MetaServices;
import org.maxwe.tao.server.service.meta.UnitEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-11 14:44.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 元数据
 */
public class MetaController extends Controller implements IMetaController {

    private IMetaServices metaServices = new MetaServices();

    public void index(){
    }

    @Override
    public void units() {
        LinkedList<VUnitEntity> vUnitEntities = new LinkedList<>();
        LinkedList<UnitEntity> unitEntities = metaServices.retrieves();
        for (UnitEntity unitEntity : unitEntities){
            vUnitEntities.add(new VUnitEntity(unitEntity));
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(vUnitEntities);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void address() {

    }
}
