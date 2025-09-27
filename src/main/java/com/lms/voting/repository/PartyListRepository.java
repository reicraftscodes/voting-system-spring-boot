package com.lms.voting.repository;

import com.lms.voting.dto.PartyList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyListRepository extends JpaRepository<PartyList, Integer> {

    List<PartyList> findPartyListById(Integer id);

    void deleteById(Integer id);
}
