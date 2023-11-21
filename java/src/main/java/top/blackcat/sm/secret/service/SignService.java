package top.blackcat.sm.secret.service;

import top.blackcat.sm.secret.dto.unique.GenerateKeyPairRepDTO;

public interface SignService {


    /***
     * 生成非对称密钥
     * @param transId
     * @return
     */

    GenerateKeyPairRepDTO generateKeyPair(String transId);


    /***
     * 摘要签名
     * @param digestData
     * @param transId
     * @return
     */
    String signDigest(String transId, String digestData);


    /***
     * 摘要验签
     * @param transId
     * @param digestData
     * @param signValue
     * @return boolean
     */

    boolean verifyDigestSign(String transId, String digestData, String signValue);


}
