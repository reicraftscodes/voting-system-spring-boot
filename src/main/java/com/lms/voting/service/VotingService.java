package com.lms.voting.service;

import org.springframework.stereotype.Service;

@Service
public class VotingService {

    public final UserDetailsService userDetailsService;
    public final PartyListService partyListService;


    public VotingService(UserDetailsService userDetailsService, PartyListService partyListService) {
        this.userDetailsService = userDetailsService;
        this.partyListService = partyListService;
    }

    // cast vote

    // count vote per party

    // get all vote



}
