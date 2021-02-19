package com.zwl.job.dao;

import com.zwl.job.entity.Friend;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface FriendDao extends Mapper<Friend> {

    @Insert("insert into tab_friend(user_id,user_name,friend_id,friend_name) values(" +
            "#{userId},#{userName},#{friendId},#{friendName})")
    int addFriend(String userId, String userName, String friendId, String friendName);

    @Delete("delete from tab_friend where user_id = #{userId} and friend_id = #{friendId}")
    int delFriend(String userId, String friendId);
}
