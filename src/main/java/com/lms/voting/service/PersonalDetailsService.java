package com.lms.voting.service;

import com.lms.voting.dto.PersonalDetails;
import com.lms.voting.repository.PersonalDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * Service annotation
 * This make whole class available as a BEAN, so that basically Spring instantiate it, and
 * then it makes it available to use within other class
 * */
@Service
public class PersonalDetailsService extends PersonDetailsServiceImp {

    // get the repository
    private final PersonalDetailsRepository personalDetailsRepository;

    // instantiate it
    private PersonalDetailsService(PersonalDetailsRepository personalDetailsRepository) {
        this.personalDetailsRepository = personalDetailsRepository;
    }

    public List<PersonalDetails> getAllPersonalDetails() {
        return personalDetailsRepository.findAll();
    }
}
