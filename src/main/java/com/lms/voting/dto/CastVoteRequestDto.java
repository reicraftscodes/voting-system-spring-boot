package com.lms.voting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CastVoteRequestDto {

    @NotBlank(message = "National Insurance Number is required")
    private String nationalInsuranceNumber;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotNull(message = "Party ID is required")
    @Positive(message = "Party ID must be positive")
    private Integer partyId;

}