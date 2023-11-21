package top.blackcat.sm;


import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

class EncryptTests {


    @Test
    public void testEncrypt() throws InvalidCipherTextException, IOException {
        String[] names = {"17600165323",
                "17600165323",
                "根据SM2算法的规范，SM2加密后的密文长度为固定的128字节（256个十六进制字符）。无论明文的长度是多少，加密后的密文长度都保持不变。"};
//        for (int i = 0; i < names.length; i++) {
//            String name = sm2Method(names[i]);
//            System.out.println(name + ":" + name.length());
//        }
        System.out.println("**********************************************************************");

        for (int i = 0; i < names.length; i++) {
            String name = sm4Method(names[i]);
            System.out.println(name.equals(names[i]));
            System.out.println(name + ":" + name.length());
        }


    }

    public String sm2Method(String msg) throws InvalidCipherTextException, IOException {
        msg = new BASE64Encoder().encode(msg.getBytes());
        // 选择SM2曲线参数
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("sm2p256v1");
        // 创建ECDomainParameters对象
        ECDomainParameters domainParams = new ECDomainParameters(spec.getCurve(), spec.getG(), spec.getN(), spec.getH());
        // 初始化密钥生成器
        ECKeyPairGenerator generator = new ECKeyPairGenerator();
        ECKeyGenerationParameters keyGenParams = new ECKeyGenerationParameters(domainParams, new SecureRandom());
        generator.init(keyGenParams);
        // 生成密钥对
        AsymmetricCipherKeyPair keyPair = generator.generateKeyPair();
        ECPrivateKeyParameters privateKey = (ECPrivateKeyParameters) keyPair.getPrivate();
        ECPublicKeyParameters publicKey = (ECPublicKeyParameters) keyPair.getPublic();
        // 要加密的明文
        byte[] plaintext = msg.getBytes();
        // 创建SM2加密器
        SM2Engine engine = new SM2Engine();
        // 设置加密模式为加密
        engine.init(true, new ParametersWithRandom(publicKey, new SecureRandom()));
        // 加密明文
        byte[] ciphertext = engine.processBlock(plaintext, 0, plaintext.length);
        // 设置加密模式为解密
        engine.init(false, privateKey);
        // 解密密文
        byte[] decrypted = engine.processBlock(ciphertext, 0, ciphertext.length);
        decrypted = new BASE64Decoder().decodeBuffer(new String(decrypted));
        System.out.println("解密后的明文：" + new String(decrypted).replace(" ", ""));
        return Hex.toHexString(ciphertext);
    }


    public static String sm4Method(String plaintext) throws InvalidCipherTextException {
        String decryptedText = null;
        try {
            // 生成随机密钥
            byte[] keyBytes = generateRandomBytes(16);
            KeyParameter key = new KeyParameter(keyBytes);

            // 生成随机IV向量
            byte[] ivBytes = generateRandomBytes(16);
            ParametersWithIV ivParams = new ParametersWithIV(key, ivBytes);

            // 创建SM4加密引擎
            BlockCipher engine = new SM4Engine();

            // 使用CBC模式和PKCS7填充方式
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));

            // 设置加密/解密模式
            cipher.init(true, ivParams); // true表示加密，false表示解密

            // 待加密的数据
            byte[] input = plaintext.getBytes(StandardCharsets.UTF_8);

            // 加密
            byte[] output = new byte[cipher.getOutputSize(input.length)];
            int len = cipher.processBytes(input, 0, input.length, output, 0);
            cipher.doFinal(output, len);

            // 打印加密结果
            String ciphertext = Hex.toHexString(output);
            System.out.println("加密结果：" + ciphertext);

            // 解密
            cipher.init(false, ivParams); // 切换为解密模式
            byte[] decrypted = new byte[cipher.getOutputSize(output.length)];
            len = cipher.processBytes(output, 0, output.length, decrypted, 0);
            cipher.doFinal(decrypted, len);

            // 打印解密结果
            decryptedText = new String(decrypted);
            System.out.println("解密结果：" + decryptedText.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText.trim();
    }

    private static byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    /***
     *
     * @param input
     * @return 64位 hash值
     */
    public String sm3Method(String input) {
        byte[] data = input.getBytes();
        SM3Digest sm3 = new SM3Digest();
        sm3.update(data, 0, data.length);
        byte[] result = new byte[sm3.getDigestSize()];
        sm3.doFinal(result, 0);
        StringBuilder hex = new StringBuilder();
        for (byte b : result) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }


}
