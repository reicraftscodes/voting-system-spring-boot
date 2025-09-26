package com.lms.voting.service;

import com.lms.voting.dto.PersonalDetails;
import com.lms.voting.repository.PersonalDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * Service annotation
 * This make whole class available as a BEAN, so that basically Spring instantiate it, and
 * then it makes it available to use within other class
 * */
@Service
public class PersonalDetailsService{

    // get the repository
    @Autowired
    private final PersonalDetailsRepository personalDetailsRepository;

    // instantiate it
    private PersonalDetailsService(PersonalDetailsRepository personalDetailsRepository) {
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

    // Todo: retrieve a single user

    // Todo: delete a single user


}
