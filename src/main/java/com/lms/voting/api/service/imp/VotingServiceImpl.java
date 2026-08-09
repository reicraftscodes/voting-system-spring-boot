package com.lms.voting.api.service.imp;

import com.lms.voting.api.exception.DuplicateResourceException;
import com.lms.voting.api.exception.IneligibleVoterException;
import com.lms.voting.api.exception.InvalidRequestException;
import com.lms.voting.api.exception.ResourceNotFoundException;
import com.lms.voting.api.model.dto.*;
import com.lms.voting.api.model.entity.AccountInfo;
import com.lms.voting.api.model.entity.PartyList;
import com.lms.voting.api.model.entity.Voting;
import com.lms.voting.api.repository.PartyListRepository;
import com.lms.voting.api.repository.UserDetailsRepository;
import com.lms.voting.api.repository.VotingRepository;
import com.lms.voting.api.service.VotingService;
import com.lms.voting.api.util.ReceiptGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lms.voting.api.constant.VotingDetailsConstant.MINIMUM_VOTING_AGE;


@Service
public class VotingServiceImpl implements VotingService {

    @Autowired
    private PartyListRepository partyListRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private VotingRepository votingRepository;

    @Override
    @Transactional
    public VoteResponseDto castVote(CastVoteRequestDto request) {

        AccountInfo userFound = findVoter(request.getNationalInsuranceNumber(), request.getLastName());

        validateVotingEligibility(userFound);

        checkExistingVote(userFound);

        // Validate party exists
        PartyList party = findParty(request.getPartyId());

        //  Generate receipt using utility class
        ReceiptGenerator receiptGenerator = new ReceiptGenerator();
        String referenceNumber = receiptGenerator.generateReceipt(request.getPartyId());

        // Save votes
        Voting vote = saveVote(userFound, party, referenceNumber);

        return VoteResponseDto.builder()
                .referenceNo(vote.getReferenceNo())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public VoterVerificationResponseDto verifyVoter(VerifyVoterRequestDto request) {

        AccountInfo userFound = findVoter(request.getNationalInsuranceNumber(), request.getLastName());

        checkExistingVote(userFound);

        validateVotingEligibility(userFound);

        return VoterVerificationResponseDto.builder()
                .accountId(userFound.getId())
                .firstName(userFound.getFirstName())
                .lastName(userFound.getLastName())
                .nationalInsuranceNumber(userFound.getNationalInsuranceNumber())
                .dateOfBirth(userFound.getDateOfBirth())
                .parties(getAllParties())
                .build();
    }

    // Looks up a voter by National Insurance number and last name. Shared by castVote and verifyVoter so both go through the same electoral register match.
    private AccountInfo findVoter(String nationalInsuranceNumber, String lastName) {
        Optional<AccountInfo> userOptional = userDetailsRepository.findByNationalInsuranceNumberAndLastName(nationalInsuranceNumber, lastName);

        if (userOptional.isEmpty()) {
            throw new InvalidRequestException("Invalid credentials. Please check your National Insurance Number and Last Name.");
        }

        return userOptional.get();
    }

    private List<PartyListDto> getAllParties() {
        List<PartyList> partyLists = partyListRepository.findAll();

        List<PartyListDto> partyListDtos = new ArrayList<>();

        for (PartyList partyList : partyLists) {
            PartyListDto dto = new PartyListDto();
            dto.setId(partyList.getId());
            dto.setPartyName(partyList.getPartyName());
            dto.setPosition(partyList.getPosition());
            partyListDtos.add(dto);
        }

        return partyListDtos;
    }

    @Override
    public void validateVotingEligibility(AccountInfo user) {
        if (!isEligibleToVote(user)) {
            throw new IneligibleVoterException(
                    String.format("User must be %d or older to vote.", MINIMUM_VOTING_AGE)
            );
        }
    }

    private void checkExistingVote(AccountInfo accountInfo) {
        Optional<Voting> existingVote = votingRepository.findByUserDetails(accountInfo);

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
    public boolean isEligibleToVote(AccountInfo accountInfo) {
        LocalDate dob = accountInfo.getDateOfBirth();
        int age = Period.between(dob, LocalDate.now()).getYears();
        return age >= MINIMUM_VOTING_AGE;
    }

    @Override
    @Transactional
    public Voting saveVote(AccountInfo user, PartyList partyList, String referenceNo) {
        Voting vote = new Voting();
        vote.setReferenceNo(referenceNo);
        vote.setUserDetails(user);
        vote.setPartyList(partyList);
        return votingRepository.save(vote);
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
    public PartyVoteResponse getTotalVotesByParty(Integer partyId) {
        // Validate party exists
        PartyList party = findParty(partyId);

        Long totalVotes = votingRepository.getAllTotalVotersVoteNumberByParty(partyId);

        if (totalVotes == null) {
            totalVotes = 0L;
        }

        return PartyVoteResponse.builder()
                .partyId(partyId)
                .partyName(party.getPartyName())
                .totalVotes(totalVotes)
                .build();
    }
}