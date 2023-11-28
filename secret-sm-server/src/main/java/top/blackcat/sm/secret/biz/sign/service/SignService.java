package top.blackcat.sm.secret.biz.sign.service;


import top.blackcat.sm.secret.biz.sign.dto.rep.CreateKeyPairRepDTO;
import top.blackcat.sm.secret.biz.sign.dto.rep.SignDigestRepDTO;
import top.blackcat.sm.secret.biz.sign.dto.rep.VerifyDigestSignRepDTO;
import top.blackcat.sm.secret.biz.sign.dto.req.CreateKeyPairReqDTO;
import top.blackcat.sm.secret.biz.sign.dto.req.SignDigestReqDTO;
import top.blackcat.sm.secret.biz.sign.dto.req.VerifyDigestSignReqDTO;

public interface SignService {


    /***
     * 生成非对称密钥
     * @param createKeyPairReqDTO
     * @return
     */

    CreateKeyPairRepDTO createKey(CreateKeyPairReqDTO createKeyPairReqDTO) throws Exception;

    /***
     * 摘要签名
     * @param signDigestReqDTO
     * @return
     */
    SignDigestRepDTO signDigest(SignDigestReqDTO signDigestReqDTO) throws Exception;


    /***
     * 摘要验签
     * @param verifyDigestSignRepDTO
     * @return VerifyDigestSignRepDTO
     */

    VerifyDigestSignRepDTO verifyDigestSign(VerifyDigestSignReqDTO verifyDigestSignRepDTO) throws Exception;


}
