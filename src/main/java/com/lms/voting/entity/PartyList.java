package com.lms.voting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Entity
@ToString
@Table(name = "party_list")
public class PartyList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Party name cannot be empty")
    private String partyName;

    @NotBlank(message = "Party position cannot be empty")
    private String position;
}
