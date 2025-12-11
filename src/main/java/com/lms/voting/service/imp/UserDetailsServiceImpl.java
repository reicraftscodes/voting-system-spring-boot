package com.lms.voting.service.imp;

import com.lms.voting.dto.UpdateUserDetailsDto;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.exception.DuplicateValueException;
import com.lms.voting.repository.UserDetailsRepository;
import com.lms.voting.service.UserDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/***
 * Service annotation
 * This make whole class available as a BEAN, so that basically Spring instantiate it, and
 * then it makes it available to use within other class
 * */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    // get all personal details
    public List<UserDetails> getAllPersonalDetails() {
        return userDetailsRepository.findAll();
    }

    // add personal details
    public UserDetails addPersonalDetails(UserDetails userDetails) {
        // check if there's an existing insurance number
        if (userDetailsRepository.existsByNationalInsuranceNumber(userDetails.getNationalInsuranceNumber())) {
            throw new DuplicateValueException("A user with this insurance number already exists.");
        }

        return userDetailsRepository.save(userDetails);
    }

    // retrieve a single user
    public Optional<UserDetails> getPersonalDetailsByID(Integer id) {
        return userDetailsRepository.findById(id);
    }


    private static UpdateUserDetailsDto getUpdateDetailsDto(UserDetails user) {
        // Create a new DTO instance to store the mapped user data.
        UpdateUserDetailsDto updateDetailsDto = new UpdateUserDetailsDto();

        // Map entity fields to the DTO fields.
        updateDetailsDto.setId(user.getId());
        updateDetailsDto.setFirstName(user.getFirstName());
        updateDetailsDto.setLastName(user.getLastName());
        updateDetailsDto.setDateOfBirth(user.getDateOfBirth());
        updateDetailsDto.setNationalInsuranceNumber(user.getNationalInsuranceNumber());

        // Return the fully populated DTO.
        return updateDetailsDto;
    }

    @Transactional
    public UpdateUserDetailsDto updateUserDetails(Integer id, UpdateUserDetailsDto updateUserDetailsDto) {

        // Attempt to retrieve the user by ID.
        // If no user is found, throw an exception to indicate invalid input.
        UserDetails user = userDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update the user's fields with values from the DTO.
        // Only fields provided by the DTO will overwrite existing values.
        user.setFirstName(updateUserDetailsDto.getFirstName());
        user.setLastName(updateUserDetailsDto.getLastName());
        user.setDateOfBirth(updateUserDetailsDto.getDateOfBirth());
        user.setNationalInsuranceNumber(updateUserDetailsDto.getNationalInsuranceNumber());

        // Persist the updated user details to the database.
        userDetailsRepository.save(user);

        // Convert the updated entity back into a DTO and return it.
        return getUpdateDetailsDto(user);
    }


}
