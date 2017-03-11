package org.maxwe.tao.server.common.sms;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.ApplicationConfigure;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Pengwei Ding on 2016-12-28 17:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 * https://api.alidayu.com/doc2/apiDetail?spm=a3142.8062825.3.1.UIigWc&apiId=25450
 */
public class SMSManager {
    private final static Logger logger = Logger.getLogger(SMSManager.class.getName());
    private static final String url = "http://gw.api.taobao.com/router/rest";
    private static final int DELAYED_ADDRESS = 1000 * 60; // 同一个地址注册的时间间隔
    private static final int DELAYED_CELLPHONE = 1000 * 60 * 10;// 同一个手机号验证码的有效期

    private static final boolean threadRunnable = true;
    private static final ConcurrentHashMap<String, Long> SMS_CACHE_ADDRESS = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, SMSEntity> SMS_CACHE_CELLPHONE = new ConcurrentHashMap<>();

    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (threadRunnable) {
                    try {
                        Iterator<Map.Entry<String, Long>> iterator = SMS_CACHE_ADDRESS.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, Long> next = iterator.next();
                            if (System.currentTimeMillis() - next.getValue() > DELAYED_ADDRESS) {
                                SMS_CACHE_ADDRESS.remove(next.getKey());
                                logger.info("自动删除地址 : " + next.getKey());
                            }
                        }
                        Thread.sleep(DELAYED_ADDRESS);
                    } catch (Exception e) {
                        logger.error("自动删除地址 : " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (threadRunnable) {
                    try {
                        Iterator<Map.Entry<String, SMSEntity>> iterator = SMS_CACHE_CELLPHONE.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, SMSEntity> next = iterator.next();
                            if (System.currentTimeMillis() - next.getValue().getGenTimestamp() > DELAYED_CELLPHONE) {
                                SMS_CACHE_CELLPHONE.remove(next.getKey());
                                logger.info("自动删除验证码 : " + next.getKey());
                            }
                        }
                        Thread.sleep(DELAYED_ADDRESS);
                    } catch (Exception e) {
                        logger.error("自动删除验证码 : " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static boolean isCacheAddress(String address) {
        Long aLong = SMS_CACHE_ADDRESS.get(address);
        if (aLong == null) {
            SMS_CACHE_ADDRESS.put(address, System.currentTimeMillis());
            return false;
        }
        long l = System.currentTimeMillis() - aLong;
        if (l > DELAYED_ADDRESS) {
            SMS_CACHE_ADDRESS.remove(address);
            return false;
        }

        return true;
    }

    private static SMSEntity isCacheCellphone(String cellphone) {
        SMSEntity smsEntityCache = SMS_CACHE_CELLPHONE.get(cellphone);
        if (smsEntityCache == null) {

            return null;
        }
        long genTimestamp = smsEntityCache.getGenTimestamp();
        if (System.currentTimeMillis() - genTimestamp > DELAYED_CELLPHONE) {
            SMS_CACHE_CELLPHONE.remove(cellphone);
            return null;
        }
        return smsEntityCache;
    }

    public static void sendSMS(String cellphone) throws ApiException {
        String code;
        SMSEntity cacheCellphone = isCacheCellphone(cellphone);
        if (cacheCellphone == null) {
            code = new Random().nextInt(10) + ""
                    + new Random().nextInt(10) + ""
                    + new Random().nextInt(10) + ""
                    + new Random().nextInt(10) + ""
                    + new Random().nextInt(10) + ""
                    + new Random().nextInt(10);
            SMS_CACHE_CELLPHONE.put(cellphone, new SMSEntity(cellphone, code));
        } else {
            code = cacheCellphone.getCode();
        }
        TaobaoClient client = new DefaultTaobaoClient(url, ApplicationConfigure.APP_SMS_KEY, ApplicationConfigure.APP_SMS_SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setSmsFreeSignName("淘妈咪");
        req.setSmsParamString("{\"code\":\"" + code + "\"}");
        req.setRecNum(cellphone);
        req.setSmsTemplateCode(ApplicationConfigure.APP_SMS_MODEL);
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        Map map = JSON.parseObject(rsp.getBody(), Map.class);
        //{"alibaba_aliqin_fc_sms_num_send_response":{"result":{"err_code":"0","model":"105243374211^1107192054051","success":true},"request_id":"s75ccxbqypop"}}
        Map alibaba_aliqin_fc_sms_num_send_response = (Map) map.get("alibaba_aliqin_fc_sms_num_send_response");
        if (alibaba_aliqin_fc_sms_num_send_response != null) {
            Map result = (Map) alibaba_aliqin_fc_sms_num_send_response.get("result");
            if (result != null) {
                Object success = result.get("success");
                if (success != null) {
                    if (Boolean.parseBoolean(success.toString())) {
                        logger.debug("sendSMS : " + cellphone + "发送结果成功");
                    } else {
                        logger.error("sendSMS : 发送结果 = " + rsp.getBody());
                    }
                } else {
                    logger.error("sendSMS : 发送结果 = " + rsp.getBody());
                }
            } else {
                logger.error("sendSMS : 发送结果 = " + rsp.getBody());
            }
        } else {
            logger.error("sendSMS : 发送结果 = " + rsp.getBody());
        }
    }

    public static String getSMSCode(String cellphone) {
        return SMS_CACHE_CELLPHONE.get(cellphone) == null ? null : SMS_CACHE_CELLPHONE.get(cellphone).getCode();
    }

    public static void main(String[] args) throws Exception {
//        String[] address = {"123", "456", "789", "012", "345"};
//        SMSEntity[] smsEntities = {new SMSEntity("a", "a"), new SMSEntity("b", "b"), new SMSEntity("c", "c"), new SMSEntity("d", "d"), new SMSEntity("e", "e")};
//        while (true) {
//            try {
//                int i = new Random().nextInt(5);
//                if (isCacheAddress(address[i])) {
//                    System.out.println("您的访问太频繁:" + address[i]);
//                } else {
//                    System.out.println("常规发送");
//                    sendSMS(smsEntities[i].getCellphone());
//                }
//                try {
//                    Thread.sleep(1000 * 1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } catch (ApiException e) {
//                e.printStackTrace();
//            }
//        }

//        sendSMS("18511694468");
    }
}
