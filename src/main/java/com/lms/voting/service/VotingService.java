package com.lms.voting.service;

import com.lms.voting.dto.CastVoteRequestDto;
import com.lms.voting.dto.PartyVoteResponse;
import com.lms.voting.dto.VoteResponseDto;
import com.lms.voting.entity.PartyList;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.entity.Voting;

import java.util.List;

public interface VotingService {

    VoteResponseDto castVote(CastVoteRequestDto castVoteRequestDto);

    Voting saveVote(UserDetails user, PartyList partyList, String referenceNo);

    boolean isEligibleToVote(UserDetails userDetails);

    void validateVotingEligibility(UserDetails user);

//    List<String> votingReceiptDisplays();

    PartyVoteResponse getTotalVotesByParty(Integer partyId);

    Long getTotalCountVoter();


}