package com.zwl.job.service;

import com.zwl.job.dao.StoreDao;
import com.zwl.job.entity.Result;
import com.zwl.job.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    private StoreDao storeDao;

    public Result insert(Store store) {
        System.out.println(store);
        if (storeDao.selectByPrimaryKey(store) == null) {
            storeDao.insertSelective(store);
            return new Result(true, "OK", "收藏成功");
        } else {
            return new Result(true, "OK", "已经收藏");
        }
    }

    public Result findAll() {
        return new Result(true, "OK", storeDao.selectAll());
    }

    public Result findById(String id) {
        return new Result(true, "OK", storeDao.findAllByUserId(id));
    }

    public Result findByIdWithPage(String id, Integer page, Integer size) {
        return new Result(true, "OK", storeDao.findAllByUserIdWithPage(id, (page - 1) * size, size));
    }

    public Result del(String userId, String jobId) {
        Store store = new Store();
        store.setUserId(userId);
        store.setJobId(jobId);
        storeDao.deleteByPrimaryKey(store);
        return new Result(true, "取消收藏");
    }
}
