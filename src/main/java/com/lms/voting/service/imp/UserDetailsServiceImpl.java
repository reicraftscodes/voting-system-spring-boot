package com.lms.voting.service.imp;

import com.lms.voting.entity.UserDetails;
import com.lms.voting.repository.UserDetailsRepository;
import com.lms.voting.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/***
 * Service annotation
 * This make whole class available as a BEAN, so that basically Spring instantiate it, and
 * then it makes it available to use within other class
 * */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // get the repository
    @Autowired
    private UserDetailsRepository personalDetailsRepository;


    // get all personal details
    public List<UserDetails> getAllPersonalDetails() {
        return personalDetailsRepository.findAll();
    }

    // add personal details
    public UserDetails addPersonalDetails(UserDetails userDetails) {
        return personalDetailsRepository.save(userDetails);
    }

    // retrieve a single user
    public Optional<UserDetails> getPersonalDetailsByID(Integer id) {
        return personalDetailsRepository.findById(id);
    }

    // delete a single user
    public void deletePersonalDetailsByID(Integer id) {
        personalDetailsRepository.deleteById(id);
    }


}
