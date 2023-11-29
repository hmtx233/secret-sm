package top.blackcat.sm.secret.biz.sign.dto.rep;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CreateKeyPairRepDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    @NotNull
    private String transId;
    // 密钥对全局唯一标识符。
    @NotNull
    private String keyPairId;
    @NotNull
    private String appId;
}
