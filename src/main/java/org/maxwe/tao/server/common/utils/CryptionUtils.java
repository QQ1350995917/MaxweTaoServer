package org.maxwe.tao.server.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.maxwe.tao.server.controller.tao.model.alimama.BrandCreateRequestModel;
import org.maxwe.tao.server.service.tao.alimama.brand.BrandServices;
import org.maxwe.tao.server.service.tao.alimama.brand.GuideEntity;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-01-10 13:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class CryptionUtils {
    private static final String password = "PollKingTueJan10";
    private static final SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");

    public static byte[] encryptDefault(String content) throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//        random.setSeed(password.getBytes());
//        keyGenerator.init(128, random);
//        SecretKey secretKey = keyGenerator.generateKey();
//        byte[] enCodeFormat = secretKey.getEncoded();
//        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//        byte[] byteContent = content.getBytes("UTF-8");
//        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
//        byte[] result = cipher.doFinal(byteContent);
//        return result; // 加密

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密
    }


    public static byte[] decryptDefault(byte[] content) throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//        random.setSeed(password.getBytes());
//
//        keyGenerator.init(128, random);
//        SecretKey secretKey = keyGenerator.generateKey();
//        byte[] enCodeFormat = secretKey.getEncoded();
//        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
//        byte[] result = cipher.doFinal(content);
//        return result; // 加密


        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result; // 加密
    }


    public static byte[] encryptCustomer(String content, String password) throws Exception {
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密
    }


    public static byte[] decryptCustomer(byte[] content, String password) throws Exception {
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result; // 加密
    }


    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
//        String content1 = "QAZWSXEDCRFV-" + System.currentTimeMillis() + "-12345678900";
//        String encodeContent = new String(Base64.getEncoder().encode(content1.getBytes()));
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 100; i++) {
//            CryptionUtils cryptionUtils = new CryptionUtils();
//            String content = JSON.toJSONString(new ExistModel("18511694468"));
//            //加密
////            System.out.println("加密前：" + content);
//            byte[] encryptResult = cryptionUtils.encryptDefault(content);
//            String encryptResultStr = parseByte2HexStr(encryptResult);
//            System.out.println("加密后：" + encryptResultStr);
//            //解密 D6882D57FC66E6D0D256D4C2BBD8BFD4B47C726BF8081E8FF27E876429322D10
////            encryptResultStr = "CCD0ECD33CC34611F22D215E04799F3BFB3D5A0DEF3E9873697E80711E9207E6";
//            byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
//            byte[] decryptResult = cryptionUtils.decryptDefault(decryptFrom);
////            System.out.println("解密后：" + new String(decryptResult));
////            byte[] decode = Base64.getDecoder().decode(decryptResult);
////            System.out.println(new String(decode));
//        }
//        long end = System.currentTimeMillis();
//        System.out.println((end - start) / 1000);

        String string = "CE5F4D20DD9FFCD86FBC6CBA4E100C1B6E9C676C22C3FA23A6EA795D869D4B365849405A1AE5C6194DE697C328B67B9856FD7E5F0209713B33064DD4822C86C18AC51EE118E8D0B300186C5C0F2E02EB9A0C3F65CE934045EF67EA8859CB4C1F3FF558AC0D3BE3B38421900F2563008047D41A4586237E9E5760E894E4AE2D2A7E9DE9B8921E42DFA79F1E56744270DDC2DB6841BCB2DBDF057713A4DF6714D0CF646EEF5A33667839D738C3042198B59E089B2C350F739DC05E96E4A7D38CB2EDEEA5424A5DBA5B6ED18272FD9258724E94871F5170047677A8DC9CBF1F9B6D833E36964B48C39644FDFDAB8AA14B2E509C9248D749D8F9C59E7C7A1A28D51D6DD491EC1863837ADC22E9EBC3D1DCF97805B77F727219FBF7EB82DD5D64B8188C452A97060D82C1038F7D86B5B621283BC2D67E0347F0B26A21D9B04925BDF2BD2BD8BD0C09287727F51C98CB8AA2AF3DF3CC29E746C3B77CE66A69F9BA99A43DD4318F946AF5893902F428D643F0B4ED3BFCCF7813FD4F1F9DA453CA2313C7704E20E5D7C36506A95000D58E74174BD04212CAE2C308BBFDEE10AB4B6489258246905E18FFC6391367DBEC9F41DEA95C79E714D6A87B2B3605D55448DB8957E6618A658BE709279D1B136213F45E9077D490251544E777C903C35F7D6A7490";

        byte[] decrypt = CryptionUtils.decryptDefault(CryptionUtils.parseHexStr2Byte(string));
//                String content = new String(new BASE64Decoder().decodeBuffer(new String(decrypt)));
        String content = new String(Base64.decodeBase64(decrypt), Charset.forName("UTF-8"));

        BrandCreateRequestModel requestModel = JSON.parseObject(content, BrandCreateRequestModel.class);
        List<GuideEntity> brandList = BrandServices.createBrand(requestModel);

        System.out.println(brandList);
    }

}
