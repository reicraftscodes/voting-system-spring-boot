package com.lms.voting.api.controller;

import com.lms.voting.api.constant.UserDetailsConstant;
import com.lms.voting.api.model.dto.*;
import com.lms.voting.api.service.UserDetailsService;
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
@CrossOrigin(origins = "http://localhost:5173/")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<UserDetailsRequestDto> createUser(@RequestBody @Valid UserDetailsRequestDto userDetails) {
        UserDetailsRequestDto addedUserDetails = userDetailsService.addPersonalDetails(userDetails);
        log.info(UserDetailsConstant.RESULT_DESCRIPTION_SUCCESS, addedUserDetails.getNationalInsuranceNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUserDetails);
    }

    @PostMapping(value = "/{accountId}/address", produces = "application/json", consumes = "application/json")
    public ResponseEntity<VoterAddressDto> createUserAddress(@PathVariable Integer accountId, @RequestBody @Valid VoterAddressDto voterAddress) {
        VoterAddressDto added = userDetailsService.addUserVoterAddress(accountId, voterAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

    @GetMapping(value = "/{accountId}/address", produces = "application/json")
    public ResponseEntity<List<VoterAddressDto>> getUserAddresses(@PathVariable Integer accountId) {
        List<VoterAddressDto> addresses = userDetailsService.getAddressesByAccountId(accountId);
        return ResponseEntity.status(HttpStatus.CREATED).body(addresses);
    }

    @PostMapping(value = "/{accountId}/pollRef", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PollReferenceRequestDto> createPollRef(@PathVariable Integer accountId, @RequestBody PollReferenceRequestDto pollReferenceRequestDto) {
        PollReferenceRequestDto pollReference = userDetailsService.addUserVoterPollReference(accountId, pollReferenceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pollReference);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getPersonalDetailsByID(@PathVariable Integer id) {
        UserDetailsDto userDetailsDto = userDetailsService.getPersonalDetailsById(id);
        return ResponseEntity.ok(userDetailsDto);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<UpdateUserDetailsDto> updateUserDetails(@PathVariable Integer id, @RequestBody UpdateUserDetailsDto updateDetailsDto) {
        UpdateUserDetailsDto updated = userDetailsService.updateUserDetails(id, updateDetailsDto);
        log.info(UserDetailsConstant.RESULT_UPDATED_INFO_SUCCESS, updated.getId());
        return ResponseEntity.ok(updated);
    }

}