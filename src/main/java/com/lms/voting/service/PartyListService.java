package com.lms.voting.service;

import com.lms.voting.entity.PartyList;

import java.util.List;

public interface PartyListService {


    List<PartyList> getAllPartyMembers();

    PartyList createPartyList(PartyList partyList);

    String getPartyNameById(Integer partyId);
}
