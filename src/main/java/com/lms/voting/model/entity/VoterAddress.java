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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String addressOne;

    private String addressTwo;

    private String townCity;

    private String postcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_info_id", nullable = false)
    private AccountInfo accountInfo;


}
