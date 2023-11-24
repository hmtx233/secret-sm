package top.blackcat.sm.utils;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;
import top.blackcat.sm.secret.enums.ErrorCodeEnum;
import top.blackcat.sm.secret.vo.KeyPairVO;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/***
 * sm算法
 */
public class SmUtils {

    /***
     *
     * @param keyHexStr 密钥 十六进制字符串
     * @param ivHexStr  iv向量 十六进制字符串
     * @param plaintext
     * @return
     * @throws InvalidCipherTextException
     */
    public static String encryptBySm4(String keyHexStr, String ivHexStr, String plaintext) throws InvalidCipherTextException {
        // 密钥
        byte[] keyBytes = Hex.decode(keyHexStr);
        KeyParameter key = new KeyParameter(keyBytes);

        // IV向量
        byte[] ivBytes = Hex.decode(ivHexStr);
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
        // 加密结果
        return Hex.toHexString(output);
    }


    /***
     *
     * @param keyHexStr  密钥 十六进制字符串
     * @param ivHexStr   iv向量 十六进制字符串
     * @param ciphertext
     * @return
     * @throws InvalidCipherTextException
     */

    public static String decryptBySm4(String keyHexStr, String ivHexStr, String ciphertext) throws Exception {
        // 密钥
        byte[] keyBytes = Hex.decode(keyHexStr);
        KeyParameter key = new KeyParameter(keyBytes);

        // IV向量
        byte[] ivBytes = Hex.decode(ivHexStr);
        ParametersWithIV ivParams = new ParametersWithIV(key, ivBytes);

        // 创建SM4加密引擎
        BlockCipher engine = new SM4Engine();

        // 使用CBC模式和PKCS7填充方式
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));

        // 设置加密true/解密模式false
        cipher.init(false, ivParams); // 切换为解密模式

        // 待解密的数据
        byte[] input = Hex.decode(ciphertext);

        // 解密
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int len = cipher.processBytes(input, 0, input.length, output, 0);
        cipher.doFinal(output, len);
        // 解密结果
        return new String(output, StandardCharsets.UTF_8).trim();

    }


    /***
     *
     * @param input
     * @return 64位 hash值
     */
    public static String hashBySm3(String input) {
        byte[] data = input.getBytes();
        SM3Digest sm3 = new SM3Digest();
        sm3.update(data, 0, data.length);
        byte[] result = new byte[sm3.getDigestSize()];
        sm3.doFinal(result, 0);
        return org.apache.commons.codec.binary.Hex.encodeHexString(result);
    }


    /***
     *
     * @param privateKeyStr 私钥 十六进制字符串
     * @param signText 要签名的文本 字符串
     * @return 十六进制字符串
     */
    public static String signBySM3withSM2(String privateKeyStr, String signText) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 私钥 十六进制字符串表示
        byte[] privateKeyBytes = Hex.decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory;
        keyFactory = KeyFactory.getInstance("EC", "BC");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("SM3withSM2", "BC");
        signature.initSign(privateKey);
        signature.update(signText.getBytes());
        return Hex.toHexString(signature.sign());


    }


    /***
     * 验签
     * @param publicKeyStr 私钥 十六进制字符串
     * @param signature 签名的 十六进制字符串
     * @param signText 要签名的文本
     * @return boolean
     */
    public static boolean verifyBySM3withSM2(String publicKeyStr, String signature, String signText) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 公钥的十六进制字符串表示
        byte[] privateKeyBytes = Hex.decode(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        Signature verifySignature = Signature.getInstance("SM3withSM2", "BC");
        verifySignature.initVerify(publicKey);
        verifySignature.update(signText.getBytes());
        return verifySignature.verify(Hex.decode(signature));
    }


    /***
     * 生成密钥对
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidAlgorithmParameterException
     */
    public static KeyPairVO getSM3withSM2KeyPair() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 生成密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("sm2p256v1");
        keyPairGenerator.initialize(spec, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取私钥和公钥
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        // 将私钥和公钥转换为字节数组
        byte[] privateKeyBytes = privateKey.getEncoded();
        byte[] publicKeyBytes = publicKey.getEncoded();
        KeyPairVO keyPairVO = new KeyPairVO();
        keyPairVO.setPrivateKey(Hex.toHexString(privateKeyBytes));
        keyPairVO.setPublicKey(Hex.toHexString(publicKeyBytes));
        return keyPairVO;
    }


    /***
     * 随机数生成器 num/2  字节
     * @return
     */
    public static String generateKey(int num) {
        // 创建一个安全的随机数生成器
        SecureRandom secureRandom = new SecureRandom();
        // 创建一个16字节（128位）的随机数组作为 key num=16
        byte[] key = new byte[num];
        secureRandom.nextBytes(key);
        return Hex.toHexString(key);
    }

}
