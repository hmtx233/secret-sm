package top.blackcat.sm.secret.biz.secret.dto.rep;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CreateKeyRepDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    private String transId;
    // 密钥标识
    private String key;
    // 密钥状态 0 1 2  待审核 1 有效 2 无效
    private String keyState;
    // 应用id
    private String appId;


}
