package com.leepc.chat.service;

import com.leepc.chat.exception.UserNotExistException;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public void hello() {
        throw new RuntimeException();
    }
}
