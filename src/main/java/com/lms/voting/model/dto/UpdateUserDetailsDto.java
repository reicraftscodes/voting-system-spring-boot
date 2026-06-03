package com.lms.voting.model.dto;

import com.lms.voting.model.entity.VoterAddress;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UpdateUserDetailsDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String nationalInsuranceNumber;
    private VoterAddress voterAddress;

}
