package com.leepc.chat;

import com.leepc.chat.service.MessageService;
import com.leepc.chat.util.Constant;
import com.leepc.chat.util.TokenUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatApplicationTests {

    @Autowired
    MessageService ms;

    @Test
    void contextLoads() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1ODQ1MDkwODEsImlkIjoxLCJ1c2VybmFtZSI6ImhhaGEiLCJleHAiOjE1ODQ1MTI2ODF9.dIMYckSCxGH90aiitMRT2GY8Nx_2DPyml0-XIG--N30";
        TokenUtils.parseJWT(token);
    }

    @Test
    void testToken() {
        String token = TokenUtils.createToken(1,"haha", Constant.JWT_TTL);
        System.out.println(token);
        Claims claims = TokenUtils.parseJWT(token);
        System.out.println(claims);

    }

}
