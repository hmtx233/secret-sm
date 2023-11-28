package top.blackcat.sm.secret.biz.client.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.blackcat.sm.secret.biz.client.service.ClientAppService;
import top.blackcat.sm.secret.result.Result;

/**
 * @Title: 写注释，下班早
 * @Desc: 加解密接口
 * @Author: Jelly
 * @Date: 2023/10/25
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/client")
public class ClientAppController {

    @Autowired
    private ClientAppService clientAppService;

    @RequestMapping(value = "/decrypt/{appId}", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> selectByAppId(@PathVariable String appId) throws Exception {
        return Result.success(clientAppService.selectByAppId(appId));
    }








}
