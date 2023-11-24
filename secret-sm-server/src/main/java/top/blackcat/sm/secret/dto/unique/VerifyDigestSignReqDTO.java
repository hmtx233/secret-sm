package top.blackcat.sm.secret.dto.unique;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class VerifyDigestSignReqDTO implements Serializable {

    // 业务ID，传递15-64位的随机数（建议业务系统进行日志记录，方便根据id排查问题）
    @NotNull
    private String transId;
    // 接口调用方（业务系统）的ID标识
    @NotNull
    private String appId;
    // 原文摘要数据的base64编码字符串
    @NotNull
    private String digestData;
    // 待验证的Base64编码格式 签名数据字符串
    @NotNull
    private String signValue;
    // 公钥说明：SM2为产生公钥的64位长度的字节数组进行Base64编码所得；
    @NotNull
    private String keyPairId;


}
