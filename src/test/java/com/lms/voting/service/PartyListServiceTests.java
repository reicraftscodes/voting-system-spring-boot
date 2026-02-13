package com.lms.voting.service;


import com.lms.voting.entity.PartyList;
import com.lms.voting.exception.ResourceNotFoundException;
import com.lms.voting.repository.PartyListRepository;
import com.lms.voting.service.imp.PartyListServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PartyListServiceTests {

    @Mock
    private PartyListRepository partyListRepository;

    @InjectMocks
    private PartyListServiceImpl partyListService;

    private List<PartyList> partyLists;

    PartyList partyCandidateOne;
    PartyList partyCandidateTwo;

    @BeforeEach
    void setUp() {
        // Create test data once, reused in all tests
        partyCandidateOne = new PartyList();
        partyCandidateOne.setId(1);
        partyCandidateOne.setPartyName("Labour");
        partyCandidateOne.setPosition("Left Wing");

        partyCandidateTwo = new PartyList();
        partyCandidateTwo.setId(2);
        partyCandidateTwo.setPartyName("Conservative");
        partyCandidateTwo.setPosition("Right Wing");

        partyLists = new ArrayList<>();
        partyLists.add(partyCandidateOne);
        partyLists.add(partyCandidateTwo);
    }

    @Test
    void whenGetAllPartyMembersThenReturnList() {
        when(partyListRepository.findAll()).thenReturn(partyLists);
        List<PartyList> result = partyListService.getAllPartyMembers();
        assertEquals(2, result.size());
        assertEquals("Labour", result.get(0).getPartyName());
        assertEquals("Conservative", result.get(1).getPartyName());

        verify(partyListRepository, times(1)).findAll();
    }

    @Test
    void whenGetAllPartyMembersWithEmptyListThenThrowException() {
        when(partyListRepository.findAll()).thenReturn(new ArrayList<>());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            partyListService.getAllPartyMembers();
        });

        verify(partyListRepository, times(1)).findAll();
    }

    @Test
    void whenGetAllPartyMembersThenVerifyCorrectPositions() {
        when(partyListRepository.findAll()).thenReturn(partyLists);

        List<PartyList> result = partyListService.getAllPartyMembers();

        assertEquals("Left Wing", result.get(0).getPosition());
        assertEquals("Right Wing", result.get(1).getPosition());
    }

    @Test
    void createPartyListThenSave() {

        // mock the repository's save method to return the partyCandidateOne object
        when(partyListRepository.save(partyCandidateOne)).thenReturn(partyCandidateOne);

        // call the service method which will interact with the repository
        PartyList savedParty = partyListService.createPartyList(partyCandidateOne);

        assertEquals(1, savedParty.getId());
        assertEquals("Labour", savedParty.getPartyName());
        assertEquals("Left Wing", savedParty.getPosition());
    }

}
