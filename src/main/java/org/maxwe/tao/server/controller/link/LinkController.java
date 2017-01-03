package org.maxwe.tao.server.controller.link;

import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.link.ILinkServices;
import org.maxwe.tao.server.service.link.LinkEntity;
import org.maxwe.tao.server.service.link.LinkServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LinkController extends Controller implements ILinkController {

    ILinkServices iLinkServices = new LinkServices();

    @Override
    public void index() {

    }

    @Override
    public void retrieves() {
        LinkedList<LinkEntity> linkEntities = iLinkServices.retrieves();
        IResultSet iResultSet = new ResultSet();
        if (linkEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LinkedList<VLinkEntity> responseVLinkEntities = new LinkedList<>();
        for (LinkEntity linkEntity : linkEntities) {
            VLinkEntity vLinkEntity = new VLinkEntity(linkEntity);
            LinkedList<VLinkEntity> childrenVlinkEntities = new LinkedList<>();
            LinkedList<LinkEntity> children = iLinkServices.retrievesByPid(linkEntity.getLinkId());
            for (LinkEntity child : children) {
                childrenVlinkEntities.add(new VLinkEntity(child));
            }
            vLinkEntity.setChildren(childrenVlinkEntities);
            responseVLinkEntities.add(vLinkEntity);
        }

        if (responseVLinkEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVLinkEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "label", "href", "children")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mCreate() {
        String params = this.getPara("p");
        VLinkEntity requestVLinkEntity = JSON.parseObject(params, VLinkEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVLinkEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVLinkEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            if (requestVLinkEntity.getPid() == null) {
                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "label", "href")));
            } else {
                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "pid", "label", "href")));
            }
            return;
        }

        if (requestVLinkEntity.getPid() == null) {
            String linkId = UUID.randomUUID().toString();
            requestVLinkEntity.setLinkId(linkId);
            requestVLinkEntity.setPid(linkId);
        } else {
            String linkId = UUID.randomUUID().toString();
            requestVLinkEntity.setLinkId(linkId);
        }
        requestVLinkEntity.setStatus(1);
        requestVLinkEntity.setWeight(0);
        if (iLinkServices.mExist(requestVLinkEntity)) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVLinkEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            if (requestVLinkEntity.getPid().equals(requestVLinkEntity.getLinkId())) {
                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "label", "href")));
            } else {
                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "pid", "label", "href")));
            }
            return;
        }

        LinkEntity linkEntity = iLinkServices.mCreate(requestVLinkEntity);
        if (linkEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVLinkEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            if (requestVLinkEntity.getPid().equals(requestVLinkEntity.getLinkId())) {
                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "label", "href")));
            } else {
                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "pid", "label", "href")));
            }
            return;
        }
        VLinkEntity responseVLinkEntity = new VLinkEntity(linkEntity);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseVLinkEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId", "pid", "label", "href", "status", "weight")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mUpdate() {
        String params = this.getPara("p");
        VLinkEntity requestVLinkEntity = JSON.parseObject(params, VLinkEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVLinkEntity.checkUpdateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVLinkEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId", "pid", "label", "href")));
            return;
        }

        if (iLinkServices.mExist(requestVLinkEntity)) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVLinkEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId", "pid", "label", "href")));
            return;
        }

        LinkEntity result = iLinkServices.mUpdate(requestVLinkEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVLinkEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId", "pid", "label", "href")));
            return;
        }

        VLinkEntity responseVLinkEntity = new VLinkEntity(result);
        responseVLinkEntity.setCs(requestVLinkEntity.getCs());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseVLinkEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId", "pid", "label", "href")));

    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mMark() {
        String params = this.getPara("p");
        VLinkEntity requestVLinkEntity = JSON.parseObject(params, VLinkEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVLinkEntity.checkMarkParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVLinkEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId", "status")));
            return;
        }

        LinkEntity result = null;
        if (requestVLinkEntity.getStatus() == -1) {
            result = iLinkServices.mDelete(requestVLinkEntity);
        } else if (requestVLinkEntity.getStatus() == 1) {
            result = iLinkServices.mBlock(requestVLinkEntity);
        } else if (requestVLinkEntity.getStatus() == 2) {
            result = iLinkServices.mUnBlock(requestVLinkEntity);
        }

        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVLinkEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId", "status")));
            return;
        }

        VLinkEntity responseVLinkEntity = new VLinkEntity(result);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(responseVLinkEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId", "status")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mSwap() {
        String params = this.getPara("p");
        VLinkEntity requestVLinkEntity = JSON.parseObject(params, VLinkEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVLinkEntity.checkSwapParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVLinkEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId1", "linkId2", "weight1", "weight2")));
            return;
        }
        // TODO 目前这里的策略是采用客户端提交的权重进行设置的，这里存在漏洞，应该采用客户端提交ID，数据库查询，然后交换
        LinkEntity linkEntity1 = new LinkEntity(requestVLinkEntity.getLinkId1(), requestVLinkEntity.getWeight1());
        LinkEntity linkEntity2 = new LinkEntity(requestVLinkEntity.getLinkId2(), requestVLinkEntity.getWeight2());
        LinkEntity[] result = iLinkServices.mSwap(linkEntity1, linkEntity2);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVLinkEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId1", "linkId2", "weight1", "weight2")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVLinkEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId1", "linkId2", "weight1", "weight2")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieves() {
        String params = this.getPara("p");
        VLinkEntity requestVLinkEntity = JSON.parseObject(params, VLinkEntity.class);
        LinkedList<LinkEntity> requestLinkEntities;
        if (requestVLinkEntity.getPid() != null) {
            requestLinkEntities = iLinkServices.mRetrievesByPid(requestVLinkEntity.getPid());
        } else {
            requestLinkEntities = iLinkServices.mRetrieves();
        }

        IResultSet iResultSet = new ResultSet();
        if (requestLinkEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVLinkEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "pid")));
            return;
        }

        LinkedList<VLinkEntity> responseVLinkEntities = new LinkedList<>();
        for (LinkEntity linkEntity : requestLinkEntities) {
            responseVLinkEntities.add(new VLinkEntity(linkEntity));
        }
        if (responseVLinkEntities.size() < 1) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVLinkEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VLinkEntity.class, "linkId", "label", "href", "status", "weight", "pid")));
    }
}
