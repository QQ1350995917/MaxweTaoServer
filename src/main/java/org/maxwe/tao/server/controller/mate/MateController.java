package org.maxwe.tao.server.controller.mate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.TokenContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.controller.account.agent.model.AgentModel;
import org.maxwe.tao.server.controller.level.LevelController;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.CSEntity;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-09 18:52.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理之间的关系
 */
public class MateController extends Controller implements IMateController {
    private final Logger logger = Logger.getLogger(MateController.class.getName());
    private IAgentServices iAgentServices = new AgentServices();

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void beg() {
        String params = this.getAttr("p");
        TrunkModel requestModel = JSON.parseObject(params, TrunkModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("beg : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 通过mark查找上级
        AgentEntity trunkEntity = this.iAgentServices.retrieveById(requestModel.getTargetId());
        if (trunkEntity == null) {
            this.logger.info("beg : 没有找到 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 上级也没有经过审核通过
        if (trunkEntity.getReach() != 1) {
            this.logger.info("beg : 查找到的尚未通过授权 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        CSEntity csEntity = new CSEntity(0, requestModel.getCellphone(), requestModel.getT(), requestModel.getApt());
        CSEntity existCSEntity = TokenContext.getCSEntity(csEntity);
        AgentEntity branchAgentEntity = this.iAgentServices.retrieveById(existCSEntity.getId());
        if (branchAgentEntity.getpId() != 0){
            this.logger.info("beg : 已经处于等待授权确认 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        branchAgentEntity.setId(existCSEntity.getId());
        branchAgentEntity.setpId(trunkEntity.getId());
        boolean askForReach = iAgentServices.askForReach(branchAgentEntity);
        if (!askForReach) {
            this.logger.info("beg : 申请加入-服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            this.logger.info("beg : 查找成功 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            requestModel.setAgentEntity(trunkEntity);
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String string = JSON.toJSONString(iResultSet, new PropertyFilter() {
                @Override
                public boolean apply(Object object, String name, Object value) {
                    if ("password".equals(name)
                            || "status".equals(name)
                            || "named".equals(name)
                            || "weight".equals(name)
                            ) {
                        return false;
                    }
                    return true;
                }
            });
            renderJson(string);
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void grant() {
        String params = this.getAttr("p");
        TrunkModel requestModel = JSON.parseObject(params, TrunkModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("grant : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 通过mark查找下级
        AgentEntity branchAgentEntity = this.iAgentServices.retrieveById(requestModel.getTargetId());
        if (branchAgentEntity == null) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        branchAgentEntity.setReach(1);
        boolean updateReach = iAgentServices.updateReach(branchAgentEntity);
        if (!updateReach) {
            this.logger.info("grant : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            this.logger.info("grant : 授权代理加入成功 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void reject() {
        String params = this.getAttr("p");
        TrunkModel requestModel = JSON.parseObject(params, TrunkModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("reject : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 通过mark查找下级
        AgentEntity branchAgentEntity = this.iAgentServices.retrieveById(requestModel.getTargetId());
        if (branchAgentEntity == null) {
            this.logger.info("reject : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        branchAgentEntity.setReach(0);
        boolean updateReach = iAgentServices.updateReach(branchAgentEntity);
        if (!updateReach) {
            this.logger.info("reject : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            this.logger.info("reject : 拒绝代理加入成功 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void leader() {
        String params = this.getAttr("p");
        TrunkModel requestModel = JSON.parseObject(params, TrunkModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("leader : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 通过mark查找上级
        AgentEntity trunkEntity = this.iAgentServices.retrieveById(requestModel.getTargetId());
        if (trunkEntity == null) {
            this.logger.info("leader : 服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            this.logger.info("leader : 查找成功 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            requestModel.setAgentEntity(trunkEntity);
            requestModel.setLevelEntity(LevelController.levelByMinNumber(trunkEntity.getHaveCodes()));
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String string = JSON.toJSONString(iResultSet, new PropertyFilter() {
                @Override
                public boolean apply(Object object, String name, Object value) {
                    if ("password".equals(name)
                            || "status".equals(name)
                            || "pId".equals(name)
                            || "named".equals(name)
                            || "weight".equals(name)
                            ) {
                        return false;
                    }
                    return true;
                }
            });
            renderJson(string);
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void mates() {
        String params = this.getAttr("p");
        BranchModel requestModel = JSON.parseObject(params, BranchModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("query : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            CSEntity csEntity = new CSEntity(0, requestModel.getCellphone(), requestModel.getT(), requestModel.getApt());
            LinkedList<AgentEntity> agentEntities = iAgentServices.retrieveByPid(TokenContext.getCSEntity(csEntity).getId(), requestModel.getPageIndex(), requestModel.getPageSize());

            if (agentEntities == null || agentEntities.size() == 0) {
                iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            } else {
                iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            }
            LinkedList<AgentModel> agentModels = new LinkedList<>();
            for (AgentEntity agentEntity:agentEntities){
                AgentModel agentModel = new AgentModel(agentEntity, LevelController.levelByMinNumber(agentEntity.getHaveCodes()));
                agentModels.add(agentModel);
            }
            requestModel.setAgentEntities(agentModels);
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            String resultJson = JSON.toJSONString(iResultSet, new PropertyFilter() {
                @Override
                public boolean apply(Object object, String name, Object value) {
                    if ("password".equals(name)
                            || "status".equals(name)
                            || "named".equals(name)
                            || "weight".equals(name)
                            ) {
                        return false;
                    }
                    return true;
                }
            });
            renderJson(resultJson);
        }
    }
}
