package org.maxwe.tao.server.controller.version;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import org.maxwe.tao.server.service.version.IVersionServices;
import org.maxwe.tao.server.service.version.VersionEntity;
import org.maxwe.tao.server.service.version.VersionServices;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Pengwei Ding on 2017-01-06 18:06.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VersionController extends Controller implements IVersionController {
    private final Logger logger = Logger.getLogger(VersionController.class.getName());
    private static IVersionServices iVersionServices = new VersionServices();
    private static ConcurrentHashMap<String, VersionEntity> versionEntityConcurrentHashMap = new ConcurrentHashMap<>();

    private final static PropertyFilter commonPropertyFilter = new PropertyFilter() {
        @Override
        public boolean apply(Object object, String name, Object value) {
            if ("versionId".equals(name)) {
                return false;
            }
            return true;
        }
    };

    @Override
    @Before(SessionInterceptor.class)
    public void version() {
        int pageIndex = this.getParaToInt("pageIndex");
        int pageSize = this.getParaToInt("pageSize") == 0 ? 12 : this.getParaToInt("pageSize");

        List<VersionEntity> topVersionEntities = iVersionServices.retrieveTopVersion();
        this.setAttr("topVersions",topVersionEntities);

        int accountsSum = iVersionServices.retrieveCounter();

        List<VersionEntity> historyVersionEntities = iVersionServices.retrieveAll(pageIndex, pageSize);
        this.setAttr("historyVersions",historyVersionEntities);
        this.setAttr("pages", accountsSum / pageSize + (accountsSum % pageSize == 0 ? 0 : 1));
        this.setAttr("pageIndex", pageIndex);
        render("/webapp/widgets/systemVersion.view.html");

//        String params = this.getPara("p");
//        VersionEntity requestVersionEntity = JSON.parseObject(params, VersionEntity.class);
//        IResultSet iResultSet = new ResultSet();
//        if (requestVersionEntity == null || StringUtils.isEmpty(requestVersionEntity.getPlatform()) || requestVersionEntity.getType() == 0) {
//            this.logger.info("version : 参数错误 " + params);
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
//            iResultSet.setData(requestVersionEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
//            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
//            renderJson(resultJson);
//            return;
//        }
//
//        VersionEntity versionEntity = this.versionEntityConcurrentHashMap.get(requestVersionEntity.getPlatform() + requestVersionEntity.getType());
//        this.logger.info("version : 新版本信息如下: " + versionEntity);
//        if (versionEntity == null) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
//        } else {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        }
//        iResultSet.setData(versionEntity);
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
//        renderJson(resultJson);
    }

    @Override
    @Clear({AppInterceptor.class})
    public void reversion() {
        IResultSet iResultSet = new ResultSet();
        List<VersionEntity> reversion = iVersionServices.reversion();
        this.versionEntityConcurrentHashMap.clear();
        for (VersionEntity versionEntity : reversion) {
            this.versionEntityConcurrentHashMap.put(versionEntity.getPlatform() + versionEntity.getType(), versionEntity);
        }
        if (this.versionEntityConcurrentHashMap.isEmpty()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
        iResultSet.setData(reversion);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(iResultSet);
    }
}
