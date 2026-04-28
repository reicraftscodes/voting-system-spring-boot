package com.lms.voting.service;

import com.lms.voting.dto.UpdateUserDetailsDto;
import com.lms.voting.dto.UserDetailsRequestDto;
import com.lms.voting.entity.UserDetails;

import java.util.Optional;

public interface UserDetailsService {

    UserDetailsRequestDto addPersonalDetails(UserDetailsRequestDto userDetails);

    Optional<UserDetails> getPersonalDetailsByID(Integer id);

    UpdateUserDetailsDto updateUserDetails(Integer id, UpdateUserDetailsDto updateDetailsDto);

}
