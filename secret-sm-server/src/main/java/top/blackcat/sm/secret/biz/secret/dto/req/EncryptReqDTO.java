package top.blackcat.sm.secret.biz.secret.dto.req;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class EncryptReqDTO implements Serializable {

    @NotNull
    private String transId;
    // 接口调用方（业务系统）的ID标识，需要管理员先在密管系统中创建，与运管应用ID保持一致。
    @NotNull
    private String appId;
    // 密钥key 64 Str。
    @NotNull
    private String key;
    @NotNull
    private String plainText;


}
