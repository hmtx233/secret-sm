package top.blackcat.sm.secret.controller;


import top.blackcat.sm.secret.result.Result;
import top.blackcat.sm.secret.service.SecretService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: 写注释，下班早
 * @Desc: 密改 接口
 * @Author: Jelly
 * @Date: 2023/10/25
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/secret")
public class SecretController {

    @Autowired
    private SecretService secretService;


    @RequestMapping(value = "/decrypt", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> decrypt(@RequestBody JSONObject data) {
        return Result.success(secretService.decrypt(data.getString("id"), data.getString("txt")));
    }


    @RequestMapping(value = "/encrypt", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> encrypt(@RequestBody JSONObject data) {
        return Result.success(secretService.encrypt(data.getString("id"), data.getString("txt")));
    }


}
