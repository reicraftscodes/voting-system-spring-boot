package com.lms.voting.repository;

import com.lms.voting.entity.PartyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyListRepository extends JpaRepository<PartyList, Integer> {

    void deleteById(Integer id);

    PartyList getById(Integer id);

}
