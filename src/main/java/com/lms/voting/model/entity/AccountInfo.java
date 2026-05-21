package com.lms.voting.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


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

    @ManyToOne
    @JoinColumn(name = "voter_address_id")
    private VoterAddress voterAddress;


}
