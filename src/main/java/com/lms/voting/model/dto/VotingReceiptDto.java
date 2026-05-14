package com.lms.voting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotingReceiptDto {
    private String referenceNo;
    private String partyName;
    private String voterLastName;
}
