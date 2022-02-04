package com.nohorang.suryongfootprint.model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "BADGE")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int badge_id;

    @Column(name="name", nullable=false)
    private String name;
}
