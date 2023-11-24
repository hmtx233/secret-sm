package top.blackcat.sm.secret.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.blackcat.sm.secret.entity.SecretKey;
import top.blackcat.sm.secret.entity.SysLog;

@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

}
