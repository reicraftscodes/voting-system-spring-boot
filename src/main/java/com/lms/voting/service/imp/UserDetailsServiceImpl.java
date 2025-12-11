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
        UpdateUserDetailsDto updateDetailsDto = new UpdateUserDetailsDto();
        updateDetailsDto.setId(user.getId());
        updateDetailsDto.setFirstName(user.getFirstName());
        updateDetailsDto.setLastName(user.getLastName());
        updateDetailsDto.setDateOfBirth(user.getDateOfBirth());
        updateDetailsDto.setNationalInsuranceNumber(user.getNationalInsuranceNumber());

        return updateDetailsDto;
    }


    // general recommended practice not to update directly through entity, so you need DTO
    @Transactional
    public UpdateUserDetailsDto updateUserDetails(Integer id, UpdateUserDetailsDto updateDetailsDto) {
        UserDetails user = userDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // update only the allowed fields
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
