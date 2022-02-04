package com.nohorang.suryongfootprint.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostCreationRequest {
    private byte[] img;
    private String content;
    private int state;
    private String userId;
    private int challengeId;
}
