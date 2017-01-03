package org.maxwe.tao.server.controller.product;

import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.controller.file.VFFile;
import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.file.FFile;
import org.maxwe.tao.server.service.file.FileServices;
import org.maxwe.tao.server.service.file.IFileServices;
import org.maxwe.tao.server.service.product.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-09-22 17:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TypeController extends Controller implements ITypeController {
    private ISeriesServices iSeriesServices = new SeriesServices();
    private ITypeServices iTypeServices = new TypeServices();
    private IFileServices iFileServices = new FileServices();

    @Override
    public void index() {

    }

    @Override
    public void retrieve() {
        String params = this.getPara("p");
        VTypeEntity requestVTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVTypeEntity.getTypeId() == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId")));
            return;
        }

        LinkedList<VFFile> vfFiles = new LinkedList<>();
        LinkedList<FFile> fFiles = iFileServices.retrieveByTrunkId(requestVTypeEntity.getTypeId());
        for (FFile fFile : fFiles) {
            VFFile vfFile = new VFFile(fFile);
            vfFiles.add(vfFile);
        }

        TypeEntity typeEntity = iTypeServices.retrieveById(requestVTypeEntity.getTypeId());
        if (typeEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        VTypeEntity responseVTypeEntity = new VTypeEntity(typeEntity);
        SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
        if (seriesEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        responseVTypeEntity.setParent(new VSeriesEntity(seriesEntity));
        responseVTypeEntity.setCovers(vfFiles);
        iResultSet.setData(responseVTypeEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(VTypeEntity.class, "seriesId", "typeId", "label", "summary", "directions", "covers"),
                new SimplePropertyPreFilter(VFFile.class,"fileId","path","trunkId"),
                new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")
        }));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mCreate() {
        String params = this.getPara("p");
        VTypeEntity requestVTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVTypeEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "label", "seriesId")));
            return;
        }

        String typeId = UUID.randomUUID().toString();
        requestVTypeEntity.setTypeId(typeId);
        requestVTypeEntity.setStatus(1);
        boolean exist = iTypeServices.mExistInSeries(requestVTypeEntity.getLabel(), requestVTypeEntity.getSeriesId());
        if (exist) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "label", "seriesId")));
            return;
        }

        TypeEntity result = iTypeServices.mCreate(requestVTypeEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "label", "seriesId")));
            return;
        }

        VTypeEntity responseVTypeEntity = new VTypeEntity(result);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseVTypeEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "label", "seriesId", "status")));
    }

    /**
     * 更新产品类型名称
     * 在同一个系列下不能出现相同的类型名称
     */
    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mUpdate() {
        String params = this.getPara("p");
        VTypeEntity requestVTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVTypeEntity.checkUpdateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "label", "seriesId")));
            return;
        }

        boolean exist = iTypeServices.mExistInSeries(requestVTypeEntity.getLabel(), requestVTypeEntity.getSeriesId());
        if (exist) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "label", "seriesId")));
            return;
        }

        TypeEntity result = iTypeServices.mUpdate(requestVTypeEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "label", "seriesId")));
            return;
        }

        VTypeEntity responseVTypeEntity = new VTypeEntity(result);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseVTypeEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "label", "seriesId")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mMark() {
        String params = this.getPara("p");
        VTypeEntity requestVTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVTypeEntity.checkMarkParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "status")));
            return;
        }

        TypeEntity result = null;
        if (requestVTypeEntity.getStatus() == -1) {
            result = iTypeServices.mDelete(requestVTypeEntity);
        } else if (requestVTypeEntity.getStatus() == 1) {
            result = iTypeServices.mBlock(requestVTypeEntity);
        } else if (requestVTypeEntity.getStatus() == 2) {
            result = iTypeServices.mUnBlock(requestVTypeEntity);
        }
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "status")));
            return;
        }

        VTypeEntity responseVTypeEntity = new VTypeEntity(result);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseVTypeEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "status")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mImage() {

    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mImageDelete() {

    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mSummary() {
        String params = this.getPara("p");
        VTypeEntity requestVTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVTypeEntity.checkTypeIdParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "summary")));
            return;
        }

        TypeEntity result = iTypeServices.mUpdateSummary(requestVTypeEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "summary")));
            return;
        }

        VTypeEntity responseVTypeEntity = new VTypeEntity(result);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseVTypeEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "summary")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mDirections() {
        String params = this.getPara("p");
        VTypeEntity requestVTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVTypeEntity.checkTypeIdParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "directions")));
            return;
        }

        TypeEntity result = iTypeServices.mUpdateDirections(requestVTypeEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "directions")));
            return;
        }

        VTypeEntity responseVTypeEntity = new VTypeEntity(result);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseVTypeEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "directions")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieves() {
        String params = this.getPara("p");
        VSeriesEntity requestVSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVSeriesEntity.checkSeriesId()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId")));
            return;
        }

        LinkedList<TypeEntity> typeEntities = iTypeServices.mRetrievesInSeries(requestVSeriesEntity);
        if (typeEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId")));
            return;
        }

        LinkedList<VTypeEntity> responseVTypeEntities = new LinkedList<>();
        for (TypeEntity typeEntity : typeEntities) {
            responseVTypeEntities.add(new VTypeEntity(typeEntity));
        }
        if (responseVTypeEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVTypeEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "seriesId", "label", "status", "queue")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieve() {
        String params = this.getPara("p");
        VTypeEntity requestVTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVTypeEntity.checkTypeIdParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId")));
            return;
        }

        LinkedList<VFFile> vfFiles = new LinkedList<>();
        LinkedList<FFile> fFiles = iFileServices.retrieveByTrunkId(requestVTypeEntity.getTypeId());
        for (FFile fFile : fFiles) {
            VFFile vfFile = new VFFile(fFile);
            vfFiles.add(vfFile);
        }

        TypeEntity result = iTypeServices.mRetrieveById(requestVTypeEntity.getTypeId());
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId")));
            return;
        }

        VTypeEntity responseVTypeEntity = new VTypeEntity(result);
        responseVTypeEntity.setCovers(vfFiles);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseVTypeEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet,
                new SerializeFilter[]{
                        new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "seriesId", "label", "summary", "directions", "status", "covers"),
                        new SimplePropertyPreFilter(VFFile.class,"fileId","path","trunkId")
                }));
    }
}
