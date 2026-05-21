package com.lms.voting.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "voter_address")
public class VoterAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String addressOne;

    private String addressTwo;

    private String townCity;

    private String postcode;

    @ManyToOne
    @JoinColumn(name = "constituency_id")
    private Constituency constituency;


}
