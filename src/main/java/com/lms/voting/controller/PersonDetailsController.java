package com.lms.voting.controller;

import com.lms.voting.dto.PersonalDetails;
import com.lms.voting.service.PersonalDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    @GetMapping()
    public List<PersonalDetails> getPersonalDetails() {
        return  personalDetailsService.getPersonalDetails();
    }
}
