package com.nohorang.suryongfootprint.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CHALLENGE")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int challengeId;

    @Column(name="title", nullable=false)
    private String title;

    @Column(name="info", nullable=false)
    private String info;

    @Column(name="condition", nullable=false)
    private int condition;

    @Column(name="participants", nullable=false)
    private int participants;
}
