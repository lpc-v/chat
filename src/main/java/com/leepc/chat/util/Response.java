package com.leepc.chat.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class Response {
    private Integer code;
    private String msg;
    private String error;
    private Map data;

    public static Response build() {
        return new Response();
    }
}
