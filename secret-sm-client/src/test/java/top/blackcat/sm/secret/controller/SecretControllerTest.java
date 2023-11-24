package top.blackcat.sm.secret.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${application.secret.app-id}")
    private String appId;

    @Value("${application.secret.key}")
    private String key;

    String text = "海波";


    @Test
    void createKey() {
        CreateKeyRepDTO repDTO = secretService.createKey(new CreateKeyReqDTO().setAppId(appId).setTransId(transId));
        if (repDTO != null) {
            System.out.println(repDTO.getKey());
            return;
        }
        Assertions.assertTrue(false);
    }


    @Test
    void secret() {
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