package com.lms.voting.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
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
