package top.blackcat.sm.secret.biz.sign.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.blackcat.sm.secret.biz.sign.dto.rep.CreateKeyPairRepDTO;
import top.blackcat.sm.secret.biz.sign.dto.rep.SignDigestRepDTO;
import top.blackcat.sm.secret.biz.sign.dto.rep.VerifyDigestSignRepDTO;
import top.blackcat.sm.secret.biz.sign.dto.req.CreateKeyPairReqDTO;
import top.blackcat.sm.secret.biz.sign.dto.req.SignDigestReqDTO;
import top.blackcat.sm.secret.biz.sign.dto.req.VerifyDigestSignReqDTO;
import top.blackcat.sm.secret.biz.sign.service.SignService;
import top.blackcat.sm.secret.biz.signKey.service.SignKeyService;
import top.blackcat.sm.secret.entity.SignKey;
import top.blackcat.sm.secret.enums.ErrorCodeEnum;
import top.blackcat.sm.secret.enums.KeyStateEnum;
import top.blackcat.sm.secret.vo.KeyPairVO;
import top.blackcat.sm.utils.Base64Utils;
import top.blackcat.sm.utils.SmUtils;

import java.util.Date;
import java.util.UUID;


@Slf4j
@Service
public class SignServiceImpl implements SignService {

    @Autowired
    private SignKeyService signKeyService;


    /***
     * 获取签名密钥
     * @param reqDTO
     * @return
     */
    @Override
    public CreateKeyPairRepDTO createKey(CreateKeyPairReqDTO reqDTO) throws Exception {
        KeyPairVO keyPairVO = SmUtils.getSM3withSM2KeyPair();
        String keyPairId = UUID.randomUUID().toString().replace("-", "");
        CreateKeyPairRepDTO repDTO = new CreateKeyPairRepDTO().setAppId(reqDTO.getAppId()).
                setTransId(reqDTO.getTransId()).
                setKeyPairId(keyPairId);
        repDTO.setKeyPairId(Base64Utils.encode(repDTO.getKeyPairId()));
        SignKey signKey = new SignKey();
        signKey.setKeyState(KeyStateEnum.YES.getCode());
        signKey.setAppId(reqDTO.getAppId());
        signKey.setKeyPairId(keyPairId);
        signKey.setPublicKey(keyPairVO.getPublicKey());
        signKey.setPrivateKey(keyPairVO.getPrivateKey());
        signKey.setCreateTime(new Date());
        signKeyService.save(signKey);
        return repDTO;
    }


    /***
     * 签名
     * @param reqDTO
     * @return
     */
    @Override
    public SignDigestRepDTO signDigest(SignDigestReqDTO reqDTO) throws RuntimeException {
        String keyPairId = Base64Utils.decode(reqDTO.getKeyPairId());
        SignKey signKey = signKeyService.selectByKeyPairId(keyPairId);
        reqDTO.setKeyPairId(keyPairId);
        reqDTO.setDigestData(Base64Utils.decode(reqDTO.getDigestData()));
        String signValue;
        try {
            signValue = SmUtils.signBySM3withSM2(signKey.getPrivateKey(), reqDTO.getDigestData());
        } catch (Exception e) {
            throw new RuntimeException(ErrorCodeEnum.ERROR_SIGN_KEY.getMsg());
        }
        return new SignDigestRepDTO().setSignValue(Base64Utils.encode(signValue)).setTransId(reqDTO.getAppId());

    }


    /**
     * 验签
     *
     * @param reqDTO
     * @return
     */
    @Override
    public VerifyDigestSignRepDTO verifyDigestSign(VerifyDigestSignReqDTO reqDTO) throws RuntimeException {
        String keyPairId = Base64Utils.decode(reqDTO.getKeyPairId());
        SignKey signKey = signKeyService.selectByKeyPairId(keyPairId);
        reqDTO.setKeyPairId(keyPairId);
        reqDTO.setDigestData(Base64Utils.decode(reqDTO.getDigestData()));
        reqDTO.setSignValue(Base64Utils.decode(reqDTO.getSignValue()));
        boolean result;
        try {
            result = SmUtils.verifyBySM3withSM2(signKey.getPublicKey(), reqDTO.getSignValue(), reqDTO.getDigestData());
        } catch (Exception e) {
            throw new RuntimeException(ErrorCodeEnum.ERROR_SIGN_KEY.getMsg());
        }
        return new VerifyDigestSignRepDTO().setResult(result).setTransId(reqDTO.getAppId());

    }


}
