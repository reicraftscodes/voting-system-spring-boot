package com.lms.voting.controller;

import com.lms.voting.constant.UserDetailsConstant;
import com.lms.voting.model.dto.UpdateUserDetailsDto;
import com.lms.voting.model.dto.UserDetailsRequestDto;
import com.lms.voting.model.entity.AccountInfo;
import com.lms.voting.service.UserDetailsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<UserDetailsRequestDto> createUser(@RequestBody @Valid UserDetailsRequestDto userDetails) {
        UserDetailsRequestDto addedUserDetails = userDetailsService.addPersonalDetails(userDetails);
        log.info(UserDetailsConstant.RESULT_DESCRIPTION_SUCCESS, addedUserDetails.getNationalInsuranceNumber());
        return ResponseEntity.status(HttpStatus.OK).body(addedUserDetails);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountInfo> getPersonalDetailsByID(@PathVariable Integer id) {
        log.info(UserDetailsConstant.RESULT_FETCH_SUCCESS_SUCCESS, userDetailsService.getPersonalDetailsByID(id));
        return userDetailsService.getPersonalDetailsByID(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<UpdateUserDetailsDto> updateUserDetails(@PathVariable Integer id, @RequestBody UpdateUserDetailsDto updateDetailsDto) {
        UpdateUserDetailsDto updated = userDetailsService.updateUserDetails(id, updateDetailsDto);
        log.info(UserDetailsConstant.RESULT_UPDATED_INFO_SUCCESS, updateDetailsDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

}
