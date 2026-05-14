package com.lms.voting.service;

import com.lms.voting.model.dto.CastVoteRequestDto;
import com.lms.voting.model.dto.PartyVoteResponse;
import com.lms.voting.model.dto.VoteResponseDto;
import com.lms.voting.model.entity.AccountInfo;
import com.lms.voting.model.entity.PartyList;
import com.lms.voting.model.entity.Voting;

public interface VotingService {

    VoteResponseDto castVote(CastVoteRequestDto castVoteRequestDto);

    Voting saveVote(AccountInfo user, PartyList partyList, String referenceNo);

    boolean isEligibleToVote(AccountInfo accountInfo);

    void validateVotingEligibility(AccountInfo user);

//    List<String> votingReceiptDisplays();

    PartyVoteResponse getTotalVotesByParty(Integer partyId);

    Long getTotalCountVoter();


}