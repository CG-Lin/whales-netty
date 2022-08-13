package com.whales.netty.server.message;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString(callSuper = true)
public class GroupCreateMessage {
    private String groupName;
    private Set<String> members;

    public GroupCreateMessage(String groupName, Set<String> members) {
        this.groupName = groupName;
        this.members = members;
    }
}
