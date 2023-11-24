package top.blackcat.sm.secret.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import java.io.Serializable;
/**
 * table name:  sign_key
 * author name: jelly
 * create time: 2023-11-24 12:27:32
 */ 
@Data
public class SignKey implements Serializable {

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	private String appId;
	
	private String keyPairId;
	
	private String keyState;
	
	private String publicKey;
	
	private String privateKey;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

}

