package com.lms.voting.service;

import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.entity.PartyList;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.entity.Voting;

import java.util.List;

public interface VotingService {

    String castVote(CastVoteRequest castVoteRequest);

    void saveVote(UserDetails user, PartyList partyList, String referenceNo);

    List<Voting> votingReceiptDisplays();

    Integer getTotalCountVoter();
}
