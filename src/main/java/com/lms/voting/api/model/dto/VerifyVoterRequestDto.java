package com.lms.voting.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VerifyVoterRequestDto {

    @NotBlank(message = "National Insurance Number is required")
    private String nationalInsuranceNumber;

    @NotBlank(message = "Last Name is required")
    private String lastName;

}