package com.lms.voting.controller;


import com.lms.voting.dto.PartyVoteSummary;
import com.lms.voting.service.VotingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(VotingController.class)
public class VotingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotingService votingService;

    @Test
    void countAllTotalVotes() throws Exception {

        Integer totalVotes = 145;

        when(votingService.getTotalCountVoter()).thenReturn(totalVotes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/voting/count")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void countTotalVotesByParty() throws Exception {

        // Create a mock PartyVoteSummary using the builder pattern
        // Must use .builder() because PartyVoteSummary has @Builder annotation
        PartyVoteSummary partyVoteSummary = PartyVoteSummary.builder()
                .partyId(1)
                .partyName("Conservative")
                .totalVotes(15L)
                .build();

        // Mock the service method to return our test data when called with partyId = 1
        when(votingService.getTotalVotesByParty(1)).thenReturn(partyVoteSummary);

        //  Perform the HTTP request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/voting/party/1")
                        // Request JSON response
                        .accept(MediaType.APPLICATION_JSON))
                // Verify HTTP 200 status
                .andExpect(status().isOk())
                // Verify the JSON response fields match our test data
                .andExpect(jsonPath("$.partyId").value(1))
                .andExpect(jsonPath("$.partyName").value("Conservative"))
                .andExpect(jsonPath("$.totalVotes").value(15));
    }
}
