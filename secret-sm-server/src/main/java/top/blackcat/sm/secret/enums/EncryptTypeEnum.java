package top.blackcat.sm.secret.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @Title: 写注释，下班早
 * @Desc: 密评接口请求错误信息
 * @Author: Jelly
 * @Date: 2023/10/25
 * @Version V1.0
 */
@Getter
@AllArgsConstructor
public enum EncryptTypeEnum {

    BOTH("0",	"加解密"),
    ENCRYPT("1",	"加密"),
    DECRYPT("2",	"解密"),
    ;


    private String code;
    private String msg;

    /***
     * 获取 msg
     * @param code
     * @return
     */
    public static String getThisMsg(String code) {
        EncryptTypeEnum[] enums = EncryptTypeEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getCode().equals(code)) {
                return enums[i].getMsg();
            }
        }
        return "";
    }




}
