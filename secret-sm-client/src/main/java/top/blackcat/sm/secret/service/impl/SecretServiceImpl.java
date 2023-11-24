package top.blackcat.sm.secret.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.blackcat.sm.secret.dto.encrypt.*;
import top.blackcat.sm.secret.service.SecretService;
import top.blackcat.sm.utils.Base64Utils;

import javax.validation.constraints.NotNull;


@Slf4j
@Service
public class SecretServiceImpl implements SecretService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.secret.url}")
    private String POST_URL;

    /****
     * @param reqDTO
     * @return
     */
    @Override
    public CreateKeyRepDTO createKey(CreateKeyReqDTO reqDTO) {
        try {
            JSONObject data = postBillUrl("/secret/createKey", JSONObject.toJSONString(reqDTO));
            CreateKeyRepDTO repDTO = JSONObject.parseObject(data.toString(), CreateKeyRepDTO.class);
            repDTO.setKey(Base64Utils.decode(repDTO.getKey()));
            return repDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /***
     * @param reqDTO
     * @return
     */
    @Override
    public EncryptRepDTO encrypt(EncryptReqDTO reqDTO) {
        try {
            reqDTO.setPlainText(Base64Utils.encode(reqDTO.getPlainText()));
            reqDTO.setKey(Base64Utils.encode(reqDTO.getKey()));
            JSONObject data = postBillUrl("/secret/encrypt", JSONObject.toJSONString(reqDTO));
            EncryptRepDTO repDTO = JSONObject.parseObject(data.toString(), EncryptRepDTO.class);
            repDTO.setCipherText(Base64Utils.decode(repDTO.getCipherText()));
            return repDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 解密
     * @param reqDTO
     * @return
     */
    @Override
    public DecryptRepDTO decrypt(DecryptReqDTO reqDTO) {
        try {
            reqDTO.setDecryptText(Base64Utils.encode(reqDTO.getDecryptText()));
            reqDTO.setKey(Base64Utils.encode(reqDTO.getKey()));
            JSONObject data = postBillUrl("/secret/decrypt", JSONObject.toJSONString(reqDTO));
            DecryptRepDTO repDTO = JSONObject.parseObject(data.toString(), DecryptRepDTO.class);
            repDTO.setPlainText(Base64Utils.decode(repDTO.getPlainText()));
            return repDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /****
     * 请求
     * @param url
     * @param jsonString
     * @return
     */
    private JSONObject postBillUrl(@NotNull String url, @NotNull String jsonString) {
        log.info("请求url：" + url);
        log.info("业务参数：" + jsonString);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<JSONObject> res = restTemplate.postForEntity(POST_URL + url, request, JSONObject.class);
        log.info("返回结果：{}", res.getBody());
        int code = res.getBody().getIntValue("code");
        if (HttpStatus.OK.value() != code) {
            log.error("接口请求错误：{}", res);
            return null;
        }
        return res.getBody().getObject("data", JSONObject.class);
    }

}
