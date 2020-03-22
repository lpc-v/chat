package com.leepc.chat.exception;


public class UserNotExistException extends RuntimeException {
    private Integer id;

    public UserNotExistException(Integer id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "user " + id + " not exist!";
    }
}
