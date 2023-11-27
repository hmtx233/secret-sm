package top.blackcat.sm.secret.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.blackcat.sm.secret.annotation.RecordClientRequest;
import top.blackcat.sm.secret.dto.unique.CreateKeyPairReqDTO;
import top.blackcat.sm.secret.dto.unique.SignDigestReqDTO;
import top.blackcat.sm.secret.dto.unique.VerifyDigestSignReqDTO;
import top.blackcat.sm.secret.enums.ErrorCodeEnum;
import top.blackcat.sm.secret.result.Result;
import top.blackcat.sm.secret.service.ClientAppService;
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

    @Autowired
    private ClientAppService clientAppService;


    @RecordClientRequest
    @RequestMapping(value = "/createKey", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> createKey(@RequestBody CreateKeyPairReqDTO reqDTO) throws Exception {
        if (!clientAppService.selectByAppId(reqDTO.getAppId())) {
            throw new Exception(ErrorCodeEnum.ERROR_APP_ID.getMsg());
        }
        return Result.success(signService.createKey(reqDTO));
    }

    @RecordClientRequest
    @RequestMapping(value = "/signDigest", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> sign(@RequestBody SignDigestReqDTO reqDTO) throws Exception {
        if (!clientAppService.selectByAppId(reqDTO.getAppId())) {
            throw new Exception(ErrorCodeEnum.ERROR_APP_ID.getMsg());
        }
        return Result.success(signService.signDigest(reqDTO));
    }

    @RecordClientRequest
    @RequestMapping(value = "/verifyDigestSign", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> verify(@RequestBody VerifyDigestSignReqDTO repDTO) throws Exception {
        if (!clientAppService.selectByAppId(repDTO.getAppId())) {
            throw new Exception(ErrorCodeEnum.ERROR_APP_ID.getMsg());
        }
        return Result.success(signService.verifyDigestSign(repDTO));
    }


}
