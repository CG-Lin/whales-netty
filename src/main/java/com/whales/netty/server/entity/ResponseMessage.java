package com.whales.netty.server.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ResponseMessage {
    private boolean success;
    private String reason;

    public ResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
}
