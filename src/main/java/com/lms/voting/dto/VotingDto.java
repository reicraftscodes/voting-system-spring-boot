package com.lms.voting.dto;

import com.lms.voting.entity.PartyList;
import com.lms.voting.entity.UserDetails;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VotingDto {
    private String referenceNo;
    private UserDetails userDetails;
    private PartyList partyList;
}
