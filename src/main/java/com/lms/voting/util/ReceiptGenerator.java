package com.lms.voting.util;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class ReceiptGenerator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public static String generateReceipt(Integer partyId) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(FORMATTER);
        return timestamp + String.format("%03d", partyId);
    }
}
