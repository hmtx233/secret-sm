package top.blackcat.sm;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        String encodedString = "MF0CAQEwJQIBATAKBggqgRzPVQFoAgoBAQMRADExMTExMTExMTExMTExMTEDMQDENKQoSQvYxRv4aH6vIJdQmahdvmMhsttqB4MqkKn13jgdq3FfMEQN/wJgxTk0/5w=";

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);

        System.out.println(decodedString);
    }
}