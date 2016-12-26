package org.maxwe.tao.server.service.user.proxy;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-12-25 15:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IProxyServices {
    boolean existProxy(String cellphone);
    ProxyEntity createProxy(ProxyEntity proxyEntity);
    ProxyEntity updateProxyPassword(ProxyEntity proxyEntity);
    ProxyEntity retrieveProxy(ProxyEntity proxyEntity);
    ProxyEntity retrieveProxy(String proxyId);
    LinkedList<ProxyEntity> retrieveProxyByPId(String proxyPId);
    ProxyEntity retrieveProxyByCellphone(String cellphone);
}
