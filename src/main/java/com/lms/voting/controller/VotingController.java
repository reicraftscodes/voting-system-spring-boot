package com.lms.voting.controller;

import com.lms.voting.constant.VotingDetailsConstant;
import com.lms.voting.dto.CastVoteRequestDto;
import com.lms.voting.dto.PartyVoteResponse;
import com.lms.voting.dto.VoteResponse;
import com.lms.voting.entity.Voting;
import com.lms.voting.service.VotingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/voting")
public class VotingController {

    private final VotingService votingService;

    @Autowired
    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @PostMapping
    public ResponseEntity<VoteResponse> castVote(@Valid @RequestBody CastVoteRequestDto request) {
        VoteResponse vote = votingService.castVote(request);
        log.info(VotingDetailsConstant.RESULT_VOTED_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    }

    @GetMapping("/receipts")
    public ResponseEntity<List<Voting>> getAllVotingReceipts() {
        List<Voting> receipts = votingService.votingReceiptDisplays();
        return ResponseEntity.status(HttpStatus.OK).body(receipts);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalVoteCount() {
        Long totalVotes = votingService.getTotalCountVoter();
        log.info(VotingDetailsConstant.TOTAL_COUNT_RETRIEVED_SUCCESS, totalVotes);
        return ResponseEntity.status(HttpStatus.OK).body(totalVotes);
    }

    @GetMapping("/party/{partyId}")
    public ResponseEntity<PartyVoteResponse> getVotesByParty(@PathVariable Integer partyId) {
        PartyVoteResponse summary = votingService.getTotalVotesByParty(partyId);
        log.info(VotingDetailsConstant.PARTY_TOTAL_VOTE_SUCCESS, summary.getPartyId(), summary.getTotalVotes());
        return ResponseEntity.status(HttpStatus.OK).body(summary);
    }
}