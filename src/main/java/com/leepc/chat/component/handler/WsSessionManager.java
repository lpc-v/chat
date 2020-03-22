package com.leepc.chat.component.handler;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WsSessionManager {

    private static final Map<Integer, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();
    private static final Map<WebSocketSession, Integer> ID_POOL = new ConcurrentHashMap<>();

    public static synchronized void add(Integer id,WebSocketSession session) {
        SESSION_POOL.put(id, session);
        ID_POOL.put(session, id);
    }

    public static synchronized WebSocketSession remove(Integer id) {
        WebSocketSession session = SESSION_POOL.remove(id);
        ID_POOL.remove(session);
        return session;
    }

    public static synchronized WebSocketSession remove(WebSocketSession session) {
        Integer id = ID_POOL.remove(session);
        SESSION_POOL.remove(id);
        return session;
    }

    public static synchronized void removeAndClose(Integer id) {
        WebSocketSession ws = SESSION_POOL.remove(id);
        ID_POOL.remove(ws);
        try {
            ws.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized WebSocketSession getSession(Integer id) {
        return SESSION_POOL.get(id);
    }

    public static synchronized Integer getId(WebSocketSession session) {
        return ID_POOL.get(session);
    }
}
