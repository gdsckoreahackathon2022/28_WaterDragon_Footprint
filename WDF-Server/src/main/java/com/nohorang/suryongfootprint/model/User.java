package com.nohorang.suryongfootprint.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "USER")
public class User {
    @Id
    @Column(name="user_id", nullable=false, length=20)
    private String userId;

    @Column(name="user_email", nullable=false, length=30)
    private String userEmail;

    @Column(name="user_pw", nullable=false, length=20)
    private String userPw;

    @Column(name="user_name", nullable=false, length=10)
    private String userName;

    @Column(name="user_nickname", nullable=false, length=10)
    private String userNickname;

    //User의 Badge (1:N 관계 메핑)
    //User의 Post (1:N 관계 매핑)
    //User의 Count (1:N 관계 매핑)
}
