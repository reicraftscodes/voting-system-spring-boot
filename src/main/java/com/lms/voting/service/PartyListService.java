package com.lms.voting.service;

import com.lms.voting.dto.PartyList;
import com.lms.voting.repository.PartyListRepository;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PartyListService {

    private final PartyListRepository partyListRepository;

    public PartyListService(PartyListRepository partyListRepository) {
        this.partyListRepository = partyListRepository;
    }

    // find all party list groups
    public List<PartyList> getAllPartyMembers(){
        return partyListRepository.findAll();
    }

    //add partylist
    public Optional<PartyList> createPartyList(PartyList partyList){
        return partyListRepository.save(partyList);
    }
}
