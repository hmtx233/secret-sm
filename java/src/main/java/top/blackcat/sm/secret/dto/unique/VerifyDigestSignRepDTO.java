package top.blackcat.sm.secret.dto.unique;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class VerifyDigestSignRepDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    @NotNull
    private String transId;

    // 验签结果，正确true，错误为false
    @NotNull
    private String result;


}
