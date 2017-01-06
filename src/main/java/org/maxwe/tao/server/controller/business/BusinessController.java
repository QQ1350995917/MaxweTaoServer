package org.maxwe.tao.server.controller.business;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.utils.Code;
import org.maxwe.tao.server.controller.user.VAgentEntity;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.user.CSEntity;
import org.maxwe.tao.server.service.user.agent.AgentEntity;
import org.maxwe.tao.server.service.user.agent.AgentServices;
import org.maxwe.tao.server.service.user.agent.IAgentServices;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-05 16:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class BusinessController extends Controller implements IBusinessController {
    private final Logger logger = Logger.getLogger(BusinessController.class.getName());
    private IAgentServices iAgentServices = new AgentServices();

    private final static PropertyFilter commonPropertyFilter = new PropertyFilter() {
        @Override
        public boolean apply(Object object, String name, Object value) {
            if ("agentId".equals(name) || "agentPId".equals(name) || "grantCode".equals(name) || "level".equals(name)) {
                return false;
            }
            return true;
        }
    };

    /**
     * 获取自己的代理集合
     * 经过拦截器参数是合格的
     * session是存在的
     * 数据是正常的
     * 此处无需校验
     */
    @Override
    @Before(TokenInterceptor.class)
    public void agents() {
        String params = this.getPara("p");
        SubAgentModel requestSubAgentEntity = JSON.parseObject(params, SubAgentModel.class);
        IResultSet iResultSet = new ResultSet();

        CSEntity agentCS = new CSEntity(null, requestSubAgentEntity.getCellphone(), requestSubAgentEntity.getT(),
                requestSubAgentEntity.getType());
        CSEntity csEntity = SessionContext.getCSEntity(agentCS);

        LinkedList<AgentEntity> agentEntities = iAgentServices.retrieveAgentByPId(csEntity.getAgentId(),
                requestSubAgentEntity.getPageIndex(), requestSubAgentEntity.getCounter());
        if (agentEntities == null) {
            this.logger.info("retrieveSubAgents : 服务器查询错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestSubAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t")));
            return;
        }

        if (agentEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        this.logger.info("retrieveSubAgents : 服务器查询数据数量 " + agentEntities.size());
        requestSubAgentEntity.setSubAgents(agentEntities);
        iResultSet.setData(requestSubAgentEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
        renderJson(resultJson);
    }

    /**
     * 通过电话号码查找自己的代理
     * 从而实现授权
     */
    @Override
    @Before(TokenInterceptor.class)
    public void agent() {
        String params = this.getPara("p");
        TradeModel requestVAgentEntity = JSON.parseObject(params, TradeModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVAgentEntity.checkFindAgentParams()) {
            this.logger.info("retrieveAgentByCellphone : 参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        AgentEntity authorizedAgent = iAgentServices.retrieveAgentByCellphone(requestVAgentEntity.getAuthorizedAgent().getCellphone());
        if (authorizedAgent == null) {
            this.logger.info("retrieveAgentByCellphone : 查询结果为空 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        CSEntity agentCS = new CSEntity(null, requestVAgentEntity.getCellphone(), requestVAgentEntity.getT(), requestVAgentEntity.getType());
        CSEntity csEntity = SessionContext.getCSEntity(agentCS);
        if (!StringUtils.equals(authorizedAgent.getAgentPId(),csEntity.getAgentId())){
            this.logger.info("retrieveAgentByCellphone : 此号码不是您的代理，无权进行交易 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        this.logger.info("retrieveAgentByCellphone : 查询结果 " + authorizedAgent.toString());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        requestVAgentEntity.setAuthorizedAgent(authorizedAgent);
        iResultSet.setData(requestVAgentEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
        renderJson(resultJson);
    }

    /**
     * 通过电话号码授权
     */
    @Override
    @Before(TokenInterceptor.class)
    public void grant() {
        String params = this.getPara("p");
        TradeModel requestVAgentEntity = JSON.parseObject(params, TradeModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVAgentEntity.checkGrantParams()) {
            this.logger.info("grantToCellphone : 参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        AgentEntity authorizedAgent = iAgentServices.retrieveAgentByCellphone(requestVAgentEntity.getAuthorizedAgent().getCellphone());
        if (authorizedAgent == null) {
            this.logger.info("grantToCellphone : 被授权人查询结果为空 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        if (!StringUtils.isEmpty(authorizedAgent.getGrantCode())) {
            this.logger.info("grantToCellphone : 发生重复授权 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        CSEntity agentCS = new CSEntity(null, requestVAgentEntity.getCellphone(), requestVAgentEntity.getT(), requestVAgentEntity.getType());
        CSEntity csEntity = SessionContext.getCSEntity(agentCS);
        AgentEntity grantAgent = iAgentServices.retrieveAgentById(csEntity.getAgentId());
        if (grantAgent == null) {
            this.logger.info("grantToCellphone : 查询授权人-服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        if (grantAgent.getLeftCodes() < 1) {
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        authorizedAgent.setLevel(grantAgent.getLevel() + 1);
        authorizedAgent.setGrantCode(Code.enAccessCode(authorizedAgent.getCellphone()));
        authorizedAgent.setAgentPId(grantAgent.getAgentId());

        boolean grantResult = iAgentServices.grantToCellphone(grantAgent, authorizedAgent);
        if (!grantResult) {
            this.logger.info("grantToCellphone : 授权失败-服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        this.logger.info("grantToCellphone : 授权成功 " + params);
        requestVAgentEntity.setAuthorizedAgent(authorizedAgent);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVAgentEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
        renderJson(resultJson);
    }

    /**
     * 授权交易
     */
    @Override
    @Before(TokenInterceptor.class)
    public void trade() {
        String params = this.getPara("p");
        TradeModel requestVAgentEntity = JSON.parseObject(params, TradeModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVAgentEntity.checkTradeParams()) {
            this.logger.info("tradeCodesToCellphone : 参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        AgentEntity authorizedAgent = iAgentServices.retrieveAgentByCellphone(requestVAgentEntity.getAuthorizedAgent().getCellphone());
        if (authorizedAgent == null) {
            this.logger.info("tradeCodesToCellphone : 被授权人查询结果为空 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        CSEntity agentCS = new CSEntity(null, requestVAgentEntity.getCellphone(), requestVAgentEntity.getT(), requestVAgentEntity.getType());
        CSEntity csEntity = SessionContext.getCSEntity(agentCS);
        AgentEntity currentAgent = iAgentServices.retrieveAgentById(csEntity.getAgentId());
        if (currentAgent == null) {
            this.logger.info("tradeCodesToCellphone : 查询授权人-服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        if (currentAgent.getLeftCodes() < requestVAgentEntity.getTradeCode()) {
            this.logger.info("tradeCodesToCellphone : 交易时候拥有的授权数量大于拥有数量 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        if (!iAgentServices.tradeAccessCode(currentAgent, authorizedAgent, requestVAgentEntity.getTradeCode())) {
            this.logger.info("tradeCodesToCellphone : 交易失败-服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        authorizedAgent.setHaveCodes(authorizedAgent.getHaveCodes() + requestVAgentEntity.getTradeCode());
        requestVAgentEntity.setAuthorizedAgent(authorizedAgent);
        this.logger.info("tradeCodesToCellphone : 交易成功 " + params);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVAgentEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
        renderJson(resultJson);
    }
}
