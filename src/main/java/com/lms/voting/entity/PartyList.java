package com.lms.voting.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "party_list")
public class PartyList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String partyName;
    private String position;
}
