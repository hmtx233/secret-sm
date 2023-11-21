package top.blackcat.sm.secret.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.blackcat.sm.secret.annotation.SecretEncrypt;
import top.blackcat.sm.secret.result.Result;

import java.util.HashMap;

/**
 * @Title: 写注释，下班早
 * @Desc: 密改 接口
 * @Author: Jelly
 * @Date: 2023/10/25
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {


    /***
     * 加解密 测试 map
     * @param realName
     * @param phone
     * @return
     */
    @SecretEncrypt(type = 0, optField = {"realName", "phone"})
    @RequestMapping(value = "/testMap", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> testMap(String realName, String phone) {
        HashMap data = new HashMap<>();
        data.put("phone", phone);
        data.put("realName", realName);
        return Result.success(data);
    }



}
