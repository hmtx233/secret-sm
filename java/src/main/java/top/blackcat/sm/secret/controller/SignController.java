package top.blackcat.sm.secret.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.blackcat.sm.secret.result.Result;
import top.blackcat.sm.secret.service.SecretService;
import top.blackcat.sm.secret.service.SignService;

/**
 * @Title: 写注释，下班早
 * @Desc: 密改 接口
 * @Author: Jelly
 * @Date: 2023/10/25
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/sign")
public class SignController {

    @Autowired
    private SignService signService;


    @RequestMapping(value = "/signDigest", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> signDigest(@RequestBody JSONObject data) {
        return Result.success(signService.signDigest(data.getString("id"), data.getString("txt")));
    }


    @RequestMapping(value = "/verifyDigestSign", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> verifyDigestSign(@RequestBody JSONObject data) {
        return Result.success(signService.verifyDigestSign(data.getString("id"), data.getString("txt"), data.getString("signValue")));
    }


}
