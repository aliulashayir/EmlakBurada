package com.patika.util;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

public class OrderCodeGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateOrderCode() {
        String timestamp = Long.toString(Instant.now().toEpochMilli());
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 10);

        String combinedString = timestamp + uuid;
        String orderCode = combinedString.substring(6, 16); // Ensure the length is 10 characters

        return formatOrderCode(orderCode);
    }

    private static String formatOrderCode(String code) {
        return String.format("%s-%s-%s",
                code.substring(0, 4),
                code.substring(4, 8),
                code.substring(8, 10));
    }
}
