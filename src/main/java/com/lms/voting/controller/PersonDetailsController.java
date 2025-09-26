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
@RestController
@RequestMapping("api/v1/person-details")
public class PersonDetailsController {

    private final PersonalDetailsService personalDetailsService;
    private final PersonalDetailsRepository personalDetailsRepository;

    public PersonDetailsController(PersonalDetailsService personalDetailsService, PersonalDetailsRepository personalDetailsRepository) {
        this.personalDetailsService = personalDetailsService;
        this.personalDetailsRepository = personalDetailsRepository;
    }


    //    return list of Person details
    @GetMapping
    public List<PersonalDetails> getPersonalDetails() {
        return personalDetailsService.getAllPersonalDetails();
    }


    @PostMapping(produces = "application/json")
    public ResponseEntity<PersonalDetails> add(@RequestBody PersonalDetails personalDetails) {
        PersonalDetails addedPersonalDetails = personalDetailsService.addPersonalDetails(personalDetails);
        return new ResponseEntity<>(addedPersonalDetails, HttpStatus.CREATED);
    }


    // retrieve a single user
    @GetMapping("/members/{id}")
     public ResponseEntity<Optional<PersonalDetails>> getPersonalDetailsByID(@PathVariable Integer id) {
        Optional<PersonalDetails> personalDetails = personalDetailsRepository.findById(id);
        return new ResponseEntity<>(personalDetails,HttpStatus.OK );
    }

    // delete a single user

}
