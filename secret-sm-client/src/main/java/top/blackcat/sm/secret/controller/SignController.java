package top.blackcat.sm.secret.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.blackcat.sm.secret.dto.unique.CreateKeyPairReqDTO;
import top.blackcat.sm.secret.dto.unique.SignDigestReqDTO;
import top.blackcat.sm.secret.dto.unique.VerifyDigestSignReqDTO;
import top.blackcat.sm.secret.result.Result;
import top.blackcat.sm.secret.service.SignService;

/**
 * @Title: 写注释，下班早
 * @Desc: 签名接口
 * @Author: Jelly
 * @Date: 2023/10/25
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/sign")
public class SignController {

    @Autowired
    private SignService signService;

    @RequestMapping(value = "/createKey", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> createKey(@RequestBody CreateKeyPairReqDTO reqDTO) {
        return Result.success(signService.createKey(reqDTO));
    }


    @RequestMapping(value = "/sign", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> sign(@RequestBody SignDigestReqDTO repDTO) {
        return Result.success(signService.signDigest(repDTO));
    }


    @RequestMapping(value = "/verify", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> verify(@RequestBody VerifyDigestSignReqDTO repDTO) {
        return Result.success(signService.verifyDigestSign(repDTO));
    }


}
