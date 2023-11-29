package top.blackcat.sm.secret.biz.secret.dto.rep;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DecryptRepDTO implements Serializable {

    // 方法调用时传入的requestId，作为结果原样返回
    @NotNull
    private String requestId;
    // 解密完的base64Str。
    @NotNull
    private String plainText;


}
