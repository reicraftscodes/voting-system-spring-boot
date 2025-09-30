package com.lms.voting.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
