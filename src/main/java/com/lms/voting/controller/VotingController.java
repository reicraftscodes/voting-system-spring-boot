package com.lms.voting.controller;

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
    public ResponseEntity<VoteResponse> castVote(@Valid @RequestBody CastVoteRequest request) {
        VoteResponse vote = votingService.castVote(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    }

    @GetMapping("/receipts")
    public ResponseEntity<List<Voting>> getAllVotingReceipts() {
        List<Voting> receipts = votingService.votingReceiptDisplays();

        return ResponseEntity.ok(receipts);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalVoteCount() {
        Integer totalVotes = votingService.getTotalCountVoter();
        return ResponseEntity.ok(totalVotes);
    }

    @GetMapping("/party/{partyId}")
    public ResponseEntity<PartyVoteSummary> getVotesByParty(@PathVariable Integer partyId) {
        PartyVoteSummary summary = votingService.getTotalVotesByParty(partyId);
        return ResponseEntity.status(HttpStatus.OK).body(summary);
    }
}