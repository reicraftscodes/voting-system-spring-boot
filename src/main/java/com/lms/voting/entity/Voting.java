package com.lms.voting.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "voting")
public class Voting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String referenceNo;

    @ManyToOne
    @JoinColumn(name = "user_details")
    private UserDetails userDetails;

    @ManyToOne
    @JoinColumn(name = "party_list")
    private PartyList partyList;

}
