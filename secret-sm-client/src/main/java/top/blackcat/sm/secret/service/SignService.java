package top.blackcat.sm.secret.service;

import top.blackcat.sm.secret.dto.unique.*;

public interface SignService {


    /***
     * 生成非对称密钥
     * @param createKeyPairReqDTO
     * @return
     */

    CreateKeyPairRepDTO createKey(CreateKeyPairReqDTO createKeyPairReqDTO);

    /***
     * 摘要签名
     * @param signDigestReqDTO
     * @return
     */
    SignDigestRepDTO  signDigest(SignDigestReqDTO signDigestReqDTO);


    /***
     * 摘要验签
     * @param verifyDigestSignRepDTO
     * @return VerifyDigestSignRepDTO
     */

    VerifyDigestSignRepDTO verifyDigestSign(VerifyDigestSignReqDTO verifyDigestSignRepDTO);


}
