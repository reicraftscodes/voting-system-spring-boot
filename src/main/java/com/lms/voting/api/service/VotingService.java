package com.lms.voting.api.service;

import com.lms.voting.api.model.dto.*;
import com.lms.voting.api.model.entity.AccountInfo;
import com.lms.voting.api.model.entity.PartyList;
import com.lms.voting.api.model.entity.Voting;

public interface VotingService {

    VoteResponseDto castVote(CastVoteRequestDto castVoteRequestDto);

    VoterVerificationResponseDto verifyVoter(VerifyVoterRequestDto verifyVoterRequestDto);

    Voting saveVote(AccountInfo user, PartyList partyList, String referenceNo);

    boolean isEligibleToVote(AccountInfo accountInfo);

    void validateVotingEligibility(AccountInfo user);

    PartyVoteResponse getTotalVotesByParty(Integer partyId);

    Long getTotalCountVoter();


}