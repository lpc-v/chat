package com.leepc.chat.controller;

import com.leepc.chat.exception.ClientException;
import com.leepc.chat.exception.UserNotExistException;
import com.leepc.chat.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response handleUserNotExistException(UserNotExistException e) {
        Response response = new Response();
        return response.setCode(401).setError("user not found");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response handleValidateException(MissingServletRequestParameterException e) {
        return Response.build().setCode(400).setError(e.getMessage());
    }

    @ExceptionHandler(ClientException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response handleClientException(ClientException e) {
        return Response.build().setError(e.getMessage()).setCode(400);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleException(Exception e) {
        Response response = new Response();
        return response.setCode(500).setError("error");
    }

}
