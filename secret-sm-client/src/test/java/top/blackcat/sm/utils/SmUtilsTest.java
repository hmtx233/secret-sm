package top.blackcat.sm.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SmUtilsTest {

    @Test
    void hashBySm3() {
        String str = "haibo";
        for (int i = 0; i < 10; i++) {
            String hash1 = SmUtils.hashBySm3(str);
            String hash2 = SmUtils.hashBySm3(str);
            Assertions.assertTrue(hash1.equals(hash2));
        }
    }
}