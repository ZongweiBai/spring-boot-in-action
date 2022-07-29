package com.github.baymin.netty.crypt;

import com.github.baymin.netty.crypt.dh.DHCoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Map;

/**
 * DH算法测试
 *
 * @author BaiZongwei
 * @date 2021/8/25 11:03
 */
public class DHCoderTest {

    //甲方公钥
    private static byte[] publicKey1;
    //甲方私钥
    private static byte[] privateKey1;
    //甲方本地密钥
    private static byte[] key1;
    //乙方公钥
    private static byte[] publicKey2;
    //乙方私钥
    private static byte[] privateKey2;
    //乙方本地密钥
    private static byte[] key2;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true");
    }

    @DisplayName("测试DH加密算法")
    @Test
    void testDHCoder() throws Exception {
        initKey();
        System.out.println();
        System.out.println("===甲方向乙方发送加密数据===");
        String input1 = "求知若饥，虚心若愚。";
        System.out.println("原文:\n" + input1);
        System.out.println("---使用甲方本地密钥对数据进行加密---");
        //使用甲方本地密钥对数据加密
        byte[] encode1 = DHCoder.encrypt(input1.getBytes(), key1);
        System.out.println("加密:\n" + Base64.getEncoder().encodeToString(encode1));
        System.out.println("---使用乙方本地密钥对数据库进行解密---");
        //使用乙方本地密钥对数据进行解密
        byte[] decode1 = DHCoder.decrypt(encode1, key2);
        String output1 = new String(decode1);
        System.out.println("解密:\n" + output1);

        System.out.println("/~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~..~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~/");
        initKey();
        System.out.println("===乙方向甲方发送加密数据===");
        String input2 = "好好学习，天天向上。";
        System.out.println("原文:\n" + input2);
        System.out.println("---使用乙方本地密钥对数据进行加密---");
        //使用乙方本地密钥对数据进行加密
        byte[] encode2 = DHCoder.encrypt(input2.getBytes(), key2);
        System.out.println("加密:\n" + Base64.getEncoder().encodeToString(encode2));
        System.out.println("---使用甲方本地密钥对数据进行解密---");
        //使用甲方本地密钥对数据进行解密
        byte[] decode2 = DHCoder.decrypt(encode2, key1);
        String output2 = new String(decode2);
        System.out.println("解密:\n" + output2);
    }

    /**
     * 初始化密钥
     *
     * @throws Exception
     */
    private void initKey() throws Exception {
        //生成甲方密钥对
        Map<String, Object> keyMap1 = DHCoder.initKey();
        publicKey1 = DHCoder.getPublicKey(keyMap1);
        privateKey1 = DHCoder.getPrivateKey(keyMap1);
        System.out.println("甲方公钥:\n" + Base64.getEncoder().encodeToString(publicKey1));
        System.out.println("甲方私钥:\n" + Base64.getEncoder().encodeToString(privateKey1));

        //由甲方公钥产生本地密钥对
        Map<String, Object> keyMap2 = DHCoder.initKey(publicKey1);
        publicKey2 = DHCoder.getPublicKey(keyMap2);
        privateKey2 = DHCoder.getPrivateKey(keyMap2);
        System.out.println("乙方公钥:\n" + Base64.getEncoder().encodeToString(publicKey2));
        System.out.println("乙方私钥:\n" + Base64.getEncoder().encodeToString(privateKey2));
        key1 = DHCoder.getSecretKey(publicKey2, privateKey1);
        System.out.println("甲方本地密钥:\n" + Base64.getEncoder().encodeToString(key1));
        key2 = DHCoder.getSecretKey(publicKey1, privateKey2);
        System.out.println("乙方本地密钥:\n" + Base64.getEncoder().encodeToString(key2));
    }

}
