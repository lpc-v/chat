package com.leepc.chat.service;

import com.alibaba.fastjson.JSON;
import com.leepc.chat.component.handler.WsHandler;
import com.leepc.chat.domain.Message;
import com.leepc.chat.domain.WebSocketMessageEntity;
import com.leepc.chat.mapper.MessageMapper;
import com.leepc.chat.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.MessageUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebSocketService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageService messageService;

    public void sendToUser(Integer uid, String message) throws IOException {
        WebSocketMessageEntity entity = JSON.parseObject(message, WebSocketMessageEntity.class);
        WsHandler.sendToUser(uid, entity);
    }

    public List<WebSocketMessageEntity> getMessageList(String token) {
        Integer toUserId = TokenUtils.getUserId(token);
        List<Message> messages = messageMapper.findByToUserId(toUserId);
        List<WebSocketMessageEntity> entities = new ArrayList<>();
        for (Message message : messages) {
            entities.add(messageService.toWebSocketEntity(message));
        }
        return entities;
    }
}
