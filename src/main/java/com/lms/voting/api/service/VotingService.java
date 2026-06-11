package com.lms.voting.api.service;

import com.lms.voting.api.model.dto.CastVoteRequestDto;
import com.lms.voting.api.model.dto.PartyVoteResponse;
import com.lms.voting.api.model.dto.VoteResponseDto;
import com.lms.voting.api.model.entity.AccountInfo;
import com.lms.voting.api.model.entity.PartyList;
import com.lms.voting.api.model.entity.Voting;

public interface VotingService {

    VoteResponseDto castVote(CastVoteRequestDto castVoteRequestDto);

    Voting saveVote(AccountInfo user, PartyList partyList, String referenceNo);

    boolean isEligibleToVote(AccountInfo accountInfo);

    void validateVotingEligibility(AccountInfo user);

//    List<String> votingReceiptDisplays();

    PartyVoteResponse getTotalVotesByParty(Integer partyId);

    Long getTotalCountVoter();


}