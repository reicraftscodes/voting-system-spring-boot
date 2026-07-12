package com.lms.voting.api.service;

import com.lms.voting.api.model.dto.PartyListDto;

import java.util.List;

public interface PartyListService {

    List<PartyListDto> getAllPartyMembers();

    PartyListDto createPartyList(PartyListDto partyListDto);

}
