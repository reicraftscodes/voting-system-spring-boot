package com.lms.voting.model.dto;

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

    @NotNull(message = "Voter address is required")
    private Integer voterAddressId;

    @NotBlank(message = "National Insurance Number cannot be empty")
    @Pattern(regexp = "^(?!BG|GB|NK|KN|NT|TN|ZZ)[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z]\\d{6}[A-D]$", message = "Invalid National Insurance number format")
    private String nationalInsuranceNumber;

}
