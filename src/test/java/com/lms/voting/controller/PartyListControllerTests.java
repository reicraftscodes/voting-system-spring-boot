package com.lms.voting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.voting.entity.PartyList;
import com.lms.voting.service.PartyListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(PartyListController.class)
class PartyListControllerTests {

    /**
     * Simulates HTTP requests to controller endpoints without starting the server
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Converts Java objects to JSON (Serialization) and JSON to Java objects (Deserialization) for requests/responses
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Mocks PartyListService to avoid calling real service or database
     */
    @MockBean
    private PartyListService partyListService;
    @Test
    void retrieveAllPartyMembersTest() throws Exception {
        PartyList partyCandidateOne = new PartyList();
        partyCandidateOne.setId(1);
        partyCandidateOne.setPartyName("Party A");
        partyCandidateOne.setPosition("Leftist");

        PartyList partyCandidateTwo = new PartyList();
        partyCandidateTwo.setId(2);
        partyCandidateTwo.setPartyName("Party B");
        partyCandidateTwo.setPosition("Rightist");

        List<PartyList> partyLists = Arrays.asList(partyCandidateOne, partyCandidateTwo);

        when(partyListService.getAllPartyMembers()).thenReturn(partyLists);

        mockMvc.perform(get("/api/v1/uk/parties/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].partyName").value("Party A"))
                .andExpect(jsonPath("$[1].position").value("Rightist"));
    }

    @Test
    void createPartyListTest() throws Exception {
        PartyList newParty = new PartyList();
        newParty.setId(1);
        newParty.setPartyName("Labour Party");
        newParty.setPosition("Leftist");

        PartyList savedParty = new PartyList();
        savedParty.setId(1);
        savedParty.setPartyName("Labour Party");
        savedParty.setPosition("Leftist");

        when(partyListService.createPartyList(any(PartyList.class))).thenReturn(savedParty);

        mockMvc.perform(post("/api/v1/uk/parties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newParty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.partyName").value("Labour Party"))
                .andExpect(jsonPath("$.position").value("Leftist"));
    }
}
