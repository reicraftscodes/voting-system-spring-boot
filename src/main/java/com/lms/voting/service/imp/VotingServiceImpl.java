package com.lms.voting.service.imp;

import com.lms.voting.exception.NoVotingRecordsFoundException;
import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.entity.PartyList;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.entity.Voting;
import com.lms.voting.repository.PartyListRepository;
import com.lms.voting.repository.UserDetailsRepository;
import com.lms.voting.repository.VotingRepository;
import com.lms.voting.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotingServiceImpl implements VotingService {

    @Autowired
    private PartyListRepository partyListRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private VotingRepository votingRepository;

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

        // save method
        saveVote(user, votedPartyList.get(), castVoteRequest.generateRandomReceiptNumbers());

        return "Vote successfully cast.";
    }


    // save vote triggers here and save to repository
    public void saveVote(UserDetails user, PartyList partyList, String referenceNo) {
        Voting vote = new Voting();
        vote.setReferenceNo(referenceNo);
        vote.setUserDetails(user);
        vote.setPartyList(partyList);
        votingRepository.save(vote);
    }


    public List<Voting> votingReceiptDisplays() {
        List<Voting> voting = votingRepository.findAll();
        if (voting.isEmpty()) {
            throw new NoVotingRecordsFoundException("No record is found {}");
        }
        return voting;
    }

}
