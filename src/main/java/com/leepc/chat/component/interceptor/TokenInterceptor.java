package com.leepc.chat.component.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.leepc.chat.annotation.PassToken;
import com.leepc.chat.annotation.TokenRequired;
import com.leepc.chat.exception.ClientException;
import com.leepc.chat.mapper.TokenMapper;
import com.leepc.chat.service.TokenService;
import com.leepc.chat.service.UserService;
import com.leepc.chat.util.TokenUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截了请求");

        //不是请求控制器方法
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        //请求控制器方法
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        //不需要验证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 需要验证
        if (method.isAnnotationPresent(TokenRequired.class)) {
            TokenRequired tokenRequired = method.getAnnotation(TokenRequired.class);
            String token = request.getHeader("token");
            if (tokenRequired.required()) {
                if (StringUtils.isEmpty(token)) {
                    throw new ClientException("there is no token, please login");
                }

                //token不为空
                if (TokenUtils.verify(token)) { //token验证通过
                    //查询是否有对于的用户和token是否在黑名单中
                    Claims claims = TokenUtils.parseJWT(token);
                    Integer tokenUid = (Integer)claims.get("uid");
                    if (userService.existUser(tokenUid) && !tokenService.expired(token))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
