package top.blackcat.sm.secret.dto.encrypt;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EncryptReqDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    @NotNull
    private String transId;
    // 接口调用方（业务系统）的ID标识，需要管理员先在密管系统中创建，与运管应用ID保持一致。
    @NotNull
    private String appId;
    // CMK全局唯一标识符。
    @NotNull
    private String keyId;
    // CMK版本
    @NotNull
    private int keyVersion;
    // 加解密模式，有效值CBC。
    private String mode="CBC";
    // IV值，mode为CBC时有效。
    // 注意：如果mode为CBC，iv值填写16位的base64码。
    // 这里写的是：16 个1
    private String iv="MTExMTExMTExMTExMTExMQ==";
    // 填充模式，有效值PKCS7PADDING
    private String padding="PKCS7PADDING";
    // 待加密明文（必须经过 Base64 编码）。
    @NotNull
    private String plainText;
    // 加密算法类型（SM4）
    private String symAlgId="SM4";


}
