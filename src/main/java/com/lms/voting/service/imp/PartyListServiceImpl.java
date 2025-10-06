package com.lms.voting.service.imp;

import com.lms.voting.entity.PartyList;
import com.lms.voting.repository.PartyListRepository;
import com.lms.voting.service.PartyListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyListServiceImpl implements PartyListService {

    @Autowired
    private PartyListRepository partyListRepository;

    // find all party list groups
    public List<PartyList> getAllPartyMembers() {
        return partyListRepository.findAll();
    }

    //create a party list member
    public PartyList createPartyList(PartyList partyList) {
        return partyListRepository.save(partyList);
    }

}
