package com.lms.voting.service;

import com.lms.voting.dto.PartyListDto;

import java.util.List;

public interface PartyListService {

    List<String> getAllPartyMembers();

//    PartyListDto createPartyList(PartyList partyList);

    PartyListDto createPartyList(PartyListDto partyListDto);


    //    String getPartyNameById(Integer partyId);
//
//    PartyListDto getPartyById(Integer partyId);
}
