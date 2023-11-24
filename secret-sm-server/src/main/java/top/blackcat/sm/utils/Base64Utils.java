package top.blackcat.sm.utils;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

/***
 * sm算法
 */
public class Base64Utils {


    public static String encode(String text) {
        byte[] encodedBytes = Base64.getEncoder().encode(text.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    public static String decode(String encodedText) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText.getBytes(StandardCharsets.UTF_8));
        return new String(decodedBytes, StandardCharsets.UTF_8);

    }


}
