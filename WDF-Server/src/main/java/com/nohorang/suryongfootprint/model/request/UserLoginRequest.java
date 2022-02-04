package com.nohorang.suryongfootprint.model.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String userId;
    private String userPw;
}
