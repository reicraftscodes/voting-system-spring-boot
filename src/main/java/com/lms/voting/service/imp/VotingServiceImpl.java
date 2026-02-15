package com.lms.voting.service.imp;

import com.lms.voting.dto.CastVoteRequest;
import com.lms.voting.dto.PartyVoteSummary;
import com.lms.voting.dto.VoteResponse;
import com.lms.voting.entity.PartyList;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.entity.Voting;
import com.lms.voting.exception.DuplicateResourceException;
import com.lms.voting.exception.IneligibleVoterException;
import com.lms.voting.exception.InvalidRequestException;
import com.lms.voting.exception.ResourceNotFoundException;
import com.lms.voting.repository.PartyListRepository;
import com.lms.voting.repository.UserDetailsRepository;
import com.lms.voting.repository.VotingRepository;
import com.lms.voting.service.VotingService;
import com.lms.voting.util.ReceiptGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class VotingServiceImpl implements VotingService {

    private static final int MINIMUM_VOTING_AGE = 18;

    private final PartyListRepository partyListRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final VotingRepository votingRepository;

    @Autowired
    public VotingServiceImpl(PartyListRepository partyListRepository,
                             UserDetailsRepository userDetailsRepository,
                             VotingRepository votingRepository) {
        this.partyListRepository = partyListRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.votingRepository = votingRepository;
    }

    @Override
    @Transactional
    public VoteResponse castVote(CastVoteRequest request) {

        // Find and validate user based on NI and Last Name
        Optional<UserDetails> userOptional = userDetailsRepository.findByNationalInsuranceNumberAndLastName(
                request.getNationalInsuranceNumber(),
                request.getLastName()
        );

        if (userOptional.isEmpty()) {
            throw new InvalidRequestException(
                    "Invalid credentials. Please check your National Insurance Number and Last Name."
            );
        }

        // Continue processing with the found user
        UserDetails userFound = userOptional.get();
        validateVotingEligibility(userFound);
        checkExistingVote(userFound);

        // Validate party exists
        PartyList party = findParty(request.getPartyId());

        //  Generate receipt using utility class
        ReceiptGenerator receiptGenerator = new ReceiptGenerator();
        String referenceNumber = receiptGenerator.generateReceipt(request.getPartyId());

        // Save votes
        Voting vote = saveVote(userFound, party, referenceNumber);

        return VoteResponse.builder()
                .referenceNo(vote.getReferenceNo())
                .partyName(party.getPartyName())
                .description("Vote successfully cast")
                .build();
    }

    @Override
    public void validateVotingEligibility(UserDetails user) {
        if (!isEligibleToVote(user)) {
            throw new IneligibleVoterException(
                    String.format("User must be %d or older to vote.", MINIMUM_VOTING_AGE)
            );
        }
    }

    private void checkExistingVote(UserDetails user) {
        Optional<Voting> existingVote = votingRepository.findByUserDetails(user);

        if (existingVote.isPresent()) {
            throw new DuplicateResourceException("This user has already voted.");
        }
    }

    private PartyList findParty(Integer partyId) {
        Optional<PartyList> partyOptional = partyListRepository.findById(partyId);

        if (partyOptional.isPresent()) {
            return partyOptional.get();
        } else {
            throw new ResourceNotFoundException("Party not found with ID: " + partyId);
        }
    }

    @Override
    public boolean isEligibleToVote(UserDetails userDetails) {
        LocalDate dob = userDetails.getDateOfBirth();
        int age = Period.between(dob, LocalDate.now()).getYears();
        return age >= MINIMUM_VOTING_AGE;
    }

    @Override
    @Transactional
    public Voting saveVote(UserDetails user, PartyList partyList, String referenceNo) {
        Voting vote = new Voting();
        vote.setReferenceNo(referenceNo);
        vote.setUserDetails(user);
        vote.setPartyList(partyList);
        return votingRepository.save(vote);
    }

    @Override
    public List<Voting> votingReceiptDisplays() {
        List<Voting> votes = votingRepository.findAll();
        if (votes.isEmpty()) {
            throw new ResourceNotFoundException("No voting records found.");
        }
        return votes;
    }

    @Override
    public Long getTotalCountVoter() {
        Long count = votingRepository.getTotalCountVoter();
        if (count == null) {
            return 0L;
        }
        return count;
    }

    @Override
    public PartyVoteSummary getTotalVotesByParty(Integer partyId) {
        // Validate party exists
        PartyList party = findParty(partyId);

        Long totalVotes = votingRepository.getAllTotalVotersVoteNumberByParty(partyId);

        if (totalVotes == null) {
            totalVotes = 0L;
        }

        return PartyVoteSummary.builder()
                .partyId(partyId)
                .partyName(party.getPartyName())
                .totalVotes(totalVotes)
                .build();
    }
}