package com.lms.voting.controller;


import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/voting")
public class VotingController {

    @Autowired
    private VotingService votingService;

    @PostMapping
    public ResponseEntity<String> castVote(@RequestBody CastVoteRequest castVoteRequest) {
        String result = votingService.castVote(castVoteRequest);

        if (result.equalsIgnoreCase("Vote successfully cast.")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
