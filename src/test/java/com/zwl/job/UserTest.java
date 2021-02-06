package com.zwl.job;

import com.zwl.job.dao.UserDao;
import com.zwl.job.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void test01() {
        User user = userDao.selectByPrimaryKey("13283457");
        System.out.println(user);
    }

    @Test
    public void test02() {
        List<User> list = userDao.getAllUsers();
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void test03() {
        List<User> list = userDao.findAll();
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void test04() {
        User user = userDao.findById("13283457");
        System.out.println(user);
    }
}
