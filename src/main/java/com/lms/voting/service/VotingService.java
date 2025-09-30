package com.lms.voting.service;

import com.lms.voting.dto.Voting;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingService {

    public UserDetailsService userDetailsService;
    public PartyListService partyListService;


    public VotingService(UserDetailsService userDetailsService, PartyListService partyListService) {
        this.userDetailsService = userDetailsService;
        this.partyListService = partyListService;
    }


}
