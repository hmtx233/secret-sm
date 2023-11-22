package top.blackcat.sm;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author : ZHL
 * @Date: 2023-11-17 16:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ModelBase implements Serializable {

    private String id;//批次
    private String batch;//主键
    private String operatingUnits;//运营单位名称
    private String phone;//手机号
    private String region;//地区
    private String serviceAreas;//地域标签
    private String username;//用户名
    /**是否加密**/
    private String IfSecret;
}
