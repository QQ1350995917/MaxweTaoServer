package org.maxwe.tao.server.controller.tao.model.alimama;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-03-24 14:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 精简后的商品信息返回给客户端，暂未启用
 */
public class GoodsResponseEntity implements Serializable {
    private long sellerId;//卖家的ID
    private long userType; // 0 淘宝 1天猫
    private String shopTitle; // "隆缘裳服饰旗舰店",
    private String nick; // "隆缘裳服饰旗舰店" 卖家昵称
    private String pictUrl; // "http; ////image.taobao.com/bao/uploaded/i4/TB1kA3XPXXXXXcJXXXXYXGcGpXX_M2.SS2",
    private String title; // "隆缘裳短裙2017春装新款<span class=H>女装</span>简约时尚立领长袖修身印花连衣裙1892",
    private long auctionId; // 544504885384,商品ID
    private String auctionUrl; // "http; ////item.taobao.com/item.htm?id=544504885384",
    private long tkRate; // 14,佣金率
    private float tk3rdRate; //活动发起者淘客的佣金比例
    private float eventRate; // 生效佣金比例
    private float reservePrice; // 663,原来价格
    private float tkCommFee; // 55.72, 淘客佣金
    private float zkPrice; // 398,//淘客价格
    private String couponInfo; // "优惠券满21元减20元",
    private float couponStartFee; // 21,使用优惠券的起始点
    private long couponAmount; // 20,优惠券的额度
    private String couponEffectiveStartTime; // "2016-11-29",优惠券可用起始时间
    private String couponEffectiveEndTime; // "2017-03-30",优惠券可用结束时间
    private long biz30day;//月销量
    private long dayLeft; // -17221,活动剩余天数
    private long couponTotalCount; // 100000
    private long couponLeftCount; // 95200
}
