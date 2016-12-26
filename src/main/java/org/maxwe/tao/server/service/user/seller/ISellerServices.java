package org.maxwe.tao.server.service.user.seller;

/**
 * Created by Pengwei Ding on 2016-12-25 15:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ISellerServices {
    boolean existSeller(String cellphone);
    SellerEntity createSeller(SellerEntity sellerEntity);
    SellerEntity updateSellerPassword(SellerEntity sellerEntity);
    SellerEntity retrieveSeller(SellerEntity sellerEntity);
    SellerEntity retrieveSeller(String sellerId);


}
