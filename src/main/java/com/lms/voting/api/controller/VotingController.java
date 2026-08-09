package com.lms.voting.api.controller;

import com.lms.voting.api.constant.VotingDetailsConstant;
import com.lms.voting.api.model.dto.*;
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
@CrossOrigin(origins = "http://localhost:5173/")
public class VotingController {

    @Autowired
    private VotingService votingService;

    @PostMapping("/castVote")
    public ResponseEntity<VoteResponseDto> castVote(@Valid @RequestBody CastVoteRequestDto request) {
        VoteResponseDto vote = votingService.castVote(request);
        log.info(VotingDetailsConstant.RESULT_VOTED_SUCCESS, request.getNationalInsuranceNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    }

    @PostMapping("/verify")
    public ResponseEntity<VoterVerificationResponseDto> verifyVoter(@Valid @RequestBody VerifyVoterRequestDto request) {
        VoterVerificationResponseDto verification = votingService.verifyVoter(request);
        log.info("Voter verified successfully: {}", request.getNationalInsuranceNumber());
        return ResponseEntity.status(HttpStatus.OK).body(verification);
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