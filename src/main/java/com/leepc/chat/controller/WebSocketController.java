package com.leepc.chat.controller;

import com.leepc.chat.annotation.TokenRequired;
import com.leepc.chat.domain.WebSocketMessageEntity;
import com.leepc.chat.service.WebSocketService;
import com.leepc.chat.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    @PostMapping("/sendToUser")
    @ResponseBody
    @TokenRequired
    public Response sendToUser(@RequestParam("toUserId") @NotNull Integer uid, @RequestParam("message") String message) {
        try {
            webSocketService.sendToUser(uid, message);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return Response.build().setCode(200).setMsg("发送成功");
    }

    @PostMapping("/messages")
    @ResponseBody
    @TokenRequired
    public Response getMessageList(@RequestHeader("token") String token) {
        List<WebSocketMessageEntity> entities = webSocketService.getMessageList(token);
        Map map = new HashMap();
        map.put("message", entities);
        return Response.build().setCode(200).setData(map);
    }
}
