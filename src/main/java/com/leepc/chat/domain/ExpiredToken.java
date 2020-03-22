package com.leepc.chat.domain;

import lombok.Data;

@Data
public class ExpiredToken {
    private Integer id;
    private String token;
}
