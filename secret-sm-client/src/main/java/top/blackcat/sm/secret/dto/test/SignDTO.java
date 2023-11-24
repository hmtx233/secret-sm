package top.blackcat.sm.secret.dto.test;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignDTO implements Serializable {

    /****
     * 主键
     */
    private String id;
    /***
     * 日期
     */
    private String date;


}
