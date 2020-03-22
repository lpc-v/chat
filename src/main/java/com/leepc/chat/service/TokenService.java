package com.leepc.chat.service;

import com.leepc.chat.domain.ExpiredToken;
import com.leepc.chat.mapper.TokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    TokenMapper tokenMapper;
    public boolean expired(String token) {
        ExpiredToken expiredToken = tokenMapper.findByToken(token);
        if (expiredToken != null)
            return false;
        return true;
    }
}
