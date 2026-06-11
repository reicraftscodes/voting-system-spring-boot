package com.lms.voting.api.repository;

import com.lms.voting.api.model.entity.AccountInfo;
import com.lms.voting.api.model.entity.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Long> {

    Optional<Voting> findByUserDetails(AccountInfo user);

//    List<VotingReceiptDto> findVotingByReferenceNo();

    @Query(value = "SELECT COUNT(DISTINCT id ) from voting", nativeQuery = true)
    Long getTotalCountVoter();


    @Query(value = "SELECT COUNT(u.id) FROM user_details u INNER JOIN voting p ON u.id = p.id WHERE p.party_list = :partyName", nativeQuery = true)
    Long getAllTotalVotersVoteNumberByParty(@Param("partyName") Integer partyName);
}
