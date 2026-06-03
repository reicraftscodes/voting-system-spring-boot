package com.lms.voting.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VoterAddressDto {

    private Integer id;

    @NotNull(message = "User account ID is required")
    private Integer accountInfoId;

    @NotBlank(message = "Address cannot be empty")
    private String addressOne;

    private String addressTwo;

    private String townCity;

    @NotBlank(message = "Postcode cannot be empty")
    private String postcode;


}
