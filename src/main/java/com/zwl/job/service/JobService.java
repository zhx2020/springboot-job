package com.zwl.job.service;

import com.zwl.job.dao.JobDao;
import com.zwl.job.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobDao jobDao;

    public void insert(Job job) {
        if (jobDao.selectByPrimaryKey(job) == null) {
            jobDao.insertSelective(job);
        } else {
            jobDao.updateByPrimaryKeySelective(job);
        }
    }
}
