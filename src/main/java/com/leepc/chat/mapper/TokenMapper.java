package com.leepc.chat.mapper;

import com.leepc.chat.domain.ExpiredToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TokenMapper {

    @Select("select * from token_expired")
    List<ExpiredToken> findAll();

    @Select("select * from token_expired where id = #{id}")
    ExpiredToken find(Integer id);

    @Insert("insert into token_expired(token) values(#{tokenStr})")
    void insert(String tokenStr);

    @Select("select * from token_expired where token = #{tokenStr}")
    ExpiredToken findByToken(String tokenStr);
}
