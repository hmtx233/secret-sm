package top.blackcat.sm.secret.biz.signKey.service;

import top.blackcat.sm.secret.entity.SignKey;

public interface SignKeyService {


    boolean save(SignKey signKey);

    boolean updateState(int id, String state);

    SignKey selectByKeyPairId(String keyPairId);
    boolean selectByAppId(String appId);

}
