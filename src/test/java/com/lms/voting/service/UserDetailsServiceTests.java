package com.lms.voting.service;

import com.lms.voting.entity.UserDetails;
import com.lms.voting.repository.UserDetailsRepository;
import com.lms.voting.service.imp.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTests {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void shouldReturnUserDetailsWhenUserIdIsValid() {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(1);
        userDetails.setFirstName("John");
        userDetails.setLastName("Doe");
        userDetails.setNationalInsuranceNumber("1234567DL");
        userDetails.setDateOfBirth(LocalDate.of(2000, 2, 2));

        when(userDetailsRepository.findById(1)).thenReturn(Optional.of(userDetails));

        Optional<UserDetails> result = userDetailsService.getPersonalDetailsByID(1);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(1, result.get().getId());
        Assertions.assertEquals("John", result.get().getFirstName());
        Assertions.assertEquals("Doe", result.get().getLastName());
        Assertions.assertEquals("1234567DL", result.get().getNationalInsuranceNumber());
        Assertions.assertEquals(LocalDate.of(2000, 2, 2), result.get().getDateOfBirth());
    }
}
