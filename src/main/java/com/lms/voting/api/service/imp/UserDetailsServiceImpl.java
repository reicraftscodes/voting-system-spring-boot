package com.lms.voting.api.service.imp;

import com.lms.voting.api.exception.DuplicateResourceException;
import com.lms.voting.api.exception.ResourceNotFoundException;
import com.lms.voting.api.model.dto.UpdateUserDetailsDto;
import com.lms.voting.api.model.dto.UserDetailsDto;
import com.lms.voting.api.model.dto.UserDetailsRequestDto;
import com.lms.voting.api.model.dto.VoterAddressDto;
import com.lms.voting.api.model.entity.AccountInfo;
import com.lms.voting.api.model.entity.VoterAddress;
import com.lms.voting.api.repository.UserDetailsRepository;
import com.lms.voting.api.repository.VoterAddressRepository;
import com.lms.voting.api.service.UserDetailsService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lms.voting.api.constant.VotingDetailsConstant.ERR_MAX_VALID_ADDRESSES_REACHED;

/**
 * Service implementation responsible for managing user personal details
 * and voter address information.
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private VoterAddressRepository voterAddressRepository;

    /**
     * Creates a new user account after validating that the National Insurance Number is unique.
     *
     * @param userDetailsDto User details received from the client
     * @return Saved user details as DTO
     * @throws DuplicateResourceException if NI number already exists
     */
    @Override
    public UserDetailsRequestDto addPersonalDetails(UserDetailsRequestDto userDetailsDto) {

        // Prevent duplicate user registration using NI number
        if (userDetailsRepository.existsByNationalInsuranceNumber(
                userDetailsDto.getNationalInsuranceNumber())) {

            log.warn("Duplicate NI number attempt: {}",
                    userDetailsDto.getNationalInsuranceNumber());

            throw new DuplicateResourceException(
                    "A user with this national insurance number already exists.");
        }

        // Convert DTO to entity
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setNationalInsuranceNumber(userDetailsDto.getNationalInsuranceNumber());
        accountInfo.setFirstName(userDetailsDto.getFirstName());
        accountInfo.setLastName(userDetailsDto.getLastName());
        accountInfo.setDateOfBirth(userDetailsDto.getDateOfBirth());

        // Persist entity to database
        AccountInfo savedAccountInfo = userDetailsRepository.save(accountInfo);

        // Convert saved entity back to DTO
        UserDetailsRequestDto userDetailsSavedDto = new UserDetailsRequestDto();
        userDetailsSavedDto.setId(savedAccountInfo.getId());
        userDetailsSavedDto.setFirstName(savedAccountInfo.getFirstName());
        userDetailsSavedDto.setLastName(savedAccountInfo.getLastName());
        userDetailsSavedDto.setDateOfBirth(savedAccountInfo.getDateOfBirth());
        userDetailsSavedDto.setNationalInsuranceNumber(savedAccountInfo.getNationalInsuranceNumber());

        return userDetailsSavedDto;
    }

    /**
     * Adds a voter address for an existing user.
     * The operation is transactional to ensure data consistency.
     *
     * @param accountInfoId   User account ID
     * @param voterAddressDto Address information
     * @return Saved voter address DTO
     * @throws ResourceNotFoundException if user does not exist
     */
    @Override
    @Transactional
    public VoterAddressDto addUserVoterAddress(Integer accountInfoId, VoterAddressDto voterAddressDto) {

        // Retrieve the user account associated with this address
        AccountInfo accountInfo = userDetailsRepository.findById(accountInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + accountInfoId));

        // Create address entity from DTO
        VoterAddress voterAddress = new VoterAddress();
        voterAddress.setAddressOne(voterAddressDto.getAddressOne());
        voterAddress.setAddressTwo(voterAddressDto.getAddressTwo());
        voterAddress.setTownCity(voterAddressDto.getTownCity());
        voterAddress.setPostcode(voterAddressDto.getPostcode());

        // Establish relationship between address and user
        voterAddress.setAccountInfo(accountInfo);

        // Save address record
        VoterAddress saved = voterAddressRepository.save(voterAddress);

        log.info("Voter address created for accountId={}: {}", accountInfoId, saved.getId());

        // Users can only have 2 maximum addresses
        if (saved.getId() > 2) {
            log.warn(ERR_MAX_VALID_ADDRESSES_REACHED, accountInfoId);
            throw new IllegalArgumentException("Maximum number of valid addresses reached for user.");
        }

        return toVoterAddressDto(saved);
    }

    /**
     * Retrieves all voter addresses linked to a specific user account.
     *
     * @param accountInfoId User account ID
     * @return List of voter address DTOs
     * @throws ResourceNotFoundException if user does not exist
     */
    @Override
    public List<VoterAddressDto> getAddressesByAccountId(Integer accountInfoId) {

        // Validate that the user exists before searching for addresses
        if (!userDetailsRepository.existsById(accountInfoId)) {
            throw new ResourceNotFoundException("User not found with id: " + accountInfoId);
        }

        // Convert each address entity into a DTO
        List<VoterAddressDto> list = new ArrayList<>();
        for (VoterAddress voterAddress : voterAddressRepository.findByAccountInfoId(accountInfoId)) {
            VoterAddressDto voterAddressDto = toVoterAddressDto(voterAddress);
            list.add(voterAddressDto);
        }
        return list;
    }


    @Override
    public UserDetailsDto getPersonalDetailsById(Integer accountId) {

        // find entity
        AccountInfo user = userDetailsRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + accountId));

        UserDetailsDto userDetailsdto = new UserDetailsDto();

        userDetailsdto.setId(user.getId());
        userDetailsdto.setFirstName(user.getFirstName());
        userDetailsdto.setLastName(user.getLastName());
        userDetailsdto.setNationalInsuranceNumber(user.getNationalInsuranceNumber());
        userDetailsdto.setDateOfBirth(user.getDateOfBirth());
        userDetailsdto.setVoterAddress(user.getVoterAddresses());

        return userDetailsdto;
    }


    /**
     * Updates an existing user's personal information.
     *
     * @param id               User account ID
     * @param updateDetailsDto Updated user details
     * @return Updated user details DTO
     * @throws ResourceNotFoundException if user does not exist
     */
    @Override
    @Transactional
    public UpdateUserDetailsDto updateUserDetails(Integer id, UpdateUserDetailsDto updateDetailsDto) {

        // Retrieve existing user record
        AccountInfo user = userDetailsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));

        // Apply updates
        user.setFirstName(updateDetailsDto.getFirstName());
        user.setLastName(updateDetailsDto.getLastName());
        user.setDateOfBirth(updateDetailsDto.getDateOfBirth());
        user.setNationalInsuranceNumber(updateDetailsDto.getNationalInsuranceNumber());

        // Save updated entity
        userDetailsRepository.save(user);

        return toUpdateUserDetailsDto(user);
    }

    /**
     * Converts a VoterAddress entity into a DTO.
     *
     * @param addr Address entity
     * @return Address DTO
     */
    private VoterAddressDto toVoterAddressDto(VoterAddress addr) {
        VoterAddressDto dto = new VoterAddressDto();
        dto.setId(addr.getId());
        dto.setAccountInfoId(addr.getAccountInfo().getId());
        dto.setAddressOne(addr.getAddressOne());
        dto.setAddressTwo(addr.getAddressTwo());
        dto.setTownCity(addr.getTownCity());
        dto.setPostcode(addr.getPostcode());
        return dto;
    }

    /**
     * Converts an AccountInfo entity into an UpdateUserDetailsDto.
     *
     * @param user User entity
     * @return User details DTO
     */
    private static UpdateUserDetailsDto toUpdateUserDetailsDto(AccountInfo user) {
        UpdateUserDetailsDto dto = new UpdateUserDetailsDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setNationalInsuranceNumber(user.getNationalInsuranceNumber());
        return dto;
    }
}