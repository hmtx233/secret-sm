package top.blackcat.sm.secret.dto.encrypt;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DencryptRepDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    @NotNull
    private String transId;
    // CMK全局唯一标识符。
    private String keyId;
    // CMK版本
    @NotNull
    private String keyVersion;
    // 待解密的密文。Base64编码 密文数据为ASN1结构
    @NotNull
    private String plainText;



}
