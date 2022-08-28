package com.github.baymin.springnative.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5哈希
 *
 * @author BaiZongwei
 * @date 2021/9/2 14:12
 */
public class Md5DigestUtil {

    public static String md5Bit256(String plainText) {
        try {
            // 可以是 SHA-1" 或者 "MD5"
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();// 32字节256位的加密

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5Bit128(String plainText) {
        try {
            // 可以是 SHA-1" 或者 "MD5"
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString().substring(8, 24); // 16字节128位的加密

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5Bit2562(String plainText) {
        try {
            // 可以是 SHA-1" 或者 "MD5"
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();// 32字节256位的加密

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(Md5DigestUtil.md5Bit128("test"));
        System.out.println(Md5DigestUtil.md5Bit256("test"));
        System.out.println(Md5DigestUtil.md5Bit2562("test"));
    }
}