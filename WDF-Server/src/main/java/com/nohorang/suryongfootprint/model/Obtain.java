package com.nohorang.suryongfootprint.model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "OBTAIN")
@IdClass(ObtainId.class)
public class Obtain {
    @Id
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="badge_id", nullable=false)
    private Badge badge;

    @Column(name="count", nullable=false)
    private int count;
}
