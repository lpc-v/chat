package com.leepc.chat.service;

import com.leepc.chat.domain.User;
import com.leepc.chat.exception.ClientException;
import com.leepc.chat.mapper.TokenMapper;
import com.leepc.chat.mapper.UserMapper;
import com.leepc.chat.util.Constant;
import com.leepc.chat.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenMapper tokenMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> findAll(){
        return this.userMapper.findAll();
    }

    public User findById(Integer id){
        return userMapper.findById(id);
    }

    public User findByUsername(String username){
        return userMapper.findByUsername(username);
    }

    public void insert(User user){
        userMapper.insert(user);
    }

    public String login(String username, String password) {
        if (verifyPassword(username, password)) {
            Integer uid = getIdByUsername(username);
             return TokenUtils.createToken(uid, username, Constant.JWT_TTL);
        }
         return null;
    }

    public boolean verifyPassword(String username, String password) {
        User dbUser = userMapper.findByUsername(username);
        if (dbUser == null)
            return false;
        if (!dbUser.getPassword().equals(password))
            return false;
        return true;
    }

    public Integer getIdByUsername(String username) {
        User dbUser = userMapper.findByUsername(username);
        return dbUser.getId();
    }

    public boolean existUser(Integer uid) {
        User dbUser = userMapper.findById(uid);
        if (dbUser == null)
            return true;
        return false;
    }

    public boolean logout(String token) {
        if (!TokenUtils.verify(token))
            throw new ClientException("该用户尚未登陆");
        tokenMapper.insert(token);
        return true;
    }
}
