package com.lms.voting.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;


@Getter
@Setter
@ToString
@Entity
@Table(name = "user_details")
public class AccountInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nationalInsuranceNumber;

    private LocalDate dateOfBirth;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "accountInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VoterAddress> voterAddresses;

    @OneToMany(mappedBy = "accountInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PollReference> pollReferences;

    @CreatedBy
    private LocalDate localDate;


}
