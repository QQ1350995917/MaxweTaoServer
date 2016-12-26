package org.maxwe.tao.server.service.user;

/**
 * Created by Pengwei Ding on 2016-12-25 16:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ICSServices {
    boolean existByToken(String token);
    CSEntity create(CSEntity csEntity);
    CSEntity retrieveByToken(String token);
    boolean deleteByToken(String token);
}
