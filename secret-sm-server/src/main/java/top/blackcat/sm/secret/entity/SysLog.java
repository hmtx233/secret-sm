package top.blackcat.sm.secret.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import java.io.Serializable;

/**
 * table name:  sys_log
 * author name: jelly
 * create time: 2023-11-24 11:37:20
 */
@Data
public class SysLog implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String transId;

    private String reqData;

    private String resData;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}

