package com.lms.voting.controller;

import com.lms.voting.dto.PersonalDetails;
import com.lms.voting.repository.PersonalDetailsRepository;
import com.lms.voting.service.PersonalDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


// accept requests from the clients or exposes rest api or exposes rest endpoints
// reference: https://spring.io/guides/tutorials/rest

@RestController
@RequestMapping("api/v1/person-details")
public class PersonDetailsController {

    private final PersonalDetailsService personalDetailsService;

    public PersonDetailsController(PersonalDetailsService personalDetailsService) {
        this.personalDetailsService = personalDetailsService;
    }


    //    return list of Person details
    @GetMapping
    public List<PersonalDetails> getPersonalDetails() {
        return personalDetailsService.getAllPersonalDetails();
    }


    // add multiple users in the personal details records
    @PostMapping(produces = "application/json")
    public ResponseEntity<PersonalDetails> add(@RequestBody PersonalDetails personalDetails) {
        PersonalDetails addedPersonalDetails = personalDetailsService.addPersonalDetails(personalDetails);
        return new ResponseEntity<>(addedPersonalDetails, HttpStatus.CREATED);
    }


    // retrieve a single user
    @GetMapping("/members/{id}")
    public ResponseEntity<Optional<PersonalDetails>> getPersonalDetailsByID(@PathVariable Integer id) {
        Optional<PersonalDetails> personalDetails = personalDetailsService.getPersonalDetailsByID(id);
        return new ResponseEntity<>(personalDetails, HttpStatus.OK);
    }

    // delete a single user
    @DeleteMapping("/members/{id}")
    public ResponseEntity<PersonalDetails> deletePersonalDetailsByID(@PathVariable Integer id) {
        Optional<PersonalDetails> existingId = personalDetailsService.getPersonalDetailsByID(id);
        if (existingId.isEmpty()) {
            personalDetailsService.deletePersonalDetailsByID(id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
