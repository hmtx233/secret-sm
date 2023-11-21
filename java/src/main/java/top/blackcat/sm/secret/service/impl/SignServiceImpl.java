package top.blackcat.sm.secret.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.blackcat.sm.secret.SmUtils;
import top.blackcat.sm.secret.dto.encrypt.CreateKeyRepDTO;
import top.blackcat.sm.secret.dto.unique.GenerateKeyPairRepDTO;
import top.blackcat.sm.secret.service.SecretService;
import top.blackcat.sm.secret.service.SignService;


@Slf4j
@Service
public class SignServiceImpl implements SignService {


    @Autowired
    private RedisTemplate redisTemplate;


    private static String key = "4028805e82aebcdc0182b0012c750012";

    private static String GENERATE_KEY_PAIR = "GENERATE_KEY_PAIR";
    private static String KEY_USAGE = "ENCRYPT/DECRYPT";
    private static String ENCRYPT_KEY = "ENCRYPT_KEY";

    /***
     * 获取签名密钥
     * @param transId
     * @return
     */
    @Override
    public GenerateKeyPairRepDTO generateKeyPair(String transId) {
        return null;
    }


    /***
     * 签名
     * @param digestData 要签名的数据
     * @param transId
     * @return
     */
    @Override
    public String signDigest(String transId, String digestData) {
        return SmUtils.hashBySm3(digestData);
    }


    /**
     * 验签
     *
     * @param transId
     * @param digestData 原文摘要数据的base64编码字符串
     * @param signValue  待验证的Base64编码格式PKCS1签名数据字符串
     * @return
     */
    @Override
    public boolean verifyDigestSign(String transId, String digestData, String signValue) {
        return SmUtils.hashBySm3(digestData).equals(signValue);
    }


}
