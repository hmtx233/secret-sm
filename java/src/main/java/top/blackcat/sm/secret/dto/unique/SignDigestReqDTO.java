package top.blackcat.sm.secret.dto.unique;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SignDigestReqDTO implements Serializable {

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
    // 密钥类型 HOSTING_KEY(托管密钥)
    private String keyStore="HOSTING_KEY";
    // 密钥ID
    @NotNull
    private String keyPairId;
    // 密钥版本
    @NotNull
    private int keyPairVersion;

}
