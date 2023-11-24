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
public enum ErrorCodeEnum {

    ERROR_APP_ID("4000", "错误的appId"),
    ERROR_PARAMS("4001", "请求参数格式错误"),
    ERROR_PARAMS_VALUE("4002", "请求参数错误"),
    ERROR_SECRET_KEY("4003", "加解密服务无效的密钥"),
    ERROR_SIGN_KEY("4004", "签名服务无效的密钥"),
    ERROR_BASE64("4005", "Base64编解码异常");


    private String code;
    private String msg;

    /***
     * 获取 msg
     * @param code
     * @return
     */
    public static String getThisMsg(String code) {
        ErrorCodeEnum[] enums = ErrorCodeEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getCode().equals(code)) {
                return enums[i].getMsg();
            }
        }
        return "";
    }


}
