package com.lms.voting.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class UserDetailsRequestDto {

    private Integer id;

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dateOfBirth;

    @NotBlank(message = "National Insurance Number cannot be empty")
    @Pattern(regexp = "[A-Z]{2}\\d{6}[A-D]", message = "Invalid NI number format")
    @Column(unique = true)
    private String nationalInsuranceNumber;
}
