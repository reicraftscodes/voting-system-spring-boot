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

        // prefix for identification
        String prefix = "VLGLAM";

        // Date for reference
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // generate random UUID
        String uuidPart = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();

        // combined all and return
        return String.format("%s-%s-%s", prefix, date, uuidPart);

    }
}
