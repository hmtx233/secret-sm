package top.blackcat.sm.secret.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestDTO implements Serializable {

    private String phone;
    private String email;
    private String realName;

}
