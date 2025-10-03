package com.lms.voting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetails {

//    this becomes column of databases
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // primary key

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String nationalInsuranceNumber;


}
