package com.lms.voting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CastVoteRequest {
    private Integer userId;
    private Integer partyId;
    private String referenceNo;
}
