package com.lms.voting.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PollReferenceDto {

    private Integer id;

    @NotBlank(message = "Number on register cannot be empty")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Invalid format for number on register")
    private String numRegister;

}
