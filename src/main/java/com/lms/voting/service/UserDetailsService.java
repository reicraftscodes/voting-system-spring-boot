package com.lms.voting.service;

import com.lms.voting.entity.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserDetailsService {

    List<UserDetails> getAllPersonalDetails();

    UserDetails addPersonalDetails(UserDetails userDetails);

    Optional<UserDetails> getPersonalDetailsByID(Integer id);

}
