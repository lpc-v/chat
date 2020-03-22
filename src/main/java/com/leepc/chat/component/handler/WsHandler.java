package com.leepc.chat.component.handler;

import com.alibaba.fastjson.JSON;
import com.leepc.chat.domain.Message;
import com.leepc.chat.domain.WebSocketMessageEntity;
import com.leepc.chat.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class WsHandler extends AbstractWebSocketHandler {

    @Autowired
    private static MessageService messageService;

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()){
            session.close();
            WsSessionManager.remove(session);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        String query = session.getUri().getQuery();
//        int begin = query.indexOf("token=");
//        String token = query.substring(begin+6);
//        Integer uid = TokenUtils.getUserId(token);
        Integer uid = (Integer)session.getAttributes().get("uid");
        WsSessionManager.add(uid, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        WebSocketMessageEntity entity = JSON.parseObject(message.getPayload(), WebSocketMessageEntity.class);
        Integer toUserId = entity.getToId();
        Integer fromUserId = entity.getFromId();
        System.out.println(message.getPayload());
        System.out.println(entity);
        log.info(fromUserId + "发送消息给: " + toUserId);
        sendToUser(toUserId, entity);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WsSessionManager.remove(session);
    }

    public static void sendToUser(Integer toUserId, WebSocketMessageEntity entity) throws IOException {
        WebSocketSession session = WsSessionManager.getSession(toUserId);
        if (session == null){
            log.info("用户不在线");
            Message dbMessage = new Message();
            dbMessage.setFromId(entity.getFromId());
            dbMessage.setToId(entity.getToId());
            dbMessage.setContent(entity.getMessage());
            dbMessage.setCreateTime(new Date());
            messageService.insert(dbMessage);
            return;
        }
        if (!session.isOpen()){
            log.error("无法连接: "+ session.getAttributes().get("uid"));
            return;
        }
        WebSocketMessage message = new TextMessage(entity.toString().getBytes());
        session.sendMessage(message);
    }

}
