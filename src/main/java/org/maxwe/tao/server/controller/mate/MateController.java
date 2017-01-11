package org.maxwe.tao.server.controller.mate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.controller.account.agent.model.FindModel;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;

/**
 * Created by Pengwei Ding on 2017-01-09 18:52.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MateController extends Controller implements IMateController {
    private final Logger logger = Logger.getLogger(MateController.class.getName());
    private IAgentServices iAgentServices = new AgentServices();

    @Override
    @Before(TokenInterceptor.class)
    public void query() {
        String params = this.getAttr("p");
        FindModel requestModel = JSON.parseObject(params, FindModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("find : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        AgentEntity agentEntity = this.iAgentServices.retrieveByMark(requestModel.getTargetMark());
        if (agentEntity == null) {
            this.logger.info("find : 没有找到 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        this.logger.info("find : 查找成功 " + requestModel.toString());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String string = JSON.toJSONString(iResultSet,new PropertyFilter() {
            @Override
            public boolean apply(Object object, String name, Object value) {
                if ("id".equals(name)
                        || "password".equals(name)
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

    @Override
    public void beg() {

    }

    @Override
    public void grant() {

    }

    @Override
    public void reject() {

    }

    @Override
    public void mates() {

    }
}
