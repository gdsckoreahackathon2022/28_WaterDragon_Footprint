package com.nohorang.suryongfootprint.model.request;
import lombok.Data;

@Data
public class UserCreationRequest {
    private String userId;
    private String userEmail;
    private String userPw;
    private String userName;
    private String userNickname;
}
