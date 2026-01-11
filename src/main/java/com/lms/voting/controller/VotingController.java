package com.lms.voting.controller;

import com.lms.voting.dto.ApiResponse;
import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.dto.VoteResponse;
import com.lms.voting.dto.PartyVoteSummary;
import com.lms.voting.entity.Voting;
import com.lms.voting.service.VotingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voting")
public class VotingController {

    private final VotingService votingService;

    @Autowired
    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> castVote(@Valid @RequestBody CastVoteRequest request) {
        VoteResponse response = votingService.castVote(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Vote Cast Successfully",
                        "New vote has been recorded",
                        response,
                        HttpStatus.CREATED.value()
                ));
    }

    @GetMapping("/receipts")
    public ResponseEntity<ApiResponse> getAllVotingReceipts() {
        List<Voting> receipts = votingService.votingReceiptDisplays();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Receipts Retrieved",
                        "All voting receipts have been retrieved",
                        receipts,
                        HttpStatus.OK.value()
                )
        );
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getTotalVoteCount() {
        Integer totalVotes = votingService.getTotalCountVoter();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Vote Count Retrieved",
                        "Total number of votes has been calculated successfully",
                        totalVotes,
                        HttpStatus.OK.value()
                )
        );
    }

    @GetMapping("/party/{partyId}")
    public ResponseEntity<ApiResponse> getVotesByParty(@PathVariable Integer partyId) {
        PartyVoteSummary summary = votingService.getTotalVotesByParty(partyId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Party Votes Retrieved",
                        "Vote summary for the selected party has been retrieved successfully",
                        summary,
                        HttpStatus.OK.value()
                )
        );
    }
}