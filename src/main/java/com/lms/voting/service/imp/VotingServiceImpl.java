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

    public String castVote(CastVoteRequest castVoteRequest) {

        // Check if the user exists using National Insurance Number and Last Name.
        Optional<UserDetails> userDetails = userDetailsRepository.findByNationalInsuranceNumberAndLastName(
                castVoteRequest.getNationalInsuranceNumber(),
                castVoteRequest.getLastName()
        );

        // Retrieve the UserDetails object from the Optional
        UserDetails user = userDetails.get();

        // If the user's NI number or Last Name doesn't match the request
        if (!user.getNationalInsuranceNumber().equals(castVoteRequest.getNationalInsuranceNumber())
                || !user.getLastName().equals(castVoteRequest.getLastName())) {
            return "Incorrect details, try again";
        }

        // Age verification check
        if (!isEligibleToVote(user)) {
            return "User must be 18 or older to vote.";
        }

        // Check if the user has already voted
        Optional<Voting> existingVote = votingRepository.findByUserDetails(user);
        if (existingVote.isPresent()) {
            return "This user has already voted.";
        }

        // Verify that the selected party exists
        Optional<PartyList> votedPartyList = partyListRepository.findById(castVoteRequest.getPartyId());
        if (votedPartyList.isEmpty()) {
            return "Party not found.";
        }

        // Save the user's vote
        saveVote(user, votedPartyList.get(), castVoteRequest.generateRandomReceiptNumbers());

        return "Vote successfully cast.";
    }

    // Age verification to determine whether the user meets the minimum voting age requirement
    public boolean isEligibleToVote(UserDetails userDetails) {
        // Retrieve the user's date of birth
        LocalDate dob = userDetails.getDateOfBirth();

        // Calculate the user's age based on today's date
        int age = Period.between(dob, LocalDate.now()).getYears();

        // Return true only if the user is 18 or older
        return age >= 18;
    }

    // Save vote to repository
    public void saveVote(UserDetails user, PartyList partyList, String referenceNo) {
        Voting vote = new Voting();
        vote.setReferenceNo(referenceNo);
        vote.setUserDetails(user);
        vote.setPartyList(partyList);
        votingRepository.save(vote);
    }

    // Display all voting receipts
    public List<Voting> votingReceiptDisplays() {
        List<Voting> voting = votingRepository.findAll();
        if (voting.isEmpty()) {
            throw new NoVotingRecordsFoundException("No voting records found.");
        }
        return voting;
    }

    // Get the total count of voters
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
