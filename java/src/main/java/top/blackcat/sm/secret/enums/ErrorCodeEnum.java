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

    ERROR_CODE_20010001("20010001",	"请求参数格式错误"),
    ERROR_CODE_85001001("85001001",	"请求参数格式错误"),
    ERROR_CODE_85002006("85002006",	"签名服务返回空证书请求"),
    ERROR_CODE_85003001("85003001",	"签名服务初始化异常"),
    ERROR_CODE_85003002("85003002",	"签名服务发生异常"),
    ERROR_CODE_85004002("85004002",	"签名服务返回空密钥"),
    ERROR_CODE_85004004("85004004",	"签名参数解析公钥失败"),
    ERROR_CODE_85005001("85005001",	"Base64编解码异常"),
    ERROR_CODE_85006001("85006001",	"签名服务返回空签名"),
    ERROR_CODE_85006002("85006002",	"签名服务签名异常"),
    ERROR_CODE_85007001("85007001",	"签名服务连接密钥管理服务通讯失败"),
    ERROR_CODE_85007002("85007002",	"签名服务从密钥管理服务获取私钥失败"),
    ERROR_CODE_85007100("85007100",	"签名服务对应算法没有找到异常"),
    ERROR_CODE_85007151("85007151",	"签名服务无效的密钥"),
    ERROR_CODE_85101001("85101001",	"请求参数错误")
    ;


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
