package com.lms.voting.controller;


import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.entity.Voting;
import com.lms.voting.service.imp.VotingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/voting")
public class VotingController {

    @Autowired
    private VotingServiceImpl votingServiceImpl;

    @PostMapping
    public ResponseEntity<String> castVote(@RequestBody CastVoteRequest castVoteRequest) {
        String result = votingServiceImpl.castVote(castVoteRequest);

        if (result.equalsIgnoreCase("Vote successfully cast.")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("display-receipt")
    public ResponseEntity<List<Voting>> getAllVotesReceiptDisplays() {
        List<Voting> voting = votingServiceImpl.votingReceiptDisplays();
        return new ResponseEntity<>(voting, HttpStatus.OK);
    }

    @GetMapping("count-total-vote")
    public ResponseEntity<Integer> getTotalCountVoter() {
        Integer votes = votingServiceImpl.getTotalCountVoter();
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }

    // show users who voted specific party list - total-vote?partyName=Labour"
    @GetMapping("/total-vote")
    public ResponseEntity<Map<String, Object>> getTotalCountVoterByParty(
            @RequestParam("partyName") String partyName) {

        if (partyName == null || partyName.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error" , "partyName is required"));
        }

        Map<String, Object> results = votingServiceImpl.getTotalVotesByParty(partyName);
        return new ResponseEntity (results,HttpStatus.OK);
    }

}
