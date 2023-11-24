package top.blackcat.sm.secret;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String phone;
    private String realName;
}
