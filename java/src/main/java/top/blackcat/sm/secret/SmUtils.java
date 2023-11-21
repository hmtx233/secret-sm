package top.blackcat.sm.secret;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/***
 * sm算法
 */
public class SmUtils {


    /***
     *
     * @param keyStr 密钥 32位 String
     * @param plaintext
     * @return
     * @throws InvalidCipherTextException
     */
    public static String encryptBySm4(String keyStr, String plaintext) throws InvalidCipherTextException {
        // 密钥
        byte[] keyBytes = Hex.decode(keyStr);
        KeyParameter key = new KeyParameter(keyBytes);

        // IV向量
        byte[] ivBytes = Hex.decode(keyStr);
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
     * @param keyStr 密钥 32位 String
     * @param ciphertext
     * @return
     * @throws InvalidCipherTextException
     */
    public static String decryptBySm4(String keyStr, String ciphertext) throws InvalidCipherTextException {
        // 密钥
        byte[] keyBytes = Hex.decode(keyStr);
        KeyParameter key = new KeyParameter(keyBytes);

        // IV向量
        byte[] ivBytes = Hex.decode(keyStr);
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


    public static void main(String[] args) throws InvalidCipherTextException {
        String ivStr = UUID.randomUUID().toString().replace("-", "");
        String keyStr = "21074dbe190340808d9fd4e1edf3b6b9";
        String encrypt = encryptBySm4(keyStr, "@任昊霖 于静有效期到期了，面板机自动清理了，这种问题以后可以修改个人信息重新下发人脸。");
        String decrypt = decryptBySm4(keyStr, encrypt);
        System.out.println(decrypt);
        String input = "hello";
        String hashValue = hashBySm3(input);
        System.out.println(hashValue);
    }


}
