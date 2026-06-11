package com.lms.voting.api.service.imp;

import com.lms.voting.api.model.dto.PartyListDto;
import com.lms.voting.api.model.entity.PartyList;
import com.lms.voting.api.exception.ResourceNotFoundException;
import com.lms.voting.api.repository.PartyListRepository;
import com.lms.voting.api.service.PartyListService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.lms.voting.api.constant.PartyListConstant.PARTY_DESCRIPTION_NOT_FOUND;
import static com.lms.voting.api.constant.PartyListConstant.PARTY_ID_DESCRIPTION_SUCCESS;

@Slf4j
@Service
public class PartyListServiceImpl implements PartyListService {

    @Autowired
    private PartyListRepository partyListRepository;

    @Autowired
    public PartyListServiceImpl(PartyListRepository partyListRepository) {
        this.partyListRepository = partyListRepository;
    }

    // Todo redo this
    @Override
    public List<String> getAllPartyMembers() {
        List<String> partyListsNames = partyListRepository.findAllPartyNames();

        if (partyListsNames.isEmpty()) {
            throw new ResourceNotFoundException(PARTY_DESCRIPTION_NOT_FOUND);
        }

        return partyListsNames;
    }


    @Override
    public PartyListDto createPartyList(PartyListDto partyListDto) {

        if (partyListRepository.existsByPartyName(partyListDto.getPartyName())) {
            throw new DuplicateRequestException(PARTY_ID_DESCRIPTION_SUCCESS);
        }
        // Convert DTO to Entity
        PartyList partyList = new PartyList();
        partyList.setPartyName(partyListDto.getPartyName());
        partyList.setPosition(partyListDto.getPosition());

        // save the entity
        PartyList savedPartyList = partyListRepository.save(partyList);

        // map the saved entity to DTO before returning
        PartyListDto savedPartyListDto = new PartyListDto();
        savedPartyListDto.setId(savedPartyList.getId());
        savedPartyListDto.setPartyName(savedPartyList.getPartyName());
        savedPartyListDto.setPosition(savedPartyList.getPosition());

        return savedPartyListDto;
    }

}