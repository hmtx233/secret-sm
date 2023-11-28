package top.blackcat.sm.secret.biz.secret.service;


import top.blackcat.sm.secret.biz.secret.dto.rep.CreateKeyRepDTO;
import top.blackcat.sm.secret.biz.secret.dto.rep.DecryptRepDTO;
import top.blackcat.sm.secret.biz.secret.dto.rep.EncryptRepDTO;
import top.blackcat.sm.secret.biz.secret.dto.req.CreateKeyReqDTO;
import top.blackcat.sm.secret.biz.secret.dto.req.DecryptReqDTO;
import top.blackcat.sm.secret.biz.secret.dto.req.EncryptReqDTO;

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
