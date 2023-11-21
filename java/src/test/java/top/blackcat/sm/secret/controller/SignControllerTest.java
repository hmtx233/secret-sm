package top.blackcat.sm.secret.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.blackcat.sm.secret.service.SecretService;
import top.blackcat.sm.secret.service.SignService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SignControllerTest {
    @Autowired
    private SignService signService;
    String signStr;
    String encryptStr = "18701559738";


    static JSONObject data = new JSONObject();

    static {
        data.put("id", "4028805e82aebcdc0182b0012c750012");
        data.put("txt", "18701559738");
    }

    @BeforeAll
    void signDigest() {
        signStr = signService.signDigest(data.getString("id"), data.getString("txt"));
        System.out.println(signStr);
    }

    @Test
    void verifyDigestSign() {
        Assertions.assertTrue(signService.verifyDigestSign(data.getString("id"), data.getString("txt"), signStr));
    }
}