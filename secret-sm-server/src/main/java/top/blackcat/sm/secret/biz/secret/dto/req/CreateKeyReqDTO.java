package top.blackcat.sm.secret.biz.secret.dto.req;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CreateKeyReqDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    @NotNull
    private String transId;
    // 接口调用方（业务系统）的ID标识，需要管理员先在密管系统中创建，与运管应用ID保持一致。
    @NotNull
    private String appId;

}
