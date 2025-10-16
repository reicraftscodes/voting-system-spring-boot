package com.lms.voting.repository;

import com.lms.voting.entity.UserDetails;
import com.lms.voting.entity.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Long> {

    Optional<Voting> findByUserDetails(UserDetails user);

    List<Voting> findAll();


    /**
     * Uses SQL query to get the total vote count overall.
     * @return returns the total vote count.
     */
    @Query(value = "SELECT COUNT(DISTINCT id ) from voting", nativeQuery = true)
    Integer getTotalCountVoter();

    /**
     * Uses SQL query to get the total count per party list in UK
     * @return returns the total count per party list.
     */
    @Query(value = "SELECT COUNT(u.id) FROM user_details u INNER JOIN voting p ON u.id = p.id WHERE p.party_list = :partyName", nativeQuery = true)
    Long getAllTotalVotersVoteNumberByParty(@Param("partyName") Integer partyId);
}
