package com.lms.voting.service.imp;

import com.lms.voting.model.dto.UpdateUserDetailsDto;
import com.lms.voting.model.dto.UserDetailsRequestDto;
import com.lms.voting.model.entity.AccountInfo;
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
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setNationalInsuranceNumber(userDetailsDto.getNationalInsuranceNumber());
        accountInfo.setFirstName(userDetailsDto.getFirstName());
        accountInfo.setLastName(userDetailsDto.getLastName());
        accountInfo.setDateOfBirth(userDetailsDto.getDateOfBirth());

        // Save the entity
        AccountInfo savedAccountInfo = userDetailsRepository.save(accountInfo);

        // Map the saved entity to DTO before returning
        UserDetailsRequestDto savedUserDetailsDto = new UserDetailsRequestDto();
        savedUserDetailsDto.setId(savedAccountInfo.getId());
        savedUserDetailsDto.setFirstName(savedAccountInfo.getFirstName());
        savedUserDetailsDto.setLastName(savedAccountInfo.getLastName());
        savedUserDetailsDto.setDateOfBirth(savedAccountInfo.getDateOfBirth());
        savedUserDetailsDto.setNationalInsuranceNumber(savedAccountInfo.getNationalInsuranceNumber());

        return savedUserDetailsDto;
    }

    public Optional<AccountInfo> getPersonalDetailsByID(Integer id) {
        return userDetailsRepository.findById(id);
    }

    private static UpdateUserDetailsDto getUpdateDetailsDto(AccountInfo user) {
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
        AccountInfo user = userDetailsRepository
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
