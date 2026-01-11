package com.lms.voting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CastVoteRequest {

    @NotBlank(message = "National Insurance Number is required")
    private String nationalInsuranceNumber;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotNull(message = "Party ID is required")
    @Positive(message = "Party ID must be positive")
    private Integer partyId;

    // Generate random reference number for voting receipt
    public String generateRandomReceiptNumbers() {
        Random random = new Random();

        StringBuilder referenceNo = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            referenceNo.append(random.nextInt(10));
        }

        return referenceNo.toString();
    }
}