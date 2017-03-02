package org.maxwe.tao.server.common.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Pengwei Ding on 2016-07-30 13:20.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class ResultSet implements IResultSet {
    private Object data;
    private int code;
    private String message = "this is tip message!";

    public ResultSet() {
    }

    public ResultSet(int code) {
        this.code = code;
    }

    public ResultSet(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    @JSONField(name = "data")
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @JSONField(name = "code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @JSONField(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    @JSONField(serialize = false)
    public String getJsonDataString() {
        return null;
    }

    @Override
    public IResultSet addIResultSet(IResultSet iResultSet) {
        this.setMessage(this.getMessage());
        return this;
    }
}
