package com.lms.voting.api.repository;

import com.lms.voting.api.model.entity.PartyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyListRepository extends JpaRepository<PartyList, Integer> {

    void deleteById(Integer id);

    PartyList getById(Integer id);

    boolean existsByPartyName(String partyName);

    @Query(value = "SELECT party_name FROM party_list", nativeQuery = true)
    List<String> findAllPartyNames();

}
