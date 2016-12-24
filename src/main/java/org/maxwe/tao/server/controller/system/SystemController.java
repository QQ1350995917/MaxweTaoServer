package org.maxwe.tao.server.controller.system;

import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import org.maxwe.tao.server.service.system.ISystemServices;
import org.maxwe.tao.server.service.system.SystemEntity;
import org.maxwe.tao.server.service.system.SystemServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-11-13 16:18.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SystemController extends Controller implements ISystemController {
    private ISystemServices iSystemServices = new SystemServices();

    @Override
    public void index() {

    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mStatus() {
        SystemEntity systemEntity = iSystemServices.retrieveStatus();
        IResultSet iResultSet = new ResultSet();
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VSystemEntity(systemEntity));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }
}
