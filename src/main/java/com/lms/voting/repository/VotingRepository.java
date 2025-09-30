package com.lms.voting.repository;

import com.lms.voting.dto.UserDetails;
import com.lms.voting.dto.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Integer> {
    
    Optional<Voting> findByUserDetails(UserDetails user);

}
