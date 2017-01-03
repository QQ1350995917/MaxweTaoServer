package org.maxwe.tao.server.controller.product;

import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.response.VPageData;
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
 * Created by Pengwei Ding on 2016-09-22 17:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FormatController extends Controller implements IFormatController {
    private ISeriesServices iSeriesServices = new SeriesServices();
    private ITypeServices iTypeServices = new TypeServices();
    private IFormatServices iFormatServices = new FormatServices();
    private IFileServices iFileServices = new FileServices();

    @Override
    public void index() {

    }

    @Override
    public void retrieves() {
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

        LinkedList<FormatEntity> formatEntities = iFormatServices.retrievesInType(requestVTypeEntity);
        if (formatEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId")));
            return;
        }

        LinkedList<VFormatEntity> responseVFormatEntities = new LinkedList<>();
        for (FormatEntity formatEntity : formatEntities) {
            responseVFormatEntities.add(new VFormatEntity(formatEntity));
        }
        if (responseVFormatEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVFormatEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(
                VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status")));
    }

    @Override
    public void retrieve() {
        String params = this.getPara("p");
        VFormatEntity requestVFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVFormatEntity.checkFormatIdParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId")));
            return;
        }

        FormatEntity formatEntity = iFormatServices.retrieveById(requestVFormatEntity.getFormatId());
        if (formatEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId")));
            return;
        }

        VFormatEntity responseFormatEntity = new VFormatEntity(formatEntity);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseFormatEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(
                VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status")));
    }

    @Override
    public void retrieveTree() {

    }

    @Override
    public void retrieveTreeInversion() {
        String params = this.getPara("p");
        VFormatEntity requestVFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVFormatEntity.checkFormatIdParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId")));
            return;
        }

        FormatEntity formatEntity = iFormatServices.retrieveById(requestVFormatEntity.getFormatId());
        if (formatEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId")));
            return;
        }

        TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
        if (typeEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId")));
            return;
        }

        SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
        if (seriesEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId")));
            return;
        }

        VFormatEntity responseFormatEntity = new VFormatEntity(formatEntity);
        VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
        vTypeEntity.setParent(new VSeriesEntity(seriesEntity));
        responseFormatEntity.setParent(vTypeEntity);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseFormatEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(
                        VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                        "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                        "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status", "parent"),
                new SimplePropertyPreFilter(VTypeEntity.class, "seriesId", "typeId", "label", "parent"),
                new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")
        }));
    }

    @Override
    public void recommends() {
        LinkedList<FormatEntity> formatEntities = iFormatServices.retrievesByWeight(0, 0);
        IResultSet iResultSet = new ResultSet();
        if (formatEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId")));
            return;
        }

        LinkedList<VFormatEntity> responseVFormatEntities = new LinkedList<>();
        for (FormatEntity formatEntity : formatEntities) {
            TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());//这里有可能是存在被禁用而无法查找到
            if (typeEntity == null) {
                responseVFormatEntities.clear();
                continue;//存在上层被禁用就先跳过
            }
            SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());//这里有可能是存在被禁用而无法查找到
            if (seriesEntity == null) {
                responseVFormatEntities.clear();
                continue;//存在上层被禁用就先跳过
            }

            LinkedList<VFFile> vfFiles = new LinkedList<>();
            LinkedList<FFile> fFiles = iFileServices.retrieveByTrunkId(typeEntity.getTypeId());
            for (FFile fFile : fFiles) {
                VFFile vfFile = new VFFile(fFile);
                vfFiles.add(vfFile);
            }

            VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
            VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
            vTypeEntity.setCovers(vfFiles);
            VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
            vTypeEntity.setParent(vSeriesEntity);
            vFormatEntity.setParent(vTypeEntity);
            responseVFormatEntities.add(vFormatEntity);
        }

        if (responseVFormatEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVFormatEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(
                        VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                        "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                        "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status", "parent"),
                new SimplePropertyPreFilter(VTypeEntity.class, "seriesId", "typeId", "label", "parent", "covers"),
                new SimplePropertyPreFilter(VFFile.class, "path"),
                new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")
        }));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mCreate() {
        String params = this.getPara("p");
        VFormatEntity requestVFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVFormatEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(
                    VFormatEntity.class, "typeId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                    "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                    "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status")));
            return;
        }
        String formatId = UUID.randomUUID().toString();
        requestVFormatEntity.setFormatId(formatId);
        requestVFormatEntity.setStatus(1);
        boolean mExist = iFormatServices.mExist(requestVFormatEntity);
        if (mExist) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(
                    VFormatEntity.class, "typeId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                    "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                    "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status")));
            return;
        }

        FormatEntity result = iFormatServices.mCreate(requestVFormatEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(
                    VFormatEntity.class, "typeId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                    "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                    "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status")));
            return;
        }

        VFormatEntity responseFormatEntity = new VFormatEntity(result);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseFormatEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(
                VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mUpdate() {
        String params = this.getPara("p");
        VFormatEntity requestVFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVFormatEntity.checkUpdateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(
                    VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                    "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                    "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status")));
            return;
        }

        boolean mExist = iFormatServices.mExist(requestVFormatEntity);
        if (mExist) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(
                    VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                    "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                    "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status")));
            return;
        }

        FormatEntity result = iFormatServices.mUpdate(requestVFormatEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(
                    VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                    "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                    "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status")));
            return;
        }

        VFormatEntity responseFormatEntity = new VFormatEntity(result);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseFormatEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(
                VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mMark() {
        String params = this.getPara("p");
        VFormatEntity requestVFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVFormatEntity.checkMarkParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId", "status")));
            return;
        }

        FormatEntity result = null;
        if (requestVFormatEntity.getStatus() == -1) {
            result = iFormatServices.mDelete(requestVFormatEntity);
        } else if (requestVFormatEntity.getStatus() == 1) {
            result = iFormatServices.mBlock(requestVFormatEntity);
        } else if (requestVFormatEntity.getStatus() == 2) {
            result = iFormatServices.mUnBlock(requestVFormatEntity);
        }
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId", "status")));
            return;
        }

        VFormatEntity responseFormatEntity = new VFormatEntity(result);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseFormatEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId", "status")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mKingWeight() {
        String params = this.getPara("p");
        VFormatEntity requestVFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVFormatEntity.checkFormatIdParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId")));
            return;
        }

        FormatEntity result = iFormatServices.mKingWeight(requestVFormatEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId")));
            return;
        }

        VFormatEntity responseFormatEntity = new VFormatEntity(result);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseFormatEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mSwapWeight() {
        String params = this.getPara("p");
        VFormatEntity requestVFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVFormatEntity.checkSwapParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId1", "formatId2", "weight1", "weight2")));
            return;
        }

        FormatEntity formatEntity1 = new FormatEntity();
        formatEntity1.setFormatId(requestVFormatEntity.getFormatId1());
        formatEntity1.setWeight(requestVFormatEntity.getWeight1());
        FormatEntity formatEntity2 = new FormatEntity();
        formatEntity2.setFormatId(requestVFormatEntity.getFormatId2());
        formatEntity2.setWeight(requestVFormatEntity.getWeight2());
        FormatEntity[] result = iFormatServices.mSwapWeight(formatEntity1, formatEntity2);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVFormatEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId1", "formatId2", "weight1", "weight2")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVFormatEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFormatEntity.class, "formatId1", "formatId2", "weight1", "weight2")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mWeights() {
        String params = this.getPara("p");
        VFormatEntity requestVFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        LinkedList<FormatEntity> formatEntities = iFormatServices.mRetrieveByWeight(requestVFormatEntity.getCurrentPageIndex(), requestVFormatEntity.getSizeInPage());
        IResultSet iResultSet = new ResultSet();
        if (formatEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }
        LinkedList<VFormatEntity> responseVFormatEntities = new LinkedList<>();
        for (FormatEntity formatEntity : formatEntities) {
            TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());//这里有可能是存在被禁用而无法查找到
            if (typeEntity == null) {
                responseVFormatEntities.clear();
                continue;//存在上层被禁用就先跳过
            }
            SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());//这里有可能是存在被禁用而无法查找到
            if (seriesEntity == null) {
                responseVFormatEntities.clear();
                continue;//存在上层被禁用就先跳过
            }
            VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
            VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
            VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
            vTypeEntity.setParent(vSeriesEntity);
            vFormatEntity.setParent(vTypeEntity);
            responseVFormatEntities.add(vFormatEntity);
        }
        int formatCounter = iFormatServices.mCount();
        if (responseVFormatEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(
                new VPageData(formatCounter % requestVFormatEntity.getSizeInPage() == 0 ?
                        formatCounter / requestVFormatEntity.getSizeInPage() : formatCounter / requestVFormatEntity.getSizeInPage() + 1,
                        requestVFormatEntity.getCurrentPageIndex(), requestVFormatEntity.getSizeInPage(), responseVFormatEntities));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(VPageData.class, "totalPageNumber", "currentPageIndex", "sizeInPage", "dataInPage"),
                new SimplePropertyPreFilter(
                        VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                        "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                        "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status", "parent"),
                new SimplePropertyPreFilter(VTypeEntity.class, "seriesId", "typeId", "label", "parent"),
                new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")
        }));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieves() {
        String params = this.getPara("p");
        VTypeEntity requestVTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVTypeEntity.checkTypeIdParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId")));
            return;
        }

        LinkedList<FormatEntity> formatEntities = iFormatServices.mRetrievesInType(requestVTypeEntity);
        if (formatEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVTypeEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VTypeEntity.class, "typeId")));
            return;
        }

        LinkedList<VFormatEntity> responseVFormatEntities = new LinkedList<>();
        for (FormatEntity formatEntity : formatEntities) {
            responseVFormatEntities.add(new VFormatEntity(formatEntity));
        }
        if (responseVFormatEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVFormatEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(
                        VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "price", "priceMeta",
                        "postage", "postageMeta", "pricing", "pricingDiscount", "pricingStart", "pricingEnd", "pricingStatus",
                        "expressCount", "expressName", "expressStart", "expressEnd", "expressStatus", "queue", "weight", "status")}));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieve() {

    }
}
