package com.lms.voting.controller;

import com.lms.voting.constant.UserDetailsConstant;
import com.lms.voting.model.dto.UpdateUserDetailsDto;
import com.lms.voting.model.dto.UserDetailsRequestDto;
import com.lms.voting.model.dto.VoterAddressDto;
import com.lms.voting.model.entity.AccountInfo;
import com.lms.voting.service.UserDetailsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    // create user (personal details only, no address)
    @PostMapping(produces = "application/json")
    public ResponseEntity<UserDetailsRequestDto> createUser(@RequestBody @Valid UserDetailsRequestDto userDetails) {
        UserDetailsRequestDto addedUserDetails = userDetailsService.addPersonalDetails(userDetails);
        log.info(UserDetailsConstant.RESULT_DESCRIPTION_SUCCESS, addedUserDetails.getNationalInsuranceNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUserDetails);
    }

    // add an address to an existing user acountId
    @PostMapping(value = "/{accountId}/address", produces = "application/json", consumes = "application/json")
    public ResponseEntity<VoterAddressDto> createUserAddress(@PathVariable Integer accountId, @RequestBody @Valid VoterAddressDto voterAddress) {
        VoterAddressDto added = userDetailsService.addUserVoterAddress(accountId, voterAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

    // get all addresses for a user
        @GetMapping(value = "/{accountId}/address", produces = "application/json")
    public ResponseEntity<List<VoterAddressDto>> getUserAddresses(@PathVariable Integer accountId) {
        List<VoterAddressDto> addresses = userDetailsService.getAddressesByAccountId(accountId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountInfo> getPersonalDetailsByID(@PathVariable Integer id) {
        return userDetailsService.getPersonalDetailsByID(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<UpdateUserDetailsDto> updateUserDetails(@PathVariable Integer id, @RequestBody UpdateUserDetailsDto updateDetailsDto) {
        UpdateUserDetailsDto updated = userDetailsService.updateUserDetails(id, updateDetailsDto);
        log.info(UserDetailsConstant.RESULT_UPDATED_INFO_SUCCESS, updated.getId());
        return ResponseEntity.ok(updated);
    }

}