package com.leepc.chat.controller;

import com.alibaba.druid.util.StringUtils;
import com.leepc.chat.annotation.PassToken;
import com.leepc.chat.exception.ClientException;
import com.leepc.chat.service.UserService;
import com.leepc.chat.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/login")
    @ResponseBody
    @PassToken
    public Response login(@RequestParam("username") @NotNull String username, @RequestParam("password") @NotNull String password) {
        Response response = new Response();
        String token = service.login(username, password);
        if (!StringUtils.isEmpty(token)) {
            Map data = new HashMap();
            data.put("token", token);
            return response.setCode(HttpStatus.OK.value()).setData(data).setMsg("登录成功");
        }
        throw new ClientException("用户名或密码错误");
    }

    @PostMapping("/logout")
    @ResponseBody
    @PassToken
    public Response logout(@RequestHeader("token") @NotNull String token) {
        service.logout(token);
        return Response.build().setCode(200).setMsg("退出成功");
    }
}
