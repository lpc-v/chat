package com.leepc.chat.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * 用于将websocket的消息序列化
 * @auther lpc
 */
@Data
@Accessors(chain = true)
public class WebSocketMessageEntity {
    private Integer fromId;
    private String fromUsername;
    private Integer toId;
    private String toUsername;
    private String message;

    //保留
    private Integer type;
    private Date date;

    public static WebSocketMessageEntity build() {
        return new WebSocketMessageEntity();
    }
}
