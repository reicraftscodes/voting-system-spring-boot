package com.lms.voting.controller;

import com.lms.voting.dto.PersonalDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

// accept requests from the clients or exposes rest api or exposes rest endpoints
@RestController
@RequestMapping("api/v1/person-details")
public class PersonDetailsController {

    //    return list of Person details
    @GetMapping()
    public List<PersonalDetails> getPersonalDetails() {
        return List.of(new PersonalDetails(1, "May", "Sanejo", LocalDate.of(2000, 3, 20), "SL82NDAH"),
                new PersonalDetails(2, "John", "Brix", LocalDate.of(200, 1, 12), "SL7FHN12"));
    }
}
