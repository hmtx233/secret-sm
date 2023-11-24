package top.blackcat.sm.secret.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.blackcat.sm.secret.dto.unique.*;
import top.blackcat.sm.secret.service.SignService;
import top.blackcat.sm.utils.Base64Utils;
import top.blackcat.sm.utils.SmUtils;

import javax.validation.constraints.NotNull;


@Slf4j
@Service
public class SignServiceImpl implements SignService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.secret.url}")
    private String POST_URL;


    @Value("${application.secret.app-id}")
    private static String APP_ID;

    /***
     * 获取签名密钥
     * @param reqDTO
     * @return
     */
    @Override
    public CreateKeyPairRepDTO createKey(CreateKeyPairReqDTO reqDTO) {
        try {
            JSONObject data = postBillUrl("/sign/createKey", JSONObject.toJSONString(reqDTO));
            CreateKeyPairRepDTO repDTO = JSONObject.parseObject(data.toString(), CreateKeyPairRepDTO.class);
            repDTO.setKeyPairId(Base64Utils.decode(repDTO.getKeyPairId()));
            return repDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /***
     * 签名
     * @param reqDTO
     * @return
     */
    @Override
    public SignDigestRepDTO signDigest(SignDigestReqDTO reqDTO) {
        try {
            // 原文摘要数据的base64编码字符串
            reqDTO.setDigestData(Base64Utils.encode(SmUtils.hashBySm3(reqDTO.getDigestData())));
            reqDTO.setKeyPairId(Base64Utils.encode(reqDTO.getKeyPairId()));
            JSONObject data = postBillUrl("/sign/signDigest", JSONObject.toJSONString(reqDTO));
            SignDigestRepDTO repDTO = JSONObject.parseObject(data.toString(), SignDigestRepDTO.class);
            repDTO.setSignValue(Base64Utils.decode(repDTO.getSignValue()));
            return repDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 验签
     *
     * @param reqDTO
     * @return
     */
    @Override
    public VerifyDigestSignRepDTO verifyDigestSign(VerifyDigestSignReqDTO reqDTO) {
        try {
            reqDTO.setKeyPairId(Base64Utils.encode(reqDTO.getKeyPairId()));
            // 原文摘要数据的base64编码字符串
            reqDTO.setDigestData(Base64Utils.encode(SmUtils.hashBySm3(reqDTO.getDigestData())));
            reqDTO.setSignValue(Base64Utils.encode(reqDTO.getSignValue()));
            JSONObject data = postBillUrl("/sign/verifyDigestSign", JSONObject.toJSONString(reqDTO));
            return JSONObject.parseObject(data.toString(), VerifyDigestSignRepDTO.class);
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
        String resDataStr = res.getBody().getString("code");
        if (!"200".equals(resDataStr)) {
            log.error("接口请求错误：{}", res);
            return null;
        }
        return res.getBody().getObject("data", JSONObject.class);
    }


}
