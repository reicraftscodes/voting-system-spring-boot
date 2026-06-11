package com.lms.voting.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.voting.api.model.dto.PartyListDto;
import com.lms.voting.api.service.PartyListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

        PartyListDto partyCandidateOne = new PartyListDto();
        partyCandidateOne.setId(1);
        partyCandidateOne.setPartyName("Party A");
        partyCandidateOne.setPosition("Leftist");

        PartyListDto partyCandidateTwo = new PartyListDto();
        partyCandidateTwo.setId(2);
        partyCandidateTwo.setPartyName("Party B");
        partyCandidateTwo.setPosition("Rightist");

        List<PartyListDto> savedParty = List.of(partyCandidateOne, partyCandidateTwo);

        when(partyListService.createPartyList(any(PartyListDto.class)))
                .thenReturn((PartyListDto) savedParty);

        mockMvc.perform(get("/api/v1/uk/parties/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].partyName").value("Party A"))
                .andExpect(jsonPath("$[0].position").value("Leftist"))
                .andExpect(jsonPath("$[1].partyName").value("Party B"))
                .andExpect(jsonPath("$[1].position").value("Rightist"));
    }

    @Test
    void createPartyListTest() throws Exception {
        PartyListDto newParty = new PartyListDto();
        newParty.setId(1);
        newParty.setPartyName("Labour Party");
        newParty.setPosition("Leftist");

        PartyListDto savedParty = new PartyListDto();
        savedParty.setId(1);
        savedParty.setPartyName("Labour Party");
        savedParty.setPosition("Leftist");


        when(partyListService.createPartyList(any(PartyListDto.class)))
                .thenReturn(savedParty);

        mockMvc.perform(post("/api/v1/uk/parties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newParty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.partyName").value("Labour Party"))
                .andExpect(jsonPath("$.position").value("Leftist"));

        verify(partyListService).createPartyList(any(PartyListDto.class));
    }
}
