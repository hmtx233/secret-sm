package top.blackcat.sm.secret.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class KeyPairVO implements Serializable {
    private String privateKey;
    private String publicKey;

}
