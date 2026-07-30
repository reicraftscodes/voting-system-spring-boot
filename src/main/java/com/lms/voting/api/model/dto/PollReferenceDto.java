package com.lms.voting.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PollReferenceDto {

    private Integer id;

    @NotBlank(message = "Number on register cannot be empty")
    @NotNull(message = "Number on register cannot be empty @Notnull")
    private String numRegister;

}
