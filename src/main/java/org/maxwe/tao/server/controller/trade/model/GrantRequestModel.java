package org.maxwe.tao.server.controller.trade.model;

import org.maxwe.tao.server.controller.account.model.AuthenticateModel;

/**
 * Created by Pengwei Ding on 2017-03-04 11:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 生成单个授权码的请求模型
 * 给普通用户使用，此时仅仅是生成码的请求，而不是用户真正使用
 */
public class GrantRequestModel extends AuthenticateModel {
    public GrantRequestModel() {
        super();
    }
}
