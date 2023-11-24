package top.blackcat.sm.secret.service;

import top.blackcat.sm.secret.dto.encrypt.*;

public interface SecretService {

    /***
     * 产生密钥
     * @param createKeyReqDTO
     * @return
     */
    CreateKeyRepDTO createKey(CreateKeyReqDTO createKeyReqDTO) throws Exception;

    /***
     * 使用密钥加密数据
     * @param encryptReqDTO
     * @return 加密后的文本
     */

    EncryptRepDTO encrypt(EncryptReqDTO encryptReqDTO) throws Exception;


    /***
     * 使用密钥解密数据
     * @param decryptReqDTO
     * @return 解密后的文本
     */
    DecryptRepDTO decrypt(DecryptReqDTO decryptReqDTO) throws Exception;




}
