package com.github.baymin.netty.crypt.gm;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

/**
 * 测试SM2加解密
 *
 * @author BaiZongwei
 * @date 2021/9/2 15:04
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SM2UtilTest {

    @Test
    @DisplayName("生成密钥对")
    @Order(1)
    public void testGenerateKeyPair() throws IOException {
        AsymmetricCipherKeyPair keyPair = SM2Util.generateKeyPairParameter();

        ECPrivateKeyParameters priKey = (ECPrivateKeyParameters) keyPair.getPrivate();
        ECPublicKeyParameters pubKey = (ECPublicKeyParameters) keyPair.getPublic();

        byte[] privateKeyPKCS8 = BCECUtil.convertECPrivateKeyToPKCS8(priKey, pubKey);
        String privateKeyPem = BCECUtil.convertECPrivateKeyPKCS8ToPEM(privateKeyPKCS8);
        FileUtils.write(new File("target/private.pem"), privateKeyPem, StandardCharsets.UTF_8);
        System.out.println("============生成PEM私钥============");
        System.out.println(privateKeyPem);

        byte[] publicKeyX509 = BCECUtil.convertECPublicKeyToX509(pubKey);
        String publicPem = BCECUtil.convertECPublicKeyX509ToPEM(publicKeyX509);
        FileUtils.write(new File("target/public.pem"), publicPem, StandardCharsets.UTF_8);
        System.out.println("============生成PEM公钥============");
        System.out.println(publicPem);
    }

    @Test
    @DisplayName("加密解密")
    @Order(2)
    public void testEncryptAndDecryptWith123() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException, InvalidCipherTextException {
        Security.addProvider(new BouncyCastleProvider());

        String privateKeyPem = FileUtils.readFileToString(new File("target/private.pem"), StandardCharsets.UTF_8);
        byte[] privateKeyPKCS8 = BCECUtil.convertECPrivateKeyPEMToPKCS8(privateKeyPem);
        ECPrivateKeyParameters priKey = BCECUtil.convertPrivateKeyToParameters(BCECUtil.convertPKCS8ToECPrivateKey(privateKeyPKCS8));

        String publicPem = FileUtils.readFileToString(new File("target/public.pem"), StandardCharsets.UTF_8);
        byte[] publicKeyX509 = BCECUtil.convertECPublicKeyPEMToX509(publicPem);
        ECPublicKeyParameters pubKey = BCECUtil.convertPublicKeyToParameters(BCECUtil.convertX509ToECPublicKey(publicKeyX509));

        String srcData = "8）PKCS#8：私钥信息语法标准。PKCS#8定义了私钥信息语法和加密私钥语法，其中私钥加密使用了PKCS#5标准。";
        byte[] srcDataBytes = srcData.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedData = SM2Util.encrypt(SM2Engine.Mode.C1C2C3, pubKey, srcDataBytes);
        System.out.println("SM2 encrypt result:\n" + ByteUtils.toHexString(encryptedData));
        byte[] decryptedData = SM2Util.decrypt(SM2Engine.Mode.C1C2C3, priKey, encryptedData);
        System.out.println("SM2 decrypt result:\n" + ByteUtils.toHexString(decryptedData));
        System.out.println("SM2 decrypt result:\n" + new String(decryptedData));
    }

}
