package top.blackcat.sm.secret.service;

import top.blackcat.sm.secret.entity.SecretKey;
import top.blackcat.sm.secret.entity.SignKey;

public interface SecretKeyService {
    boolean save(SecretKey signKey);

    boolean updateState(int id, String state);

    SecretKey selectByKeyId(String keyId);

    boolean selectByAppId(String appId);

}
