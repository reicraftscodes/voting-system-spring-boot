package com.lms.voting.controller;

import com.lms.voting.dto.UserDetails;
import com.lms.voting.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


// accept requests from the clients or exposes rest api or exposes rest endpoints
// reference: https://spring.io/guides/tutorials/rest

@RestController
@RequestMapping("api/v1/person-details")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    //    return list of Person details
    @GetMapping
    public List<UserDetails> getPersonalDetails() {
        return userDetailsService.getAllPersonalDetails();
    }


    // add multiple users in the personal details records
    @PostMapping(produces = "application/json")
    public ResponseEntity<UserDetails> createUser(@RequestBody UserDetails userDetails) {
        UserDetails addedUserDetails = userDetailsService.addPersonalDetails(userDetails);
        return new ResponseEntity<>(addedUserDetails, HttpStatus.CREATED);
    }


    // retrieve a single user
    @GetMapping("/members/{id}")
    public ResponseEntity<Optional<UserDetails>> getPersonalDetailsByID(@PathVariable Integer id) {
        Optional<UserDetails> personalDetails = userDetailsService.getPersonalDetailsByID(id);
        return new ResponseEntity<>(personalDetails, HttpStatus.OK);
    }

    // delete a single user
    @DeleteMapping("/members/{id}")
    public ResponseEntity<UserDetails> deletePersonalDetailsByID(@PathVariable Integer id) {
        Optional<UserDetails> existingId = userDetailsService.getPersonalDetailsByID(id);
        if (existingId.isEmpty()) {
            userDetailsService.deletePersonalDetailsByID(id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
