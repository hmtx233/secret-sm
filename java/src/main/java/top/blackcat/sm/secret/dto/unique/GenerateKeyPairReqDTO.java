package top.blackcat.sm.secret.dto.unique;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Accessors(chain = true)
public class GenerateKeyPairReqDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    private String transId;
    // 密钥存储方式，支持事件型EVENT_KEY和托管型HOSTING_KEY
    private String keyStore="HOSTING_KEY";
    // 密钥算法支持RSA和SM2。
    private String keyType="SM2";
    // 密钥长度，keyType为RSA时，密钥长度仅支持1024和2048；KeyType为SM2时，密钥长度仅支持256
    private int keyLength=256;
    // 接口调用方（业务系统）的ID标识，需要管理员先在密管系统中创建，与运管应用ID保持一致。
    @NotNull
    private String appId;


}
