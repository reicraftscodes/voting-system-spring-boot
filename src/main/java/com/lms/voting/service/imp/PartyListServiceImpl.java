package com.lms.voting.service.imp;

import com.lms.voting.entity.PartyList;
import com.lms.voting.repository.PartyListRepository;
import com.lms.voting.service.PartyListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartyListServiceImpl implements PartyListService {


    private final PartyListRepository partyListRepository;

    @Autowired
    public PartyListServiceImpl(PartyListRepository partyListRepository) {
        this.partyListRepository = partyListRepository;
    }

    // find all party list groups
    public List<PartyList> getAllPartyMembers() {
        return partyListRepository.findAll();
    }

    //create a party list member
    public PartyList createPartyList(PartyList partyList) {
        return partyListRepository.save(partyList);
    }

    // Retrieves the name of a party based on its ID.
    // @param partyId The ID of the party to look up.
    // @return The party's name if found, or null if no party exists with that ID.
    public String getPartyNameById(Integer partyId) {
        // Query the database (through the repository) to find a PartyList entity by its ID.
        // findById() returns an Optional, which may or may not contain a PartyList object.
        Optional<PartyList> partyOptional = partyListRepository.findById(partyId);

        // Check if a PartyList object was found.
        if (partyOptional.isPresent()) {

            // Extract the PartyList object from the Optional
            PartyList party = partyOptional.get();

            // Return the name of the party
            return party.getPartyName();

        } else {
            // If the party ID doesn't exist in the database, return null
            return null;
        }
    }


    // Alternative
    // Returns the party name for a given ID, or null if not found
//    public String getPartyNameById(Integer partyId) {
//        return partyListRepository.findById(partyId)
//                .map(PartyList::getPartyName)
//                .orElse(null);
//    }

}
