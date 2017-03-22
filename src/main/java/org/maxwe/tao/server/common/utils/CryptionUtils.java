package org.maxwe.tao.server.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

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


    public static byte[] encryptCustomer(String content,String password) throws Exception {
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密
    }


    public static byte[] decryptCustomer(byte[] content,String password) throws Exception {
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


        String content = "{\"password\":\"694468\",\"cellphone\":\"18511694468\",\"apt\":1,\"smsCode\":\"694468\"}";

        byte[] bytes2 = content.getBytes();

        byte[] bytes = encryptDefault(content);
        for (byte b:bytes){
            System.out.print(b + " ");
        }
        System.out.println();
        String parseByte2HexStr1 = parseByte2HexStr(bytes);
        System.out.println(parseByte2HexStr1);

        String parseByte2HexStr = "BA5A23E8FE59C6493AB66CBED7C1AC5ADB398B0381D1CA711254CDEAB43775037731E7E6E656598E2F2B97AE4FB8607786CC2BCF4A928AB810809BA35B94BCD57FEE45EF299BFF0F3E7812ABD3CFAAD6ADCD003473C63557C2758AA57F1D9FDB5B1811249B943451C48943A9D6C870E7EBFAE99938118A478A3538411A38F5B5952CC5F2A87A89200EB6B6AFBE093D08";
        System.out.println("iOS = " + parseByte2HexStr);

        byte[] bytes1 = decryptDefault(parseHexStr2Byte(parseByte2HexStr));
        String content1 = new String(Base64.decodeBase64(bytes1), Charset.forName("UTF-8"));
        System.out.println(content1);
    }

}
