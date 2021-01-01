package com.zwl.job;

import com.zwl.job.dao.JobDao;
import com.zwl.job.entity.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobApplication.class)
public class JobTest {

    @Autowired
    private JobDao jobDao;

    @Test
    public void insert() {
        Job job = new Job();
        job.setJobId("1003");
        job.setJobName("Java开发工程师3");
        jobDao.insert(job);
    }

    @Test
    public void insertSelective() {
        Job job = new Job();
        job.setJobId("1003");
        job.setJobHref("888");
        jobDao.insertSelective(job);
    }

    @Test
    public void update() {
        Job job = new Job();
        job.setJobId("1003");
        job.setJobName("Java开发工程师");
        jobDao.updateByPrimaryKeySelective(job);
    }

    @Test
    public void find() {
        Job param = new Job();
        param.setJobId("1005");
        Job job = jobDao.selectByPrimaryKey(param);
        System.out.println(job);
    }

    @Test
    public void delete() {
        Job param = new Job();
        param.setJobId("1002");
        jobDao.deleteByPrimaryKey(param);
    }
}
