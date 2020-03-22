package com.leepc.chat.component.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.leepc.chat.exception.ClientException;
import com.leepc.chat.service.UserService;
import com.leepc.chat.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@Slf4j
public class WsInterceptor implements HandshakeInterceptor {
    @Autowired
    UserService userService;
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        log.info("before handshake...");
        HttpServletRequest request = ((ServletServerHttpRequest)serverHttpRequest).getServletRequest();
        String token = request.getParameter("token");

        if (StringUtils.isEmpty(token))
            return false;
        map.put("uid", TokenUtils.getUserId(token));
        return true;
//        return userService.existUser(TokenUtils.getUserId(token));
//        throw new ClientException("no token");
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        log.info("after handshake...");
    }
}
