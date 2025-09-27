package com.lms.voting.service;

import com.lms.voting.dto.PersonalDetails;
import com.lms.voting.repository.PersonalDetailsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

/***
 * Service annotation
 * This make whole class available as a BEAN, so that basically Spring instantiate it, and
 * then it makes it available to use within other class
 * */
@Service
public class PersonalDetailsService {

    // get the repository
    private final PersonalDetailsRepository personalDetailsRepository;

    public PersonalDetailsService(PersonalDetailsRepository personalDetailsRepository) {
        this.personalDetailsRepository = personalDetailsRepository;
    }

    // get all personal details
    public List<PersonalDetails> getAllPersonalDetails() {
        return personalDetailsRepository.findAll();
    }

    // add personal details
    public PersonalDetails addPersonalDetails(PersonalDetails personalDetails) {
        return personalDetailsRepository.save(personalDetails);
    }

    // retrieve a single user
    public Optional<PersonalDetails> getPersonalDetailsByID(@PathVariable Integer id) {
        return personalDetailsRepository.findById(id);
    }

    // delete a single user
    public void deletePersonalDetailsByID(@PathVariable Integer id) {
        personalDetailsRepository.deleteById(id);
    }


}
