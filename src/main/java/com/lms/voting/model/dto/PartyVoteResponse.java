package com.lms.voting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyVoteResponse {
    private Integer partyId;
    private String partyName;
    private Long totalVotes;
}