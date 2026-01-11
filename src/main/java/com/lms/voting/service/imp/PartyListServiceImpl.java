package com.lms.voting.service.imp;

import com.lms.voting.entity.PartyList;
import com.lms.voting.exception.ResourceNotFoundException;
import com.lms.voting.repository.PartyListRepository;
import com.lms.voting.service.PartyListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PartyListServiceImpl implements PartyListService {

    private final PartyListRepository partyListRepository;

    @Autowired
    public PartyListServiceImpl(PartyListRepository partyListRepository) {
        this.partyListRepository = partyListRepository;
    }

    @Override
    public List<PartyList> getAllPartyMembers() {
        List<PartyList> parties = partyListRepository.findAll();
        if (parties.isEmpty()) {
            throw new ResourceNotFoundException("No parties found in the system.");
        }
        return parties;
    }

    @Override
    @Transactional
    public PartyList createPartyList(PartyList partyList) {
        return partyListRepository.save(partyList);
    }

    @Override
    public String getPartyNameById(Integer partyId) {
        Optional<PartyList> partyOptional = partyListRepository.findById(partyId);

        if (partyOptional.isPresent()) {
            PartyList party = partyOptional.get();
            return party.getPartyName();
        } else {
            throw new ResourceNotFoundException("Party not found with ID: " + partyId);
        }
    }

    @Override
    public PartyList getPartyById(Integer partyId) {
        Optional<PartyList> partyOptional = partyListRepository.findById(partyId);

        if (partyOptional.isPresent()) {
            return partyOptional.get();
        } else {
            throw new ResourceNotFoundException("Party not found with ID: " + partyId);
        }
    }
}