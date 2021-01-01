package com.zwl.job.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwl.job.dao.JobDao;
import com.zwl.job.dao.JobItemRepository;
import com.zwl.job.entity.Job;
import com.zwl.job.entity.JobItem;
import com.zwl.job.entity.Result;
import com.zwl.job.task.JobPipeline;
import com.zwl.job.task.JobProcessor2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigService {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private JobItemRepository jobItemRepository;

    @Autowired
    private JobPipeline jobPipeline;

    private Spider spider;

    public Result start() {
        spider = Spider.create(new JobProcessor2())
                .addUrl(JobProcessor2.url1, JobProcessor2.url2)
//                .addUrl(JobProcessor2.url3)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                .addPipeline(jobPipeline);
        spider.start();
        System.out.println("爬虫开启");
        return new Result(true, "OK", "爬虫开启");
    }

    public Result stop() {
        if (spider != null) {
            spider.stop();
            spider = null;
        }
        System.out.println("爬虫停止");
        return new Result(true, "OK", "爬虫停止");
    }

    public Result status() {
        if (spider == null) {
            return new Result(true, "OK", "stop");
        } else {
            return new Result(true, "OK", "start");
        }
    }

    public Result sync() {
        int page = 1;
        int num = 0;
        do {
            PageHelper.startPage(page, 500);
            List<Job> jobs = jobDao.selectAll();
            PageInfo<Job> pageInfo = new PageInfo<>(jobs);
            List<JobItem> jobItems = new ArrayList<>();
            for (Job job : jobs) {
                JobItem jobItem = new JobItem();
                BeanUtils.copyProperties(job, jobItem);
                jobItems.add(jobItem);
            }
            System.out.println("size = " + jobItems.size());
            if (jobItems.size() != 0) {
                jobItemRepository.saveAll(jobItems);
            }
            page++;
            num = pageInfo.getSize();
        } while (num == 500);
        int amount = jobDao.deleteByExample(null);
        System.out.println("同步" + amount + "条职位");
        return new Result(true, "OK", "同步" + amount + "条职位");
    }
}
