package top.blackcat.sm.secret.dto.unique;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Accessors(chain = true)
public class CreateKeyPairReqDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    @NotNull
    private String transId;
    @NotNull
    private String appId;


}
