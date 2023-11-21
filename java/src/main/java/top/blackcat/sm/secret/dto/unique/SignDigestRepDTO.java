package top.blackcat.sm.secret.dto.unique;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SignDigestRepDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    @NotNull
    private String transId;

    // PKCS1格式签名数据的Base64编码字符串
    @NotNull
    private String signValue;


}
