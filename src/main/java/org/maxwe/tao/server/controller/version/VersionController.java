package org.maxwe.tao.server.controller.version;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.service.version.IVersionServices;
import org.maxwe.tao.server.service.version.VersionEntity;
import org.maxwe.tao.server.service.version.VersionServices;

import java.util.List;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2017-01-06 18:06.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class VersionController extends Controller implements IVersionController {
    private final Logger logger = Logger.getLogger(VersionController.class.getName());
    private static IVersionServices iVersionServices = new VersionServices();
//    private static ConcurrentHashMap<String, VersionEntity> versionEntityConcurrentHashMap = new ConcurrentHashMap<>();

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
    public void version() {
        String params = this.getPara("p");
        VersionEntity requestVersionEntity = JSON.parseObject(params, VersionEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVersionEntity == null || StringUtils.isEmpty(requestVersionEntity.getPlatform()) || requestVersionEntity.getType() == 0) {
            this.logger.info("version : 参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVersionEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
            renderJson(resultJson);
            return;
        }

        VersionEntity versionEntity = iVersionServices.retrieveByPlatformAndType(requestVersionEntity.getPlatform(), requestVersionEntity.getType());
        this.logger.info("version : 新版本信息如下: " + versionEntity);
        if (versionEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(versionEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet, commonPropertyFilter);
        renderJson(resultJson);
    }

    @Override
    public void versions() {
        int pageIndex = this.getParaToInt("pageIndex") == null ? 0 : this.getParaToInt("pageIndex");
        int pageSize = (this.getParaToInt("pageSize") == null || this.getParaToInt("pageSize") == 0) ? 12 : this.getParaToInt("pageSize");

        List<VersionEntity> topVersionEntities = iVersionServices.retrieveTopVersion();
        this.setAttr("topVersions", topVersionEntities);

        int accountsSum = iVersionServices.retrieveCounter();

        List<VersionEntity> historyVersionEntities = iVersionServices.retrieveAll(pageIndex, pageSize);
        this.setAttr("historyVersions", historyVersionEntities);
        this.setAttr("pages", accountsSum / pageSize + (accountsSum % pageSize == 0 ? 0 : 1));
        this.setAttr("pageIndex", pageIndex);
        render("/webapp/widgets/systemVersion.view.html");

    }

    @Override
    @Clear({AppInterceptor.class})
    public void create() {
        String platform = this.getPara("platform");
        int type = this.getParaToInt("type") == null ? 0 : this.getParaToInt("type");
        int versionCode = this.getParaToInt("versionCode") == null ? 0 : this.getParaToInt("versionCode");
        int oldVersionCode = this.getParaToInt("oldVersionCode") == null ? 0 : this.getParaToInt("oldVersionCode");
        String versionName = this.getPara("versionName");
        String appName = this.getPara("appName");
        String information = this.getPara("information") == null ? "" : this.getPara("information").trim();
        String url = this.getPara("url");
        int upgrade = this.getParaToInt("upgrade") == null ? 0 : this.getParaToInt("upgrade");

        if (StringUtils.isEmpty(platform)
                || (type != 1 && type != 2)
                || versionCode < 0
                || versionCode < oldVersionCode
                || StringUtils.isEmpty(versionName)
                || StringUtils.isEmpty(appName)
                ) {
            this.logger.info("version create: 参数错误 ");
            renderError(500);
        } else {
            VersionEntity versionEntity = new VersionEntity();
            versionEntity.setVersionId(UUID.randomUUID().toString());
            versionEntity.setPlatform(platform);
            versionEntity.setType(type);
            versionEntity.setVersionCode(versionCode);
            versionEntity.setVersionName(versionName);
            versionEntity.setAppName(appName);
            versionEntity.setInformation(information);
            versionEntity.setUrl(url);
            versionEntity.setUpgrade(upgrade);
            VersionEntity versionEntityResult = iVersionServices.create(versionEntity);
            if (versionEntityResult == null) {
                renderError(500);
            } else {
                versions();
            }
        }
    }
}
