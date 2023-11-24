package top.blackcat.sm.secret.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.blackcat.sm.secret.dto.unique.*;
import top.blackcat.sm.secret.service.SignService;

import java.util.UUID;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SignControllerTest {

    @Autowired
    private SignService signService;
    static String transId = UUID.randomUUID().toString().replace("-", "");

    String text = "hello world!!!!!";
    String appId = "24uvzf3x8N";
    String keyPairId = "5699f43d2b584dcd85f4080204b6fab1";


    @Test
    void createKey() throws Exception {
        CreateKeyPairRepDTO createKeyPairRepDTO = signService.createKey(new CreateKeyPairReqDTO().setAppId(appId).setTransId(transId));
        if (createKeyPairRepDTO != null) {
            String keyPairId = createKeyPairRepDTO.getKeyPairId();
            System.out.println(keyPairId);
        }
        Assertions.assertTrue(false);
    }

    @Test
    void signDigest() throws Exception {
        SignDigestRepDTO signDigestRepDTO = signService.signDigest(new SignDigestReqDTO().setTransId(transId).setAppId(appId).setDigestData(text).setKeyPairId(keyPairId));
        if (signDigestRepDTO != null) {
            String signValue = signDigestRepDTO.getSignValue();
            VerifyDigestSignRepDTO verifyDigestSignRepDTO = signService.verifyDigestSign(new VerifyDigestSignReqDTO().setAppId(appId).setTransId(transId).setKeyPairId(keyPairId).setDigestData(text).setSignValue(signValue));
            if (verifyDigestSignRepDTO != null) {
                Assertions.assertTrue(verifyDigestSignRepDTO.isResult());
                return;
            }
        }
        Assertions.assertTrue(false);
    }

}