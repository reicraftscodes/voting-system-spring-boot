package com.lms.voting.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoterVerificationResponseDto {

    private Integer accountId;

    private String firstName;

    private String lastName;

    private String nationalInsuranceNumber;

    private LocalDate dateOfBirth;

    private List<PartyListDto> parties;
}