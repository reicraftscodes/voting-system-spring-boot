package com.lms.voting.service;

import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.dto.PartyList;
import com.lms.voting.dto.UserDetails;
import com.lms.voting.dto.Voting;
import com.lms.voting.repository.PartyListRepository;
import com.lms.voting.repository.UserDetailsRepository;
import com.lms.voting.repository.VotingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class VotingService {

    private final PartyListRepository partyListRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final VotingRepository votingRepository;
    // cast vote

    public String castVote(CastVoteRequest castVoteRequest) {

        // Check if the user exists using the user ID from the CastVoteRequest DTO.
        // The CastVoteRequest was created to carry the user ID during the voting process.
        Optional<UserDetails> userDetails = userDetailsRepository.findById(castVoteRequest.getUserId());
        if (userDetails.isEmpty()) {
            return "User not found.";
        }

        UserDetails user = userDetails.get();

        // Check if user already voted
        Optional<Voting> existingVote = votingRepository.findByUserDetails(user);
        if (existingVote.isPresent()) {
            return "This user has already voted.";
        }

        // Check if party exists
        Optional<PartyList> votedPartyList = partyListRepository.findById(castVoteRequest.getPartyId());
        if (votedPartyList.isEmpty()) {
            return "Party not found.";
        }

        // save
        saveVote(user, votedPartyList.get(), castVoteRequest.getReferenceNo());

        return "Vote successfully cast.";
    }


    public void saveVote(UserDetails user, PartyList partyList, String referenceNo) {
        Voting vote = new Voting();
        vote.setReferenceNo(referenceNo);
        vote.setUserDetails(user);
        vote.setPartyList(partyList);
        votingRepository.save(vote);
    }
}
