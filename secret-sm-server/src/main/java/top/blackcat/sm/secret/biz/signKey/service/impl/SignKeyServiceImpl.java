package top.blackcat.sm.secret.biz.signKey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.blackcat.sm.secret.biz.signKey.service.SignKeyService;
import top.blackcat.sm.secret.entity.SignKey;
import top.blackcat.sm.secret.biz.signKey.dao.SignKeyMapper;


@Slf4j
@Service
public class SignKeyServiceImpl implements SignKeyService {

    @Autowired
    private SignKeyMapper signKeyMapper;

    @Override
    public boolean save(SignKey signKey) {
        return signKeyMapper.insert(signKey) > 0;
    }

    @Override
    public boolean updateState(int id, String state) {
        SignKey signKey = signKeyMapper.selectById(id);
        signKey.setKeyState(state);
        return signKeyMapper.updateById(signKey) > 0;
    }

    @Override
    public SignKey selectByKeyPairId(String keyPairId) {
        QueryWrapper queryWrapper = new QueryWrapper<SignKey>();
        queryWrapper.eq("key_pair_id", keyPairId);
        return signKeyMapper.selectOne(queryWrapper);
    }


    @Override
    public boolean selectByAppId(String appId) {
        QueryWrapper queryWrapper = new QueryWrapper<SignKey>();
        queryWrapper.eq("app_id", appId);
        return signKeyMapper.selectCount(queryWrapper) > 0;
    }


}
