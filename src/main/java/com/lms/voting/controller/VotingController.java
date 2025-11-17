package com.lms.voting.controller;


import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.entity.Voting;
import com.lms.voting.service.PartyListService;
import com.lms.voting.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/voting")
public class VotingController {

    private final VotingService votingService;
    private final PartyListService partyListService;

    @Autowired
    public VotingController(VotingService votingService, PartyListService partyListService){
        this.votingService = votingService;
        this.partyListService = partyListService;
    }

    @PostMapping
    public ResponseEntity<String> castVote(@RequestBody CastVoteRequest castVoteRequest) {
        String result = votingService.castVote(castVoteRequest);

        if (result.equalsIgnoreCase("Vote successfully cast.")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("display-receipt")
    public ResponseEntity<List<Voting>> getAllVotesReceiptDisplays() {
        List<Voting> voting = votingService.votingReceiptDisplays();
        return new ResponseEntity<>(voting, HttpStatus.OK);
    }

    @GetMapping("count-total-vote")
    public ResponseEntity<Integer> getTotalCountVoter() {
        Integer votes = votingService.getTotalCountVoter();
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }

    // show users who voted specific party list: /api/v1/voting/total-vote?partyName=2
    @GetMapping("/total-vote")
    public ResponseEntity<Map<String, Object>> getTotalCountVoterByParty(@RequestParam(value = "partyName", required = false) Integer partyId) {
        // with value ="", required = false difference param

        // Check if partyId is provided
        if (partyId == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Missing party ID"));
        }

        // Get the party name from your database/service
        String partyName = partyListService.getPartyNameById(partyId);

        // Check if the ID exists in the database
        if (partyName == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Party not found: " + partyId));
        }

        // Call the service to get the total number of votes for this party.
        // The service queries the database using the numeric party ID.
        Map<String, Object> results = votingService.getTotalVotesByParty(partyId);

        // Replace the numeric ID in the results with the actual party name for better readability in the API response.
        results.put("partyName", partyName);


        return new ResponseEntity<>(results,HttpStatus.OK);
    }

}
