package com.lms.voting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonalDetails {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String nationalInsuranceNumber;

}
