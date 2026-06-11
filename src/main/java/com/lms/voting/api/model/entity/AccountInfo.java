package com.lms.voting.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@ToString
@Entity
@Table(name = "user_details")
public class AccountInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nationalInsuranceNumber;

    private LocalDate dateOfBirth;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "accountInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VoterAddress> voterAddresses;


}
