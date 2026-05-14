package com.lms.voting.model.dto;

import com.lms.voting.model.entity.AccountInfo;
import com.lms.voting.model.entity.PartyList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VotingDto {
    private String referenceNo;
    private AccountInfo accountInfo;
    private PartyList partyList;
}
