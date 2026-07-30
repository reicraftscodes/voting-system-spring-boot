package com.lms.voting.api.service;

import com.lms.voting.api.model.dto.*;

import java.util.List;

public interface UserDetailsService {

    UserDetailsRequestDto addPersonalDetails(UserDetailsRequestDto userDetails);

    UpdateUserDetailsDto updateUserDetails(Integer id, UpdateUserDetailsDto updateDetailsDto);

    VoterAddressDto addUserVoterAddress(Integer accountInfoId, VoterAddressDto voterAddressDto);

    List<VoterAddressDto> getAddressesByAccountId(Integer accountInfoId);

    UserDetailsDto getPersonalDetailsById(Integer accountId);

    PollReferenceDto addUserVoterPollReference(Integer accountInfoId, PollReferenceDto pollReferenceDto);
}
