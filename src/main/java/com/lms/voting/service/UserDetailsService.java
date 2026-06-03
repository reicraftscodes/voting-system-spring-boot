package com.lms.voting.service;

import com.lms.voting.model.dto.UpdateUserDetailsDto;
import com.lms.voting.model.dto.UserDetailsDto;
import com.lms.voting.model.dto.UserDetailsRequestDto;
import com.lms.voting.model.dto.VoterAddressDto;

import java.util.List;

public interface UserDetailsService {

    UserDetailsRequestDto addPersonalDetails(UserDetailsRequestDto userDetails);

    UpdateUserDetailsDto updateUserDetails(Integer id, UpdateUserDetailsDto updateDetailsDto);

    VoterAddressDto addUserVoterAddress(Integer accountInfoId, VoterAddressDto voterAddressDto);

    List<VoterAddressDto> getAddressesByAccountId(Integer accountInfoId);

    UserDetailsDto getPersonalDetailsById(Integer accountId);
}
