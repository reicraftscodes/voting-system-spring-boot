package com.lms.voting.api.controller;

import com.lms.voting.api.constant.VotingDetailsConstant;
import com.lms.voting.api.model.dto.CastVoteRequestDto;
import com.lms.voting.api.model.dto.PartyVoteResponse;
import com.lms.voting.api.model.dto.VoteResponseDto;
import com.lms.voting.api.service.VotingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<VoteResponseDto> castVote(@Valid @RequestBody CastVoteRequestDto request) {
        VoteResponseDto vote = votingService.castVote(request);
        log.info(VotingDetailsConstant.RESULT_VOTED_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
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