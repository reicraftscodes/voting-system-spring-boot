package com.lms.voting.service;

import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.dto.VoteResponse;
import com.lms.voting.dto.PartyVoteSummary;
import com.lms.voting.entity.PartyList;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.entity.Voting;

import java.util.List;

public interface VotingService {

    VoteResponse castVote(CastVoteRequest castVoteRequest);

    Voting saveVote(UserDetails user, PartyList partyList, String referenceNo);

    boolean isEligibleToVote(UserDetails userDetails);

    List<Voting> votingReceiptDisplays();

    PartyVoteSummary getTotalVotesByParty(Integer partyId);

    Integer getTotalCountVoter();

}