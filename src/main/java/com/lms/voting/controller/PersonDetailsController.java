package com.lms.voting.controller;

import com.lms.voting.dto.PersonalDetails;
import com.lms.voting.service.PersonalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// accept requests from the clients or exposes rest api or exposes rest endpoints
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


    @PostMapping(produces = "application/json")
    public ResponseEntity<PersonalDetails> add(@RequestBody PersonalDetails personalDetails) {
        PersonalDetails addedPersonalDetails = personalDetailsService.addPersonalDetails(personalDetails);
        return new ResponseEntity<>(addedPersonalDetails, HttpStatus.CREATED);

    }
}
