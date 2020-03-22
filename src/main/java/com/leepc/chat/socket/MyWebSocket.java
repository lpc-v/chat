package com.leepc.chat.socket;

import com.alibaba.fastjson.JSON;
import com.leepc.chat.domain.Message;
import com.leepc.chat.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/{uid}")
@Component
public class MyWebSocket {

    private static Map<Integer, MyWebSocket> userMap;
    private static int userCount;
    private static MessageService messageService;

    private Session webSocketSession;
    private Integer uid;

    private static Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    @Autowired
    public void setMessageService(MessageService m) {
        MyWebSocket.messageService = m;
    }

    static {
        userMap = new ConcurrentHashMap<>();
        userCount = 0;
    }

    @OnOpen
    public void onOpen(@PathParam(value = "uid") Integer param, Session session){
        this.webSocketSession = session;
        this.uid = param;
        userMap.put(param, this);

        logger.info("" + uid + "连接上了");
        addOnlineCount();
        logger.info("当前在线人数: " + userCount);
    }

    @OnClose
    public void onClose() {
        if (userMap.containsKey(uid)) {
            userMap.remove(uid);
            subOnlineCount();
            logger.info(""+ uid + "断开连接...");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if (!StringUtils.isEmpty(message)) {
            logger.info("收到了消息: " + message);
            Message msg = JSON.parseObject(message, Message.class);
            if (msg.getFromId() == null)
                msg.setFromId(uid);
            Integer to = msg.getToId();
            messageService.insert(msg);
            try {
                userMap.get(to).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendToUser(Integer userId, String message) throws IOException {
        if (userMap.containsKey(userId))
            userMap.get(userId).sendMessage(message);
    }

    public static void sendToAll(String message) throws IOException {
        for (Map.Entry<Integer, MyWebSocket> entry : userMap.entrySet()) {
            Integer k = entry.getKey();
            MyWebSocket v = entry.getValue();
            v.sendMessage(message);
        }
    }

    public void sendMessage(String message) throws IOException {
        logger.info("给" + uid + "发送了消息");
        this.webSocketSession.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return userCount;
    }


    public static synchronized void addOnlineCount() {
        MyWebSocket.userCount++;
    }


    public static synchronized void subOnlineCount() {
        MyWebSocket.userCount--;
    }
}
