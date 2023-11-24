package top.blackcat.sm.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Title: 写注释，下班早
 * @Desc: Base64工具类
 * @Author: Jelly
 * @Date: 2023/11/24
 * @Version V1.0
 */
@Component
public class Base64Utils {


    /***
     * 编码
     * @param text
     * @return
     */
    public static String encode(String text) {
        byte[] encodedBytes = Base64.getEncoder().encode(text.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    /***
     * 解码
     * @param encodedText
     * @return
     */
    public static String decode(String encodedText) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText.getBytes(StandardCharsets.UTF_8));
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }


}
