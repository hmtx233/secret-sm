package top.blackcat.sm.secret.dto.unique;

import lombok.Data;

import java.io.Serializable;

@Data
public class GenerateKeyPairRepDTO implements Serializable {

    // 方法调用时传入的transId，作为结果原样返回
    private String transId;
    // 密钥对全局唯一标识符。
    private String keyPairId;
    // 密钥对版本。
    private Integer keyPairVersion;
    // Base64编码格式的公钥字符串
    //公钥说明：
    // RSA为公钥PublicKey对象的二进制数据进行Base64编码所得；
    // SM2为产生公钥的64位长度的字节数组进行Base64编码所得；
    private String pubKey;



}
