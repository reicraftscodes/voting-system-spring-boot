package com.lms.voting.api.model.dto;

import com.lms.voting.api.model.entity.VoterAddress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "First name cannot be empty")
    @NotNull(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @NotNull(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "Date of birth cannot be empty")
    @NotNull(message = "Date of birth cannot be empty")
    private LocalDate dateOfBirth;

    @NotBlank(message = "National Insurance Number cannot be empty")
    @NotNull(message = "National Insurance Number cannot be empty")
    private String nationalInsuranceNumber;

    private List<VoterAddress> voterAddress;

}
