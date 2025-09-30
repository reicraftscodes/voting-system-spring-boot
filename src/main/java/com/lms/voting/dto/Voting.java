package com.lms.voting.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Voting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String referenceNo;

    @Transient
    private UserDetails userDetails;

    @ManyToOne
    @JoinColumn(name = "party_list_id")
    private PartyList partyList;

}
