package com.lms.voting.repository;

import com.lms.voting.dto.PersonalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, Integer> {

    Optional<PersonalDetails> findById(Integer id);

}
