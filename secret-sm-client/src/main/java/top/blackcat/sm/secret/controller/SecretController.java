package top.blackcat.sm.secret.controller;


import top.blackcat.sm.secret.dto.encrypt.CreateKeyReqDTO;
import top.blackcat.sm.secret.dto.encrypt.DecryptReqDTO;
import top.blackcat.sm.secret.dto.encrypt.EncryptReqDTO;
import top.blackcat.sm.secret.result.Result;
import top.blackcat.sm.secret.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: 写注释，下班早
 * @Desc: 加解密接口
 * @Author: Jelly
 * @Date: 2023/10/25
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/secret")
public class SecretController {

    @Autowired
    private SecretService secretService;


    @RequestMapping(value = "/createKey", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> createKey(@RequestBody CreateKeyReqDTO reqDTO) {
        return Result.success(secretService.createKey(reqDTO));
    }

    @RequestMapping(value = "/decrypt", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> decrypt(@RequestBody DecryptReqDTO reqDTO) {
        return Result.success(secretService.decrypt(reqDTO));
    }


    @RequestMapping(value = "/encrypt", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> encrypt(@RequestBody EncryptReqDTO reqDTO) {
        return Result.success(secretService.encrypt(reqDTO));
    }


}
