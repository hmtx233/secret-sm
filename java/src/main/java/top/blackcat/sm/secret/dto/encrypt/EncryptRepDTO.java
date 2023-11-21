package top.blackcat.sm.secret.dto.encrypt;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EncryptRepDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    @NotNull
    private String transId;
    // CMK全局唯一标识符。
    @NotNull
    private String keyId;
    // CMK版本
    private String keyVersion;
    // 加密过的密文。
    @NotNull
    private String cipherTextBlob;
    // IV值，mode为CBC时有效。
    //注意：如果mode为CBC，iv值填写16位的base64码。
    @NotNull
    private String iv;


}
