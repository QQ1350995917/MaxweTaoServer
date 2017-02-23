package org.maxwe.tao.server.service.tao.bao.favorites;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.maxwe.tao.server.ApplicationConfigure;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

/**
 * Created by Pengwei Ding on 2017-02-23 22:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoFavouritesServices {
    public static void main(String[] args) throws Exception {
        TaoFavoritesRequestModel taoFavoritesRequestModel = new TaoFavoritesRequestModel();
        taoFavoritesRequestModel.setType(1L);
        TaobaoClient taoBaoClient = new DefaultTaobaoClient(APIConstants.URL_FORMAL, ApplicationConfigure.APP_KEY, ApplicationConfigure.APP_SECRET);
        TaoFavouritesResponseModel execute = taoBaoClient.execute(taoFavoritesRequestModel);
        System.out.println(execute.getBody());

    }
}
