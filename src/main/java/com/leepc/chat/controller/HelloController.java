package com.leepc.chat.controller;

import com.leepc.chat.annotation.PassToken;
import com.leepc.chat.annotation.TokenRequired;
import com.leepc.chat.service.HelloService;
import com.leepc.chat.util.Response;
import com.leepc.chat.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
public class HelloController {

    @Autowired
    HelloService service;

    @RequestMapping("/hello")
    @ResponseBody
    @PassToken
    public String Hello(){
        return "hello";
    }

    @ResponseBody
    @TokenRequired
    @RequestMapping("testToken")
    public Response testToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        Integer id = TokenUtils.getUserId(token);
        Map map = new HashMap();
        map.put("tokenId", id);
        return Response.build().setCode(200).setMsg("success").setData(map);
    }

    @ResponseBody
    @PassToken
    @RequestMapping("notoken")
    public Response noToken() {
        return Response.build().setCode(200).setMsg("success");
    }
}
