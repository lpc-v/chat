package com.leepc.chat.mapper;

import com.leepc.chat.domain.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {

    @Select("select * from message")
    List<Message> findAll();

    @Select("select * from message where id=#{id}")
    Message findById(Integer id);


    @Insert("insert into message(from_id,to_id,content,create_time) values(#{fromId},#{toId},#{content},#{createTime})")
    void insert(Message message);

    @Delete("delete from message where id = #{id}")
    void delete(Integer id);

    @Select("select * from message where to_id = #{userId}")
    List<Message> findByToUserId(Integer userId);
}
