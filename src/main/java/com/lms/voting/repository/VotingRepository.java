package com.lms.voting.repository;

import com.lms.voting.dto.PartyList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingRepository extends JpaRepository<PartyList, Integer> {

    PartyList getById(Integer id);
}
