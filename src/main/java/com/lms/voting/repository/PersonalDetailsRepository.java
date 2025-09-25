package com.lms.voting.repository;

import com.lms.voting.dto.PersonalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalDetailsRepository
        extends JpaRepository<PersonalDetails, Integer> {

}
