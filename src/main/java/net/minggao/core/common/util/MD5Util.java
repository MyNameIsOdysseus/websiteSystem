package net.minggao.core.common.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */

public class MD5Util {
    public static String getMd5(String md5code) {
        byte[] bt = md5code.getBytes();
        md5code=(new BASE64Encoder()).encodeBuffer(bt);
        //System.out.println("这是加密之后的BASE64编码"+md5code);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(md5code.getBytes("utf-8"));
            byte hash[] = md.digest();
            StringBuffer sb = new StringBuffer();
            int i = 0;
            for (int offset = 0; offset < hash.length; offset++) {
                i = hash[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            //System.out.println("这事加密之后的MD5（Base64）编码"+sb.toString());
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
