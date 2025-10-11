package com.lms.voting.controller;

import com.lms.voting.entity.UserDetails;
import com.lms.voting.service.imp.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// accept requests from the clients or exposes rest api or exposes rest endpoints
// reference: https://spring.io/guides/tutorials/rest

@RestController
@RequestMapping("api/v1/person-details")
public class UserDetailsController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    //    return list of Person details
    @GetMapping
    public List<UserDetails> getPersonalDetails() {
        return userDetailsServiceImpl.getAllPersonalDetails();
    }


    // add multiple users in the personal details records
    @PostMapping(produces = "application/json")
    public ResponseEntity<UserDetails> createUser(@RequestBody UserDetails userDetails) {
        UserDetails addedUserDetails = userDetailsServiceImpl.addPersonalDetails(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUserDetails);
    }


    // retrieve a single user
    @GetMapping("/members/{id}")
    public ResponseEntity<UserDetails> getPersonalDetailsByID(@PathVariable Integer id) {
        return userDetailsServiceImpl.getPersonalDetailsByID(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
