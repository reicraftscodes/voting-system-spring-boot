package com.lms.voting.controller;

import com.lms.voting.dto.UpdateUserDetailsDto;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/person-details")
public class UserDetailsController {


    private final UserDetailsService userDetailsService;

    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createUser(@RequestBody UserDetails userDetails) {
        UserDetails addedUserDetails = userDetailsService.addPersonalDetails(userDetails);
        return ResponseEntity.ok(addedUserDetails);
    }


    @GetMapping("/members/{id}")
    public ResponseEntity<?> getPersonalDetailsByID(@PathVariable Integer id) {
        return userDetailsService.getPersonalDetailsByID(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/members/update/{id}")
    public ResponseEntity<?> updateUserDetails(@PathVariable Integer id, @RequestBody UpdateUserDetailsDto updateDetailsDto) {
        UpdateUserDetailsDto updated = userDetailsService.updateUserDetails(id, updateDetailsDto);

        return ResponseEntity.ok(updated);
    }

}
