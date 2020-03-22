package com.leepc.chat.domain;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Message {

    private Integer id;
    private Integer fromId;
    private Integer toId;
    private String content;
    private Date createTime;

}
