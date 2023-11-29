package top.blackcat.sm.secret.biz.sign.dto.req;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SignDigestReqDTO implements Serializable {

    // 业务ID，传递15-64位的随机数（建议业务系统进行日志记录，方便根据id排查问题）
    @NotNull
    private String transId;
    // 接口调用方（业务系统）的ID标识
    @NotNull
    private String appId;
    // 原文摘要数据的base64编码字符串
    @NotNull
    private String digestData;
    // 密钥ID
    @NotNull
    private String keyPairId;

}
