package com.lms.voting.api.repository;

import com.lms.voting.api.model.entity.PollReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollReferenceRepository extends JpaRepository<PollReference, Integer> {

    long countByAccountInfoId(Integer accountId);
}
