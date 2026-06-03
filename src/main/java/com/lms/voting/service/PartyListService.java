package com.lms.voting.service;

import com.lms.voting.model.dto.PartyListDto;

import java.util.List;

public interface PartyListService {

    List<String> getAllPartyMembers();

    PartyListDto createPartyList(PartyListDto partyListDto);

}
