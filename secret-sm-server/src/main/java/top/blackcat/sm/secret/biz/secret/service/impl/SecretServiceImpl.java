package top.blackcat.sm.secret.biz.secret.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.blackcat.sm.secret.biz.secret.dto.rep.CreateKeyRepDTO;
import top.blackcat.sm.secret.biz.secret.dto.rep.DecryptRepDTO;
import top.blackcat.sm.secret.biz.secret.dto.rep.EncryptRepDTO;
import top.blackcat.sm.secret.biz.secret.dto.req.CreateKeyReqDTO;
import top.blackcat.sm.secret.biz.secret.dto.req.DecryptReqDTO;
import top.blackcat.sm.secret.biz.secret.dto.req.EncryptReqDTO;
import top.blackcat.sm.secret.biz.secret.service.SecretService;
import top.blackcat.sm.secret.biz.secretKey.service.SecretKeyService;
import top.blackcat.sm.secret.entity.SecretKey;
import top.blackcat.sm.secret.enums.ErrorCodeEnum;
import top.blackcat.sm.secret.enums.KeyStateEnum;
import top.blackcat.sm.utils.Base64Utils;
import top.blackcat.sm.utils.SmUtils;

import java.util.Date;


@Slf4j
@Service
public class SecretServiceImpl implements SecretService {


    @Autowired
    private SecretKeyService secretKeyService;

    /****
     * @param reqDTO
     * @return
     */
    @Override
    public CreateKeyRepDTO createKey(CreateKeyReqDTO reqDTO) {
        String key = SmUtils.generateKey(32);
        SecretKey secretKey = new SecretKey();
        secretKey.setKeyState(KeyStateEnum.YES.getCode());
        secretKey.setAppId(reqDTO.getAppId());
        secretKey.setSecretKey(key);
        secretKey.setCreateTime(new Date());
        secretKeyService.save(secretKey);
        return new CreateKeyRepDTO().setKey(Base64Utils.encode(key)).setAppId(reqDTO.getAppId()).setTransId(reqDTO.getTransId()).setKeyState("0");
    }


    /***
     * @param reqDTO
     * @return
     */
    @Override
    public EncryptRepDTO encrypt(EncryptReqDTO reqDTO) {
        String res;
//        reqDTO.setPlainText(Base64Utils.decode(reqDTO.getPlainText()));
        reqDTO.setKey(Base64Utils.decode(reqDTO.getKey()));
        String key = reqDTO.getKey();
        String plainText = reqDTO.getPlainText();
        try {
            res = SmUtils.encryptBySm4(key.substring(0, 32).toString(), key.substring(32).toString(), plainText);
        } catch (Exception e) {
            throw new RuntimeException(ErrorCodeEnum.ERROR_SECRET_KEY.getMsg());
        }
        return new EncryptRepDTO().setTransId(reqDTO.getTransId()).setCipherText(Base64Utils.encode(res));
    }

    /***
     * 解密
     * @param reqDTO
     * @return
     */
    @Override
    public DecryptRepDTO decrypt(DecryptReqDTO reqDTO) throws RuntimeException {
        String res;
        reqDTO.setDecryptText(Base64Utils.decode(reqDTO.getDecryptText()));
        reqDTO.setKey(Base64Utils.decode(reqDTO.getKey()));
        String key = reqDTO.getKey();
        String encData = reqDTO.getDecryptText();
        try {
            res = SmUtils.decryptBySm4(key.substring(0, 32).toString(), key.substring(32).toString(), encData);
        } catch (Exception e) {
            throw new RuntimeException(ErrorCodeEnum.ERROR_SECRET_KEY.getMsg());
        }
        return new DecryptRepDTO().setRequestId(reqDTO.getTransId()).setPlainText(res);

    }


}
