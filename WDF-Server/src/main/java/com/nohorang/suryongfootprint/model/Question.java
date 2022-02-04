package com.nohorang.suryongfootprint.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "QUESTION")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int question_id;

    @Column(name="content", nullable=false)
    private String content;

    @Column(name="date", nullable=false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
}
