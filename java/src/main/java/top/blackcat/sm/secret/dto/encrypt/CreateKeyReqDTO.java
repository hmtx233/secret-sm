package top.blackcat.sm.secret.dto.encrypt;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CreateKeyReqDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    @NotNull
    private String transId;
    // 接口调用方（业务系统）的ID标识，需要管理员先在密管系统中创建，与运管应用ID保持一致。
    @NotNull
    private String appId;
    // 密钥材料来源: KMS(内部生产)
    private String origin;
    // 密钥用途 ENCRYPT/DECRYPT
    @NotNull
    private String keyUsage;
    // 密钥算法和长度: AES_128,SM4_128,
    private String keySpec="SM4_128";
    // 操作人姓名
    private String operateName;
    // 密钥别名
    private String alias;
    // 是否自动轮转: ENABLED 或 DISABLED, 默认为关闭
    private String autoRotation;
    // 轮转周期(天), 默认365天
    private Integer rotationDays;

}
