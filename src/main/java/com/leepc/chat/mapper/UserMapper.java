package com.leepc.chat.mapper;

import com.leepc.chat.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where id=#{id}")
    User findById(Integer id);

    @Select("select * from user where username=#{username}")
    User findByUsername(String username);

    @Insert("insert into user(username,status) values(#{username},#{status})")
    void insert(User user);
}
