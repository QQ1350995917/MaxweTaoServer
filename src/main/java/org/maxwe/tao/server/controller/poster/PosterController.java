package org.maxwe.tao.server.controller.poster;

import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import org.maxwe.tao.server.service.poster.IPosterServices;
import org.maxwe.tao.server.service.poster.PosterEntity;
import org.maxwe.tao.server.service.poster.PosterServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-15 15:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class PosterController extends Controller implements IPosterController {

    IPosterServices iPosterServices = new PosterServices();

    @Override
    public void index() {

    }

    @Override
    public void retrieves() {
        LinkedList<PosterEntity> posterEntities = iPosterServices.retrieves();
        IResultSet iResultSet = new ResultSet();
        if (posterEntities == null){
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LinkedList<VPosterEntity> responseVPosterEntities = new LinkedList<>();
        for (PosterEntity posterEntity : posterEntities) {
            VPosterEntity vPosterEntity = new VPosterEntity(posterEntity);
            responseVPosterEntities.add(vPosterEntity);
        }
        if (responseVPosterEntities.size() < 1) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVPosterEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "name","imageUrl", "clickable", "href")));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mCreate() {
        String params = this.getPara("p");
        VPosterEntity requestVPosterEntity = JSON.parseObject(params, VPosterEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVPosterEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVPosterEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "name", "fileId", "imageUrl", "clickable", "href")));
            return;
        }

        String posterId = UUID.randomUUID().toString();
        requestVPosterEntity.setPosterId(posterId);
        requestVPosterEntity.setStatus(1);
        PosterEntity result = iPosterServices.mCreate(requestVPosterEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVPosterEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class,  "name", "fileId", "imageUrl", "clickable", "href")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VPosterEntity(result));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "posterId", "name", "fileId", "imageUrl", "clickable", "href")));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mUpdate() {
        String params = this.getPara("p");
        VPosterEntity requestVPosterEntity = JSON.parseObject(params, VPosterEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVPosterEntity.checkUpdateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVPosterEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "posterId", "name", "fileId", "imageUrl", "clickable", "href")));
            return;
        }

        PosterEntity result = iPosterServices.mUpdate(requestVPosterEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVPosterEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "posterId", "name", "fileId", "imageUrl", "clickable", "href")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VPosterEntity(result));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "posterId", "name", "fileId", "imageUrl", "clickable", "href")));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mMark() {
        String params = this.getPara("p");
        VPosterEntity requestVPosterEntity = JSON.parseObject(params, VPosterEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVPosterEntity.checkMarkParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVPosterEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "posterId", "status")));
            return;
        }

        PosterEntity result = null;
        if (requestVPosterEntity.getStatus() == -1) {
            result = iPosterServices.mDelete(requestVPosterEntity);
        } else if (requestVPosterEntity.getStatus() == 1) {
            result = iPosterServices.mBlock(requestVPosterEntity);
        } else if (requestVPosterEntity.getStatus() == 2) {
            result = iPosterServices.mUnBlock(requestVPosterEntity);
        }
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVPosterEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "posterId", "status")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VPosterEntity(result));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "posterId", "status")));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mSwap() {
        String params = this.getPara("p");
        VPosterEntity requestVPosterEntity = JSON.parseObject(params, VPosterEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVPosterEntity.checkSwapParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVPosterEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "posterId1", "posterId2", "weight1", "weight2")));
            return;
        }

        PosterEntity posterEntity1 = new PosterEntity(requestVPosterEntity.getPosterId1(), requestVPosterEntity.getWeight1());
        PosterEntity posterEntity2 = new PosterEntity(requestVPosterEntity.getPosterId2(), requestVPosterEntity.getWeight2());
        PosterEntity[] result = iPosterServices.mSwap(posterEntity1, posterEntity2);//TODO 此处的交换存在漏洞，应该根据ID先查询，根据查询结果进行交换
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVPosterEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "posterId1", "posterId2", "weight1", "weight2")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVPosterEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "posterId1", "posterId2", "weight1", "weight2")));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieves() {
        IResultSet iResultSet = new ResultSet();
        LinkedList<VPosterEntity> responseVPosterEntities = new LinkedList<>();
        LinkedList<PosterEntity> posterEntities = iPosterServices.mRetrieves();
        for (PosterEntity posterEntity : posterEntities) {
            responseVPosterEntities.add(new VPosterEntity(posterEntity));
        }
        if (responseVPosterEntities.size() < 1) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVPosterEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VPosterEntity.class, "posterId", "name", "status", "fileId", "imageUrl", "clickable", "href", "weight")));
    }
}
