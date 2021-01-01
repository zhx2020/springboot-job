package com.zwl.job;

import com.zwl.job.dao.StoreDao;
import com.zwl.job.entity.Store;
import com.zwl.job.service.StoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoreTest {

    @Autowired
    private StoreService storeService;

    @Autowired
    protected StoreDao storeDao;

    @Test
    public void insert() {
        Store store = new Store();
        store.setJobId("111");
        Date date = new Date();
        System.out.println(date);
        store.setCreateTime(date);
        storeService.insert(store);
    }

    @Test
    public void hello() {
        Store store = new Store();
        store.setJobId("13283457");
        Date date = new Date();
        store.setCreateTime(date);
        Store res = storeDao.selectByPrimaryKey(store);
        System.out.println(res);
    }
}
