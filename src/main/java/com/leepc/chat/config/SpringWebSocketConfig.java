package com.leepc.chat.config;

import com.leepc.chat.component.handler.WsHandler;
import com.leepc.chat.component.interceptor.WsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class SpringWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WsHandler wsHandler;

    @Autowired
    private WsInterceptor wsInterceptor;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(wsHandler, "myWs")
                .addInterceptors(wsInterceptor).setAllowedOrigins("*");
    }

}
