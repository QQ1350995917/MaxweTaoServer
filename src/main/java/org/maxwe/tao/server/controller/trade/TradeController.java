package org.maxwe.tao.server.controller.trade;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.common.utils.GrantCodeUtils;
import org.maxwe.tao.server.common.utils.PasswordUtils;
import org.maxwe.tao.server.controller.account.model.TokenModel;
import org.maxwe.tao.server.controller.mate.model.MateModel;
import org.maxwe.tao.server.controller.trade.model.*;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;
import org.maxwe.tao.server.service.history.HistoryEntity;
import org.maxwe.tao.server.service.level.ILevelServices;
import org.maxwe.tao.server.service.level.LevelEntity;
import org.maxwe.tao.server.service.level.LevelServices;
import org.maxwe.tao.server.service.trade.ITradeServices;
import org.maxwe.tao.server.service.trade.TradeServices;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2017-01-09 18:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 交易控制器，包括单码授权交易和多码交易
 */
public class TradeController extends Controller implements ITradeController {
    private final Logger logger = Logger.getLogger(TradeController.class.getName());
    private IAgentServices iAgentServices = new AgentServices();
    private ITradeServices iTradeServices = new TradeServices();
    private ILevelServices iLevelServices = new LevelServices();

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void grant() {
        String params = this.getAttr("p");
        GrantRequestModel requestModel = JSON.parseObject(params, GrantRequestModel.class);
        if (!requestModel.isTokenParamsOk()) {
            this.logger.info("grant : 参数错误 " + requestModel.toString());
            GrantResponseModel grantResponseModel = new GrantResponseModel(requestModel);
            grantResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            grantResponseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(grantResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 这里查询授权码的数量是否足够
        AgentEntity trunkAgentEntity = iAgentServices.retrieveById(requestModel.getId());
        if (trunkAgentEntity == null) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            GrantResponseModel grantResponseModel = new GrantResponseModel(requestModel);
            grantResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            grantResponseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(grantResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (!StringUtils.equals(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getAuthenticatePassword()), trunkAgentEntity.getPassword())) {
            this.logger.info("grant : 认证密码错误 " + requestModel.toString());
            GrantResponseModel grantResponseModel = new GrantResponseModel(requestModel);
            grantResponseModel.setCode(ResponseModel.RC_CONFLICT);
            grantResponseModel.setMessage("密码错误");
            renderJson(JSON.toJSONString(grantResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (trunkAgentEntity.getReach() != 1) {
            this.logger.info("grant : 该账户尚未激活不能进行授权 " + requestModel.toString());
            GrantResponseModel grantResponseModel = new GrantResponseModel(requestModel);
            grantResponseModel.setCode(ResponseModel.RC_FORBIDDEN);
            grantResponseModel.setMessage("您的账户尚未激活，无权授权");
            renderJson(JSON.toJSONString(grantResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (trunkAgentEntity.getLeftCodes() < 1) {
            this.logger.info("grant : 账户的授权数量不足 " + requestModel.toString());
            GrantResponseModel grantResponseModel = new GrantResponseModel(requestModel);
            grantResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            grantResponseModel.setMessage("您的账户授权量不足");
            renderJson(JSON.toJSONString(grantResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(UUID.randomUUID().toString());
        historyEntity.setFromId(trunkAgentEntity.getId());
        historyEntity.setType(1);
        historyEntity.setActCode(GrantCodeUtils.genGrantCode());
        historyEntity.setCodeNum(1);
        historyEntity.setCodeDeal(1 * 0);

        boolean grant = iTradeServices.grant(trunkAgentEntity, historyEntity);
        if (!grant) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            GrantResponseModel grantResponseModel = new GrantResponseModel(requestModel);
            grantResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            grantResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(grantResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        } else {
            this.logger.info("grant : 授权成功 " + requestModel.toString());
            GrantResponseModel grantResponseModel = new GrantResponseModel(requestModel);
            grantResponseModel.setCode(ResponseModel.RC_SUCCESS);
            grantResponseModel.setActCode(historyEntity.getActCode());
            grantResponseModel.setCodeNum(historyEntity.getCodeNum());
            grantResponseModel.setMessage("授权成功");
            renderJson(JSON.toJSONString(grantResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void trade() {
        String params = this.getAttr("p");
        TradeRequestModel requestModel = JSON.parseObject(params, TradeRequestModel.class);
        if (!requestModel.isTradeRequestParamsOk()) {
            this.logger.info("trade : 参数错误 " + requestModel.toString());
            TradeResponseModel responseModel = new TradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 这里查询授权码的数量是否足够
        AgentEntity trunkAgent = iAgentServices.retrieveById(requestModel.getId());
        if (trunkAgent == null) {
            this.logger.info("trade : 服务器内部错误 " + requestModel.toString());
            TradeResponseModel responseModel = new TradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_NOT_FOUND);
            responseModel.setMessage("没找到，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (!StringUtils.equals(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getAuthenticatePassword()), trunkAgent.getPassword())) {
            this.logger.info("trade : 认证密码错误 " + requestModel.toString());
            TradeResponseModel responseModel = new TradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_CONFLICT);
            responseModel.setMessage("密码错误");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (trunkAgent.getLeftCodes() < requestModel.getCodeNum()) {
            this.logger.info("trade : 账户的授权数量不足 " + requestModel.toString());
            TradeResponseModel responseModel = new TradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("您的账户授权量不足");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 检测流向账户信息
        AgentEntity branchAgent = iAgentServices.retrieveById(requestModel.getBranchAgent().getAgent().getId());
        if (branchAgent == null) {
            this.logger.info("trade : 服务器内部错误 " + requestModel.toString());
            TradeResponseModel responseModel = new TradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_NOT_FOUND);
            responseModel.setMessage("找不到下级，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        LevelEntity branchLevel = iLevelServices.retrieveById(requestModel.getBranchAgent().getLevel().getId());
        // 查询级别ID下对应的数量
        if (branchLevel == null) {
            this.logger.info("trade : 找不到级别的ID " + requestModel.toString());
            TradeResponseModel responseModel = new TradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_NOT_FOUND);
            responseModel.setMessage("找不到下级级别，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;

        }

        if (branchAgent.getHaveCodes() < 1) {
            if (requestModel.getCodeNum() < branchLevel.getMinNum()) {
                this.logger.info("trade : 当前转让数量小于最小转让数量 " + requestModel.toString());
                TradeResponseModel responseModel = new TradeResponseModel(requestModel);
                responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
                responseModel.setMessage("转让数量小于最小转让数量");
                renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
                return;
            }
        } else {
            if (requestModel.getCodeNum() < 1) {
                this.logger.info("trade : 当前转让数量小于0 " + requestModel.toString());
                TradeResponseModel responseModel = new TradeResponseModel(requestModel);
                responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
                responseModel.setMessage("转让数量不能小于0");
                renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
                return;
            }
        }

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(UUID.randomUUID().toString());
        historyEntity.setFromId(trunkAgent.getId());
        historyEntity.setToId(branchAgent.getId());
        historyEntity.setType(2);
        historyEntity.setCodeNum(requestModel.getCodeNum());
        historyEntity.setCodeDeal(requestModel.getCodeNum() * branchLevel.getPrice());
        branchAgent.setLevel(branchLevel.getLevel());
        boolean grant = iTradeServices.trade(trunkAgent, branchAgent, historyEntity);
        if (!grant) {
            this.logger.info("trade : 服务器内部错误 " + requestModel.toString());
            TradeResponseModel responseModel = new TradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            responseModel.setMessage("发生错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        this.logger.info("trade : 交易成功 " + requestModel.toString());
        TradeResponseModel tradeResponseModel = new TradeResponseModel();
        tradeResponseModel.setBranchAgent(new MateModel(branchAgent, branchLevel));
        tradeResponseModel.setCodeNum(requestModel.getCodeNum());
        tradeResponseModel.setPrice(branchLevel.getPrice());
        tradeResponseModel.setCodeDeal(requestModel.getCodeNum() * branchLevel.getPrice());
        tradeResponseModel.setCode(ResponseModel.RC_SUCCESS);
        tradeResponseModel.setMessage("转让成功");
        renderJson(JSON.toJSONString(tradeResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void upgrade() {
        String params = this.getAttr("p");
        UpgradeRequestModel requestModel = JSON.parseObject(params, UpgradeRequestModel.class);
        if (!requestModel.isUpgradeRequestParamsOk()) {
            this.logger.info("update : 请求参数错误 " + requestModel.toString());
            UpgradeResponseModel responseModel = new UpgradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 这里查询授权码的数量是否足够
        AgentEntity trunkAgent = iAgentServices.retrieveById(requestModel.getId());
        if (trunkAgent == null) {
            this.logger.info("update : 服务器内部错误 找不到主干账户" + requestModel.toString());
            UpgradeResponseModel responseModel = new UpgradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (!StringUtils.equals(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getAuthenticatePassword()), trunkAgent.getPassword())) {
            this.logger.info("update : 认证密码错误 " + requestModel.toString());
            UpgradeResponseModel responseModel = new UpgradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_CONFLICT);
            responseModel.setMessage("密码错误");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (trunkAgent.getLeftCodes() < requestModel.getCodeNum()) {
            this.logger.info("update : 账户的授权数量不足 " + requestModel.toString());
            UpgradeResponseModel responseModel = new UpgradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("您的授权数量不足");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 检测流向账户信息
        AgentEntity branchAgent = iAgentServices.retrieveById(requestModel.getBranch().getAgent().getId());
        if (branchAgent == null) {
            this.logger.info("update : 服务器内部错误 找不到分支账户" + requestModel.toString());
            UpgradeResponseModel responseModel = new UpgradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_NOT_FOUND);
            responseModel.setMessage("找不到下级账号");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        LevelEntity branchLevel = iLevelServices.retrieveById(requestModel.getBranch().getLevel().getId());
        // 查询分支级别ID下对应的数量
        if (branchLevel == null) {
            this.logger.info("update : 找不到分支账户对应级别的ID " + requestModel.toString());
            UpgradeResponseModel responseModel = new UpgradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_NOT_FOUND);
            responseModel.setMessage("找不到下级账号级别");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        LevelEntity upgradeToLevel = iLevelServices.retrieveById(requestModel.getUpgradeToLevel().getId());
        if (upgradeToLevel == null) {
            this.logger.info("update : 服务器内部错误 找不到要升级到的级别" + requestModel.toString());
            UpgradeResponseModel responseModel = new UpgradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_NOT_FOUND);
            responseModel.setMessage("找不到升级的级别");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (upgradeToLevel.getLevel() > requestModel.getBranch().getLevel().getLevel()) {
            this.logger.info("update : 不能给代理降级 " + requestModel.toString());
            UpgradeResponseModel responseModel = new UpgradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("不能给代理降级");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (branchLevel.getMinNum() + requestModel.getCodeNum() < upgradeToLevel.getMinNum()) {
            if (requestModel.getCodeNum() < branchLevel.getMinNum()) {
                this.logger.info("update : 当前转让数量不足升级 " + requestModel.toString());
                UpgradeResponseModel responseModel = new UpgradeResponseModel(requestModel);
                responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
                responseModel.setMessage("当前转让数量不足以升级");
                renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
                return;
            }
        } else {
            if (requestModel.getCodeNum() < 1) {
                this.logger.info("update : 当前转让数量小于0 " + requestModel.toString());
                UpgradeResponseModel responseModel = new UpgradeResponseModel(requestModel);
                responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
                responseModel.setMessage("当前转让数量不能小于0");
                renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
                return;
            }
        }

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(UUID.randomUUID().toString());
        historyEntity.setFromId(trunkAgent.getId());
        historyEntity.setToId(branchAgent.getId());
        historyEntity.setType(2);
        historyEntity.setCodeNum(requestModel.getCodeNum());
        historyEntity.setCodeDeal(requestModel.getCodeNum() * branchLevel.getPrice());
        branchAgent.setLevel(upgradeToLevel.getLevel());
        boolean grant = iTradeServices.trade(trunkAgent, branchAgent, historyEntity);
        if (!grant) {
            this.logger.info("update : 服务器内部错误 " + requestModel.toString());
            UpgradeResponseModel responseModel = new UpgradeResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            responseModel.setMessage("升级失败，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        this.logger.info("update : 升级成功 " + requestModel.toString());
        UpgradeResponseModel upgradeResponseModel = new UpgradeResponseModel(requestModel);
        upgradeResponseModel.setBranch(new MateModel(branchAgent, branchLevel));
        upgradeResponseModel.setCodeNum(requestModel.getCodeNum());
        upgradeResponseModel.setCode(ResponseModel.RC_SUCCESS);
        upgradeResponseModel.setMessage("升级成功");
        renderJson(JSON.toJSONString(upgradeResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        return;
    }
}
