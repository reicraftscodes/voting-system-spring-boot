package com.lms.voting.api.model.dto;

import com.lms.voting.api.model.entity.VoterAddress;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class UserDetailsDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String nationalInsuranceNumber;
    private List<VoterAddress> voterAddress;

}
