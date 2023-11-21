package top.blackcat.sm.secret.dto.encrypt;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateKeyRepDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    private String transId;
    // 创建时间
    private String creationDate;
    // 描述
    private String description;
    // 密钥标识
    private String keyId;
    // 密钥版本
    private int keyVersion;
    // 密钥状态
    private String keyState;
    // 密钥用处
    private String keyUsage;
    // 密钥空间
    private String keySpec;
    // 删除日期
    private String deleteDate;
    // 应用id
    private String appId;
    // 来源
    private String origin;
    // 过期时间
    private String materialExpireTime;

}
