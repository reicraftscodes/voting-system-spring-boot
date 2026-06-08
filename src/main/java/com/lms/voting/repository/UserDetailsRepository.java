package com.lms.voting.repository;

import com.lms.voting.model.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<AccountInfo, Integer> {

    void deleteById(Integer id);

    boolean existsByNationalInsuranceNumber(String nationalInsuranceNumber);

    Optional<AccountInfo> findByNationalInsuranceNumberAndLastName(String nationalInsuranceNumber, String lastName);
}
