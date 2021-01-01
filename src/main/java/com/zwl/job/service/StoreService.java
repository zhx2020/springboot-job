package com.zwl.job.service;

import com.zwl.job.dao.StoreDao;
import com.zwl.job.entity.Result;
import com.zwl.job.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreDao storeDao;

    public Result insert(Store store) {
        System.out.println(store);
        if (storeDao.selectByPrimaryKey(store) == null) {
            storeDao.insertSelective(store);
        }
        return new Result(true, "OK", "收藏成功");
    }

    public Result findAll() {
        return new Result(true, "OK", storeDao.selectAll());
    }

    public Result del(String id) {
        Store store = new Store();
        store.setJobId(id);
        storeDao.deleteByPrimaryKey(store);
        return new Result(true, "OK", "取消收藏");
    }
}
