package com.lms.voting.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "poll_ref")
public class PollReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numRegister;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_ref_account", nullable = false)
    @JsonIgnore
    private AccountInfo accountInfo;

}
