package org.maxwe.tao.server.controller.file;

import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.file.FFile;
import org.maxwe.tao.server.service.file.FileServices;
import org.maxwe.tao.server.service.file.IFileServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-11-01 15:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FileController extends Controller implements IFileController {
    private IFileServices iFileServices = new FileServices();

    @Override
    public void index() {

    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mTypeCover() {
        UploadFile file = this.getFile();
        String params = this.getPara("p");
        IResultSet iResultSet = new ResultSet();
        VFFile requestVFFileEntity = JSON.parseObject(params, VFFile.class);
        if (!requestVFFileEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVFFileEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            return;
        }

        String filePath = "/upload/" + file.getFileName();
//        boolean fileCopy = FileServices.fileCopy(file.getFile(), new File(filePath));
//        if (!fileCopy) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVFFileEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            return;
//        }
        FFile fFile;
        if (requestVFFileEntity.getFileId() == null) {
            requestVFFileEntity.setFileId(UUID.randomUUID().toString());
            requestVFFileEntity.setPath(filePath);
            requestVFFileEntity.setType("image");
            requestVFFileEntity.setStatus(2);
            fFile = iFileServices.mCreate(requestVFFileEntity);
        } else {
            requestVFFileEntity.setPath(filePath);
            fFile = iFileServices.mUpdate(requestVFFileEntity);
        }

        if (fFile == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVFFileEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VFFile(fFile));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFFile.class, "fileId", "path", "type", "trunkId")));
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mTypeDirectionImage() {
        UploadFile file = this.getFile();
        String filePath = "/upload/direction_" + file.getFileName();
        IResultSet iResultSet = new ResultSet();
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VFFile(filePath));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFFile.class, "path")));
    }
}
