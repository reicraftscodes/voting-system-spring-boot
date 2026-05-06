package com.lms.voting.service;

import com.lms.voting.dto.CastVoteRequestDto;
import com.lms.voting.dto.VoteResponseDto;
import com.lms.voting.entity.PartyList;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.entity.Voting;
import com.lms.voting.exception.IneligibleVoterException;
import com.lms.voting.exception.InvalidRequestException;
import com.lms.voting.repository.PartyListRepository;
import com.lms.voting.repository.UserDetailsRepository;
import com.lms.voting.repository.VotingRepository;
import com.lms.voting.service.imp.VotingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VotingServiceTests {

    @Mock
    private VotingRepository votingRepository;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private PartyListRepository partyListRepository;

    @InjectMocks
    private VotingServiceImpl votingService;

    @Test
    void shouldReturnSuccessWhenUserSuccessfullyCastsVote() {
        String nationalInsuranceNumber = "123456SDLC";
        String lastName = "Doe";
        Integer partyId = 1;

        CastVoteRequestDto castVoteRequestDto = new CastVoteRequestDto();
        castVoteRequestDto.setNationalInsuranceNumber(nationalInsuranceNumber);
        castVoteRequestDto.setLastName(lastName);
        castVoteRequestDto.setPartyId(partyId);

        // mock UserDetails repository behaviour (valid user found)
        UserDetails mockUserDetails = new UserDetails();
        mockUserDetails.setNationalInsuranceNumber(nationalInsuranceNumber);
        mockUserDetails.setLastName(lastName);
        mockUserDetails.setDateOfBirth(LocalDate.of(2000, 1, 1));

        when(userDetailsRepository.findByNationalInsuranceNumberAndLastName(nationalInsuranceNumber, lastName))
                .thenReturn(Optional.of(mockUserDetails));

        // mock PartyList repository behaviour (party exists)
        PartyList mockParty = new PartyList();
        mockParty.setPartyName("Labour");
        mockParty.setPosition("Left Wing");

        when(partyListRepository.findById(partyId)).thenReturn(Optional.of(mockParty));

        // mock voting repository behavior (saving vote)
        Voting mockVote = new Voting();
        mockVote.setReferenceNo("REFERENCE123");

        when(votingRepository.save(any(Voting.class))).thenReturn(mockVote);

        VoteResponseDto response = votingService.castVote(castVoteRequestDto);

        assertNotNull(response);
        assertEquals("REFERENCE123", response.getReferenceNo());
        assertEquals("Labour", response.getPartyName());
        assertEquals("Vote successfully cast", response.getDescription());

        verify(userDetailsRepository, times(1)).findByNationalInsuranceNumberAndLastName(nationalInsuranceNumber, lastName);
        verify(partyListRepository, times(1)).findById(partyId);
        verify(votingRepository, times(1)).save(any(Voting.class));
    }

    @Test
    void shouldThrowInvalidRequestExceptionWhenUserCredentialsAreInvalid() {

        String nationalInsuranceNumber = "123456SDLC";
        String lastName = "Doe";
        Integer partyId = 1;

        CastVoteRequestDto castVoteRequestDto = new CastVoteRequestDto();
        castVoteRequestDto.setNationalInsuranceNumber(nationalInsuranceNumber);
        castVoteRequestDto.setLastName(lastName);
        castVoteRequestDto.setPartyId(partyId);

        when(userDetailsRepository.findByNationalInsuranceNumberAndLastName(nationalInsuranceNumber, lastName))
                // return empty Optional to simulate invalid credentials
                .thenReturn(Optional.empty());

        assertThrows(InvalidRequestException.class, () -> {
            votingService.castVote(castVoteRequestDto);
        });

        // verify interactions with repositories
        verify(userDetailsRepository, times(1)).findByNationalInsuranceNumberAndLastName(nationalInsuranceNumber, lastName);
        // party repository shouldn't be called if user is invalid
        verify(partyListRepository, never()).findById(partyId);
        // vote repository shouldn't be called if user is invalid
        verify(votingRepository, never()).save(any(Voting.class));
    }

    @Test
    void verifyEligibleVoterAgeThenReturnSucess() {
        UserDetails user = mock(UserDetails.class);
        LocalDate dob = LocalDate.of(2000, 1, 1);

        when(user.getDateOfBirth()).thenReturn(dob);

        boolean isEligible = votingService.isEligibleToVote(user);

        assertTrue(isEligible, "User must be %d or older to vote");

    }

    @Test
    void verifyIneligibleVoterAgeThenReturnSuccess() {
        UserDetails user = mock(UserDetails.class);
        LocalDate dob = LocalDate.of(2010, 1, 1); // Birthdate: January 1, 2010 (ineligible)
        when(user.getDateOfBirth()).thenReturn(dob);

        boolean isEligible = votingService.isEligibleToVote(user);

        assertFalse(isEligible, "User should not be eligible to vote");
    }

    @Test
    void verifyValidateVotingEligibilityForEligibleUser() {
        UserDetails user = mock(UserDetails.class);
        LocalDate dob = LocalDate.of(2000, 1, 1); // Birthdate: January 1, 2000 (eligible)
        when(user.getDateOfBirth()).thenReturn(dob);

        assertDoesNotThrow(() -> votingService.isEligibleToVote(user), "Eligible user should not throw exception");
    }


    @Test
    void verifyIneligibleVoterAgeThenReturnException() {

        UserDetails user = mock(UserDetails.class);
        LocalDate dob = LocalDate.of(2011, 1, 1);
        when(user.getDateOfBirth()).thenReturn(dob);

        // should throw IneligibleVoterException for ineligible user
        IneligibleVoterException exception = assertThrows(IneligibleVoterException.class, () -> {
            votingService.validateVotingEligibility(user);
        });

        assertEquals("User must be 18 or older to vote.", exception.getMessage());
    }


}
