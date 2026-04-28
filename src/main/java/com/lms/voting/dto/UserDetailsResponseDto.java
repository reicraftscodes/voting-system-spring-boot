package com.lms.voting.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class UserDetailsResponseDto {

    private UUID id;
    private String firstName;
    private String lastName;
}
