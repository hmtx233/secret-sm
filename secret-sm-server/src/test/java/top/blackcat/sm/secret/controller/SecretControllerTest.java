package top.blackcat.sm.secret.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.blackcat.sm.secret.dto.encrypt.*;
import top.blackcat.sm.secret.service.SecretService;

import java.util.UUID;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SecretControllerTest {

    @Autowired
    private SecretService secretService;

    static String transId = UUID.randomUUID().toString().replace("-", "");

    String key = "c046071f0c03ff4ef85d8f24acdd96b6dd85d1048f5916ef731714f66b01fe13";
    String text = "海波";
    String appId = "24uvzf3x8N";

    @Test
    void createKey() throws Exception {
        CreateKeyRepDTO repDTO = secretService.createKey(new CreateKeyReqDTO().setAppId(appId).setTransId(transId));
        if (repDTO != null) {
            System.out.println(repDTO.getKey());
            return;
        }
        Assertions.assertTrue(false);
    }


    @Test
    void secret() throws Exception {
        EncryptRepDTO encryptRepDTO = secretService.encrypt(new EncryptReqDTO().setKey(key).setPlainText(text).setTransId(transId).setAppId(appId));
        if (encryptRepDTO != null) {
            String cipherText = encryptRepDTO.getCipherText();
            DecryptRepDTO decryptRepDTO = secretService.decrypt(new DecryptReqDTO().setKey(key).setDecryptText(cipherText).setTransId(transId).setAppId(appId));
            if (decryptRepDTO != null) {
                String plainText = decryptRepDTO.getPlainText();
                Assertions.assertTrue(plainText.equals(text));
                return;
            }
        }
        Assertions.assertTrue(false);
    }

}