package top.blackcat.sm.secret.biz.secretKey.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.blackcat.sm.secret.biz.client.service.ClientAppService;
import top.blackcat.sm.secret.biz.secret.service.SecretService;

/**
 * @Title: 写注释，下班早
 * @Desc: 加解密接口
 * @Author: Jelly
 * @Date: 2023/10/25
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/secretKey")
public class SecretKeyController {

    @Autowired
    private SecretService secretService;

    @Autowired
    private ClientAppService clientAppService;


}
