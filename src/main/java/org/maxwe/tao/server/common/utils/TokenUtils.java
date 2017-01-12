package org.maxwe.tao.server.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by Pengwei Ding on 2017-01-10 14:31.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TokenUtils {

    private static String genRandom() {
        Random random = new Random();
        return (random.nextInt(9) + 1) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + "";
    }

    public static String getToken(String cellphone,String password) {
        try {
            cellphone += password + DateTimeUtils.getCurrentTimestamp() + genRandom();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(cellphone.getBytes());
            String result = new BigInteger(1, md.digest()).toString(16);
            md.update(result.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
