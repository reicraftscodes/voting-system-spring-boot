package com.lms.voting.service.imp;

import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.entity.PartyList;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.entity.Voting;
import com.lms.voting.exception.NoVotingRecordsFoundException;
import com.lms.voting.repository.PartyListRepository;
import com.lms.voting.repository.UserDetailsRepository;
import com.lms.voting.repository.VotingRepository;
import com.lms.voting.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VotingServiceImpl implements VotingService {

    private final PartyListRepository partyListRepository;

    private final UserDetailsRepository userDetailsRepository;

    private final VotingRepository votingRepository;

    @Autowired
    public VotingServiceImpl(PartyListRepository partyListRepository, UserDetailsRepository userDetailsRepository, VotingRepository votingRepository) {
        this.partyListRepository = partyListRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.votingRepository = votingRepository;
    }

    // cast vote
    public String castVote(CastVoteRequest castVoteRequest) {

        // Check if the user exists using the user ID from the CastVoteRequest DTO.
        Optional<UserDetails> userDetails = userDetailsRepository.findById(castVoteRequest.getUserId());
        if (userDetails.isEmpty()) {
            return "User not found.";
        }

        // Retrieve the UserDetails object from the Optional
        UserDetails user = userDetails.get();

        // age verification check
        if (isEligibleToVote(user)) {
            return "User must be 18 or older to vote.";
        }
        // Check whether the current user has already submitted a vote. If a Voting record exists for this user, prevent duplicate voting.
        Optional<Voting> existingVote = votingRepository.findByUserDetails(user);
        if (existingVote.isPresent()) {
            return "This user has already voted.";
        }

        // Verify that the selected party exists.
        Optional<PartyList> votedPartyList = partyListRepository.findById(castVoteRequest.getPartyId());
        if (votedPartyList.isEmpty()) {
            return "Party not found.";
        }

        //  Save the user's vote, including the selected party and a newly generated receipt number.
        saveVote(user, votedPartyList.get(), castVoteRequest.generateRandomReceiptNumbers());

        return "Vote successfully cast.";
    }

    // / Age verification to determine whether the user meets the minimum voting age requirement
    public boolean isEligibleToVote(UserDetails userDetails){
        // Retrieve the user's date of birth
        LocalDate dob = userDetails.getDateOfBirth();

        // Calculate the user's age based on today's date
        int age = Period.between(dob, LocalDate.now()).getYears();

        // Return true only if the user is older than 18
        return age <= 18;
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

    public Integer getTotalCountVoter() {
        return votingRepository.getTotalCountVoter();
    }

    @Override
    public Map<String, Object> getTotalVotesByParty(Integer partyName) {
        Long totalVotes = votingRepository.getAllTotalVotersVoteNumberByParty(partyName);

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("partyName", partyName);

        if (totalVotes != null) {
            response.put("totalVotes", totalVotes);
        } else {
            response.put("totalVotes", 0L);
        }

        return response;
    }

}
