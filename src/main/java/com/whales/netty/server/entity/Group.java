package com.whales.netty.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    // 聊天室名称
    private String name;
    // 聊天室成员
    private Set<String> members;
}
