package org.maxwe.tao.server.common.response;

import org.maxwe.tao.server.controller.account.model.TokenModel;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-03-07 11:10.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 相应模型
 */
public class ResponseModel<T extends TokenModel> implements Serializable {
    public transient static final int RC_SUCCESS = 200;//执行成功	执行成功

    public transient static final int RC_BAD_PARAMS = 400;//参数错误	请求提交的参数不符合请求规则
    public transient static final int RC_FORBIDDEN = 403;//权限拒绝	请求中的参数没有权限处理提交的内容
    public transient static final int RC_NOT_FOUND = 404;//找不到内容	请求中的参数在服务器上没有对应的内容
    public transient static final int RC_NOT_ACCEPTABLE = 406;//内容一致导致的冲突	请求中的参数在服务器上完全一致，比如：注册
    public transient static final int RC_CONFLICT = 409;//内容不一致导致的冲突	提交的内容和现有的内容不一致冲突，比如：密码验证
    public transient static final int RC_TO_MANY = 421;//链接频率过高

    public transient static final int RC_SERVER_ERROR = 500;


    private int code;
    private String message;
    private T request;

    public ResponseModel() {
        super();
    }

    public ResponseModel(T requestModel) {
        super();
        this.request = requestModel;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }
}
