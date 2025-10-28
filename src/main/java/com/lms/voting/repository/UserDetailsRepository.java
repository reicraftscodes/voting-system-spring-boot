package com.lms.voting.repository;

import com.lms.voting.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {

    @Override
    Optional<UserDetails> findById(Integer id);

    void deleteById(Integer id);

    boolean existsByNationalInsuranceNumber(String nationalInsuranceNumber);
}
