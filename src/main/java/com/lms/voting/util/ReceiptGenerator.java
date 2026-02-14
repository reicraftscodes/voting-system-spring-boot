package com.lms.voting.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ReceiptGenerator {

    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public String generateReceipt(Integer partyId) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(FORMATTER);
        return timestamp + String.format("%03d", partyId);
    }
}
