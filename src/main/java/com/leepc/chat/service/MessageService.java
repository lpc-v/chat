package com.leepc.chat.service;

import com.leepc.chat.domain.Message;
import com.leepc.chat.domain.WebSocketMessageEntity;
import com.leepc.chat.mapper.MessageMapper;
import com.leepc.chat.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    public void setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public List<Message> findAll() {
        return messageMapper.findAll();
    }

    public Message findById(Integer id) {
        return messageMapper.findById(id);
    }

    public void insert(Message message) {
        messageMapper.insert(message);
    }

    public WebSocketMessageEntity toWebSocketEntity(Message message) {
        return WebSocketMessageEntity.build()
                .setFromUsername(userMapper.findById(message.getFromId()).getUsername())
                .setFromId(message.getFromId())
                .setToId(message.getToId())
                .setToUsername(userMapper.findById(message.getToId()).getUsername())
                .setMessage(message.getContent());
    }
}
