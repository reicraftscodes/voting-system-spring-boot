package com.lms.voting.api.repository;

import com.lms.voting.api.model.entity.VoterAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoterAddressRepository extends JpaRepository<VoterAddress, Integer> {
    // fetches ALL addresses for a given user
    List<VoterAddress> findByAccountInfoId(Integer accountInfoId);

}
