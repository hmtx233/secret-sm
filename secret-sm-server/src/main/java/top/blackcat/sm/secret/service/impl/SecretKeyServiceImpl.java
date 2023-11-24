package top.blackcat.sm.secret.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.blackcat.sm.secret.entity.SecretKey;
import top.blackcat.sm.secret.entity.SignKey;
import top.blackcat.sm.secret.mapper.SecretKeyMapper;
import top.blackcat.sm.secret.service.SecretKeyService;


@Slf4j
@Service
public class SecretKeyServiceImpl implements SecretKeyService {

    @Autowired
    private SecretKeyMapper secretKeyMapper;

    @Override
    public boolean save(SecretKey secretKey) {
        return secretKeyMapper.insert(secretKey) > 0;
    }

    @Override
    public boolean updateState(int id, String state) {
        SecretKey signKey = secretKeyMapper.selectById(id);
        signKey.setKeyState(state);
        return secretKeyMapper.updateById(signKey) > 0;
    }

    @Override
    public SecretKey selectByKeyId(String keyId) {
        QueryWrapper queryWrapper = new QueryWrapper<SignKey>();
        queryWrapper.eq("secret_key", keyId);
        return secretKeyMapper.selectOne(queryWrapper);
    }


    @Override
    public boolean selectByAppId(String appId) {
        QueryWrapper queryWrapper = new QueryWrapper<SecretKey>();
        queryWrapper.eq("app_id", appId);
        return secretKeyMapper.selectCount(queryWrapper) > 0L;
    }

}
