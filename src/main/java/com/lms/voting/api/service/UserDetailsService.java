package com.lms.voting.api.service;

import com.lms.voting.api.model.dto.UpdateUserDetailsDto;
import com.lms.voting.api.model.dto.UserDetailsDto;
import com.lms.voting.api.model.dto.UserDetailsRequestDto;
import com.lms.voting.api.model.dto.VoterAddressDto;

import java.util.List;

public interface UserDetailsService {

    UserDetailsRequestDto addPersonalDetails(UserDetailsRequestDto userDetails);

    UpdateUserDetailsDto updateUserDetails(Integer id, UpdateUserDetailsDto updateDetailsDto);

    VoterAddressDto addUserVoterAddress(Integer accountInfoId, VoterAddressDto voterAddressDto);

    List<VoterAddressDto> getAddressesByAccountId(Integer accountInfoId);

    UserDetailsDto getPersonalDetailsById(Integer accountId);
}
