package com.lms.voting.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CastVoteRequest {
    private Integer userId;
    private Integer partyId;
    private String referenceNo;


    public String generateRandomReceiptNumbers() {

        String prefix = "VLGLAM";
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String uuidPart = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();

        return String.format("%s-%s-%s", prefix, date, uuidPart);

    }
}
