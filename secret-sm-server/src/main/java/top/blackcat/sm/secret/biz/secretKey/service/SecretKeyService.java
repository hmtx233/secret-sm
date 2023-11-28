package top.blackcat.sm.secret.biz.secretKey.service;

import top.blackcat.sm.secret.entity.SecretKey;

public interface SecretKeyService {
    boolean save(SecretKey signKey);

    boolean updateState(int id, String state);

    SecretKey selectByKeyId(String keyId);

    boolean selectByAppId(String appId);

}
