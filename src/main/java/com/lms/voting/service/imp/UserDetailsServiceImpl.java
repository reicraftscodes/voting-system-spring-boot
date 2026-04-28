package com.lms.voting.service.imp;

import com.lms.voting.dto.UpdateUserDetailsDto;
import com.lms.voting.dto.UserDetailsRequestDto;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.repository.UserDetailsRepository;
import com.lms.voting.service.UserDetailsService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }


    // add personal details
    public UserDetailsRequestDto addPersonalDetails(UserDetailsRequestDto userDetailsDto) {
        // Check if there's an existing insurance number
        if (userDetailsRepository.existsByNationalInsuranceNumber(userDetailsDto.getNationalInsuranceNumber())) {
            throw new DuplicateRequestException("A user with this national insurance number already exists.");
        }

        // Convert DTO to Entity
        UserDetails userDetails = new UserDetails();
        userDetails.setNationalInsuranceNumber(userDetailsDto.getNationalInsuranceNumber());
        userDetails.setFirstName(userDetailsDto.getFirstName());
        userDetails.setLastName(userDetailsDto.getLastName());
        userDetails.setDateOfBirth(userDetailsDto.getDateOfBirth());

        // Save the entity
        UserDetails savedUserDetails = userDetailsRepository.save(userDetails);

        // Map the saved entity to DTO before returning
        UserDetailsRequestDto savedUserDetailsDto = new UserDetailsRequestDto();
        savedUserDetailsDto.setId(savedUserDetails.getId());
        savedUserDetailsDto.setFirstName(savedUserDetails.getFirstName());
        savedUserDetailsDto.setLastName(savedUserDetails.getLastName());
        savedUserDetailsDto.setDateOfBirth(savedUserDetails.getDateOfBirth());
        savedUserDetailsDto.setNationalInsuranceNumber(savedUserDetails.getNationalInsuranceNumber());

        return savedUserDetailsDto;
    }


    // retrieve a single user
    public Optional<UserDetails> getPersonalDetailsByID(Integer id) {
        return userDetailsRepository.findById(id);
    }


    private static UpdateUserDetailsDto getUpdateDetailsDto(UserDetails user) {
        UpdateUserDetailsDto updateDetailsDto = new UpdateUserDetailsDto();
        updateDetailsDto.setId(user.getId());
        updateDetailsDto.setFirstName(user.getFirstName());
        updateDetailsDto.setLastName(user.getLastName());
        updateDetailsDto.setDateOfBirth(user.getDateOfBirth());
        updateDetailsDto.setNationalInsuranceNumber(user.getNationalInsuranceNumber());

        return updateDetailsDto;
    }

    @Transactional
    public UpdateUserDetailsDto updateUserDetails(Integer id, UpdateUserDetailsDto updateDetailsDto) {
        UserDetails user = userDetailsRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(updateDetailsDto.getFirstName());
        user.setLastName(updateDetailsDto.getLastName());
        user.setDateOfBirth(updateDetailsDto.getDateOfBirth());
        user.setNationalInsuranceNumber(updateDetailsDto.getNationalInsuranceNumber());

        // save all new details to the repo
        userDetailsRepository.save(user);

        // return new update user details via dto
        return getUpdateDetailsDto(user);

    }


}
