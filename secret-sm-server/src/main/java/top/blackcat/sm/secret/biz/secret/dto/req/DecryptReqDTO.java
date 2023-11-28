package top.blackcat.sm.secret.biz.secret.dto.req;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DecryptReqDTO implements Serializable {

    // 方法调用时传入的requestId，作为结果原样返回
    @NotNull
    private String transId;
    // 接口调用方（业务系统）的ID标识。
    @NotNull
    private String appId;
    // 密钥 64位str。
    @NotNull
    private String key;
    // 待密的密文。
    @NotNull
    private String decryptText;


}
