package top.blackcat.sm.secret.dto.unique;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class VerifyDigestSignReqDTO implements Serializable {

    // 业务ID，传递15-64位的随机数（建议业务系统进行日志记录，方便根据id排查问题）
    @NotNull
    private String transId;
    // 接口调用方（业务系统）的ID标识
    @NotNull
    private String appId;
    // 签名算法标识，SM3withSM2
    private String signAlgIdentifier="SM3withSM2";
    // 原文摘要数据的base64编码字符串
    @NotNull
    private String digestData;
    // 待验证的Base64编码格式PKCS1签名数据字符串
    @NotNull
    private String signValue;
    // Base64编码格式的证书（证书和公钥参数二选一，证书不为空时【pubKey空或不空】，均以证书为准，否则以公钥为准）
    private String base64Cert;
    // Base64编码的公钥（base64Cert参数为空时，pubKey参数生效）
    // 公钥说明：SM2为产生公钥的64位长度的字节数组进行Base64编码所得；
    @NotNull
    private String pubKey;
    // 是否验证证书，为true时验证证书有效性，必须传入证书参数；为false时，不验证证书，证书可为空
    private String verifyCert="false";


}
