package com.lms.voting.service;

import com.lms.voting.model.dto.UpdateUserDetailsDto;
import com.lms.voting.model.dto.UserDetailsRequestDto;
import com.lms.voting.model.dto.VoterAddressDto;
import com.lms.voting.model.entity.AccountInfo;

import java.util.List;
import java.util.Optional;

public interface UserDetailsService {

    UserDetailsRequestDto addPersonalDetails(UserDetailsRequestDto userDetails);

    Optional<AccountInfo> getPersonalDetailsByID(Integer id);

    UpdateUserDetailsDto updateUserDetails(Integer id, UpdateUserDetailsDto updateDetailsDto);

    VoterAddressDto addUserVoterAddress(Integer accountInfoId, VoterAddressDto voterAddressDto);

    List<VoterAddressDto> getAddressesByAccountId(Integer accountInfoId);


}
