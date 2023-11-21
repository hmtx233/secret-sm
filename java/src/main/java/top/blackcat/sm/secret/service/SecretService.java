package top.blackcat.sm.secret.service;

import top.blackcat.sm.secret.dto.encrypt.CreateKeyRepDTO;

public interface SecretService {

    /***
     * 产生密钥
     * @param transId
     * @return
     */
    CreateKeyRepDTO createKey(String transId);


    /***
     * 使用密钥加密数据
     * @param transId
     * @param plaintext
     * @return 加密后的文本
     */
    String encrypt(String transId, String plaintext);


    /***
     * 使用密钥解密数据
     * @param transId
     * @param encData
     * @return 解密后的文本
     */
    String decrypt(String transId, String encData);


}
