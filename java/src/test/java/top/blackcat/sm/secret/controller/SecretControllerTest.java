package top.blackcat.sm.secret.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.blackcat.sm.secret.service.SecretService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SecretControllerTest {
    @Autowired
    private SecretService secretService;

    String decryptStr;
    String encryptStr = "18701559738";

    @Test
    void decrypt() {
        JSONObject data = new JSONObject();
        data.put("id", "4028805e82aebcdc0182b0012c750012");
        data.put("txt", decryptStr);
        Assertions.assertTrue(encryptStr.equals(secretService.decrypt(data.getString("id"), data.getString("txt"))));
    }

    @BeforeAll
    void encrypt() {
        JSONObject data = new JSONObject();
        data.put("id", "4028805e82aebcdc0182b0012c750012");
        data.put("txt", "18701559738");
        decryptStr = secretService.encrypt(data.getString("id"), data.getString("txt"));
        System.out.println(secretService.encrypt(data.getString("id"), data.getString("txt")));
    }

    @Test
    void signDigest() {
    }

    @Test
    void verifyDigestSign() {
    }
}