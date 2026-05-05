package com.lms.voting.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PartyListDto {

    private Integer id;

    @NotBlank(message = "Party name cannot be empty")
    private String partyName;

    @NotBlank(message = "Party position cannot be empty")
    private String position;
}
