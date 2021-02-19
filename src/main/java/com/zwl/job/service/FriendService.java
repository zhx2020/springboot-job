package com.zwl.job.service;

import com.zwl.job.dao.FriendDao;
import com.zwl.job.entity.Friend;
import com.zwl.job.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    public List<Friend> findAll() {
        return friendDao.selectAll();
    }

    public Result add(Friend friend) {
        System.out.println(friend);
        if (friendDao.selectByPrimaryKey(friend) == null) {
            System.out.println("不是好友");
            friendDao.addFriend(friend.getUserId(), friend.getUserName(), friend.getFriendId(), friend.getFriendName());
            friendDao.addFriend(friend.getFriendId(), friend.getFriendName(), friend.getUserId(), friend.getUserName());
            return new Result(true, "添加成功");
        } else {
            return new Result(false, "添加失败");
        }
    }

    public Result del(String userId, String friendId) {
        friendDao.delFriend(userId, friendId);
        friendDao.delFriend(friendId, userId);
        return new Result(true, "删除成功");
    }
}
