package top.blackcat.sm.secret.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.blackcat.sm.secret.SmUtils;
import top.blackcat.sm.secret.dto.encrypt.CreateKeyRepDTO;
import top.blackcat.sm.secret.service.SecretService;


@Slf4j
@Service
public class SecretServiceImpl implements SecretService {

    @Autowired
    private RedisTemplate redisTemplate;

    private static String key = "4028805e82aebcdc0182b0012c750022";

    private static String GENERATE_KEY_PAIR = "GENERATE_KEY_PAIR";
    private static String KEY_USAGE = "ENCRYPT/DECRYPT";
    private static String ENCRYPT_KEY = "ENCRYPT_KEY";



    /****
     * @param transId
     * @return
     */
    @Override
    public CreateKeyRepDTO createKey(String transId) {

        return null;
    }


    /***
     * @param transId
     * @param plaintext 要加密的文本
     * @return
     */
    @Override
    public String encrypt(String transId, String plaintext) {
        String res = null;
        try {
            res = SmUtils.encryptBySm4(key, plaintext);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }
        return res;
    }

    /***
     * 解密
     * @param transId
     * @param encData 要解密的 密文
     * @return
     */
    @Override
    public String decrypt(String transId, String encData) {
        String res = null;
        try {
            res = SmUtils.decryptBySm4(key, encData);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }
        return res;
    }


}
