package com.lms.voting.controller;


import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.entity.PartyList;
import com.lms.voting.entity.Voting;
import com.lms.voting.service.imp.VotingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/voting")
public class VotingController {

    private final VotingServiceImpl votingServiceImpl;

    @Autowired
    public VotingController(VotingServiceImpl votingService){
        this.votingServiceImpl = votingService;
    }

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

    // show users who voted specific party list: /api/v1/voting/total-vote?partyName=2
    @GetMapping("/total-vote")
    public ResponseEntity<Map<String, Object>> getTotalCountVoterByParty(@RequestParam(value = "partyName", required = false) Integer partyId) {
        // with value ="", required = false difference param

        // Create a simple in-memory mapping between party IDs and their readable names (hardcoded)
        Map<Integer, String> partyMap = new HashMap<>();
        partyMap.put(1, "Reform UK");
        partyMap.put(2, "Labour");
        partyMap.put(53, "Sample");

        // Get the party name that matches the given party ID.
        String partyName = partyMap.get(partyId);

        // Check if the party ID is missing or not found in the list.
        if (partyId == null || !partyMap.containsKey(partyId)) {
            // If it's missing or wrong, send back an error message.
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or missing party ID"));
        }

        // Call the service to get the total number of votes for this party.
        // The service queries the database using the numeric party ID.
        Map<String, Object> results = votingServiceImpl.getTotalVotesByParty(partyId);

        // Replace the numeric ID in the results with the actual party name for better readability in the API response.
        results.put("partyName", partyName);


        return new ResponseEntity<>(results,HttpStatus.OK);
    }

}
