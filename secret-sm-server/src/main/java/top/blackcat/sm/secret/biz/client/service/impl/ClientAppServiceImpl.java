package top.blackcat.sm.secret.biz.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.blackcat.sm.secret.biz.client.service.ClientAppService;
import top.blackcat.sm.secret.entity.ClientApp;
import top.blackcat.sm.secret.biz.client.dao.ClientAppMapper;


@Slf4j
@Service
public class ClientAppServiceImpl implements ClientAppService {

    @Autowired
    private ClientAppMapper clientAppMapper;

    @Override
    public boolean selectByAppId(String appId) {
        QueryWrapper queryWrapper = new QueryWrapper<ClientApp>();
        queryWrapper.eq("app_id", appId);
        return clientAppMapper.selectCount(queryWrapper) > 0L;
    }

}
