package com.lms.voting.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.dto.PartyVoteSummary;
import com.lms.voting.dto.VoteResponse;
import com.lms.voting.entity.PartyList;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.entity.Voting;
import com.lms.voting.service.VotingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(VotingController.class)
public class VotingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VotingService votingService;


    @Test
    void userCastVotes() throws Exception {
        CastVoteRequest castVoteRequest = new CastVoteRequest();
        castVoteRequest.setNationalInsuranceNumber("CS200001S");
        castVoteRequest.setLastName("San");
        castVoteRequest.setPartyId(1);

        // Create a VoteResponse object, representing the response after the vote is cast.
        VoteResponse voteResponse = VoteResponse.builder()
                .referenceNo("20260130173747817001")
                .partyName("Labour")
                .description("Vote successfully cast")
                .build();

        // Mock the behaviour of the voting service, specifying that when the castVote method
        // is called with the castVoteRequest, it should return the voteResponse object.
        when(votingService.castVote(castVoteRequest)).thenReturn(voteResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/voting")
                        // If Content-Type is not explicitly set to application/json, Spring treats the request body as application/octet-stream,
                        // which causes HttpMediaTypeNotSupportedException.
                        // Explicitly set Content-Type to application/json so Spring can deserialize the request body into CastVoteRequest.

                        // Set the Content-Type to application/json
                        .contentType(MediaType.APPLICATION_JSON)
                        // Indicate that the response should be in JSON format
                        .accept(MediaType.APPLICATION_JSON)
                        // Convert the voteResponse to JSON string
                        .content(objectMapper.writeValueAsString(voteResponse)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllVotingReceipts_EmptyList() throws Exception {

        List<Voting> votingList = new ArrayList<>();

        when(votingService.votingReceiptDisplays()).thenReturn(votingList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/voting/receipts")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Expect the response body to be an empty array.
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllVotingReceipts_withItems() throws Exception {

        UserDetails userDetails = new UserDetails();
        userDetails.setId(1);
        userDetails.setFirstName("John");
        userDetails.setLastName("Doe");
        userDetails.setNationalInsuranceNumber("SampleReferences1234");

        PartyList partyListCandidateSample = new PartyList();
        partyListCandidateSample.setId(1);
        partyListCandidateSample.setPartyName("Labour");
        partyListCandidateSample.setPosition("Left Wing");

        Voting userVotedOne = new Voting();
        userVotedOne.setId(1);
        userVotedOne.setReferenceNo("SampleReferences1234");
        userVotedOne.setUserDetails(userDetails);
        userVotedOne.setPartyList(partyListCandidateSample);


        List<Voting> votingListWithItems = List.of(
                userVotedOne);

        when(votingService.votingReceiptDisplays()).thenReturn(votingListWithItems);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/voting/receipts")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("["
                        + "{\"id\":1,"
                        + "\"referenceNo\":\"SampleReferences1234\","
                        + "\"userDetails\":{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"nationalInsuranceNumber\":\"SampleReferences1234\"},"
                        + "\"partyList\":{\"id\":1,\"partyName\":\"Labour\",\"position\":\"Left Wing\"}"
                        + "}]"));  // expect the response body to match the JSON structure
    }

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partyId").value(1))
                .andExpect(jsonPath("$.partyName").value("Conservative"))
                .andExpect(jsonPath("$.totalVotes").value(15));
    }
}
