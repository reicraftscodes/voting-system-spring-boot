package com.lms.voting.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.voting.model.dto.CastVoteRequestDto;
import com.lms.voting.model.dto.PartyVoteResponse;
import com.lms.voting.model.dto.VoteResponseDto;
import com.lms.voting.model.entity.AccountInfo;
import com.lms.voting.model.entity.PartyList;
import com.lms.voting.model.entity.Voting;
import com.lms.voting.service.VotingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
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
        CastVoteRequestDto castVoteRequestDto = new CastVoteRequestDto();
        castVoteRequestDto.setNationalInsuranceNumber("CS200001S");
        castVoteRequestDto.setLastName("San");
        castVoteRequestDto.setPartyId(1);

        // Create a VoteResponse object, representing the response after the vote is cast.
        VoteResponseDto voteResponseDto = VoteResponseDto.builder()
                .referenceNo("20260130173747817001")
                .partyName("Labour")
                .description("Vote successfully cast")
                .build();

        // Mock the behaviour of the voting service, specifying that when the castVote method
        // is called with the castVoteRequest, it should return the voteResponse object.
        when(votingService.castVote(castVoteRequestDto)).thenReturn(voteResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/voting")
                        // If Content-Type is not explicitly set to application/json, Spring treats the request body as application/octet-stream,
                        // which causes HttpMediaTypeNotSupportedException.
                        // Explicitly set Content-Type to application/json so Spring can deserialize the request body into CastVoteRequest.

                        //  Set the Content-Type of the request to application/json
                        .contentType(MediaType.APPLICATION_JSON)
                        // Indicate that the response should be in JSON format or  expected response type
                        .accept(MediaType.APPLICATION_JSON)  // The response should also be JSON
                        //Serialize the castVoteRequest object to a JSON string as the body of the POST request
                        .content(objectMapper.writeValueAsString(castVoteRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.referenceNo").value("20260130173747817001"))
                .andExpect(jsonPath("$.partyName").value("Labour"))
                .andExpect(jsonPath("$.description").value("Vote successfully cast"));


        verify(votingService).castVote(castVoteRequestDto);
    }

//    @Test
//    void getAllVotingReceipts_EmptyList() throws Exception {
//
//        List<Voting> votingList = new ArrayList<>();
//
//        when(votingService.votingReceiptDisplays()).thenReturn(votingList);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/voting/receipts")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                // Expect the response body to be an empty array
//                .andExpect(content().json("[]"));
//    }

//    @Test
//    void getAllVotingReceipts_withItems() throws Exception {
//
//        Voting userVotedOne = getUserVotedOne();
//
//        List<Voting> votingListWithItems = List.of(
//                userVotedOne);
//
//        when(votingService.votingReceiptDisplays()).thenReturn(votingListWithItems);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/voting/receipts")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                // hasSize() matcher to verify array size
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].referenceNo").value("SampleReferences1234"))
//                .andExpect(jsonPath("$[0].userDetails.firstName").value("John"))
//                .andExpect(jsonPath("$[0].userDetails.lastName").value("Doe"))
//                .andExpect(jsonPath("$[0].partyList.partyName").value("Labour"))
//                .andExpect(jsonPath("$[0].partyList.position").value("Left Wing"));
//    }

    private static Voting getUserVotedOne() {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setId(1);
        accountInfo.setFirstName("John");
        accountInfo.setLastName("Doe");
        accountInfo.setNationalInsuranceNumber("SampleReferences1234");

        PartyList partyListCandidateSample = new PartyList();
        partyListCandidateSample.setId(1);
        partyListCandidateSample.setPartyName("Labour");
        partyListCandidateSample.setPosition("Left Wing");

        Voting userVotedOne = new Voting();
        userVotedOne.setId(1);
        userVotedOne.setReferenceNo("SampleReferences1234");
        userVotedOne.setAccountInfo(accountInfo);
        userVotedOne.setPartyList(partyListCandidateSample);
        return userVotedOne;
    }

    @Test
    void whenUserCountAllTotalVotes() throws Exception {

        Long totalVotes = 145L;

        when(votingService.getTotalCountVoter()).thenReturn(totalVotes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/voting/count")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("145"));
    }

    @Test
    void whenUserCountTotalVotesByParty() throws Exception {
        // Must use .builder() because PartyVoteSummary has @Builder annotation
        PartyVoteResponse partyVoteSummary = PartyVoteResponse.builder()
                .partyId(1)
                .partyName("Conservative")
                .totalVotes(15L)
                .build();

        when(votingService.getTotalVotesByParty(1)).thenReturn(partyVoteSummary);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/voting/party/1")
                        // Request JSON response
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partyId").value(1))
                .andExpect(jsonPath("$.partyName").value("Conservative"))
                .andExpect(jsonPath("$.totalVotes").value(15));
    }
}
