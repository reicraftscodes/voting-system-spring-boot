package com.lms.voting.repository;

import com.lms.voting.entity.UserDetails;
import com.lms.voting.entity.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Integer> {

    Optional<Voting> findByUserDetails(UserDetails user);

    List<Voting> findAll();

    @Query(value = "SELECT COUNT(DISTINCT id ) from voting", nativeQuery = true)
    Integer getTotalCountVoter();
}
