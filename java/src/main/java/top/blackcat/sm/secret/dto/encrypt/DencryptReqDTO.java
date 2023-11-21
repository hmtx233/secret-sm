package top.blackcat.sm.secret.dto.encrypt;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DencryptReqDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    @NotNull
    private String transId;
    // 接口调用方（业务系统）的ID标识，需要管理员先在密管系统中创建，与运管应用ID保持一致。
    @NotNull
    private String appId;
    // CMK全局唯一标识符。
    @NotNull
    private String keyId;
    // 待解密的密文。Base64编码 密文数据为ASN1结构
    @NotNull
    private String encData;



}
