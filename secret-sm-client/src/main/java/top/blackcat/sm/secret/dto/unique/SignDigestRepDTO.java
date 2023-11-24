package top.blackcat.sm.secret.dto.unique;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SignDigestRepDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    @NotNull
    private String transId;

    //签名数据的Base64编码字符串
    @NotNull
    private String signValue;


}
