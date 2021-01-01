package com.zwl.job.task;

import com.zwl.job.entity.Job;
import com.zwl.job.service.JobService;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;
import java.util.List;

@Component
public class JobPipeline implements Pipeline {

    @Autowired
    private JobService jobService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        /*List<Job> jobList = resultItems.get("job_list");
        if (jobList != null) {
            System.out.println("收到 " + jobList.size() + "条数据");
            for (Job job : jobList) {
                jobService.insert(job);
            }
        }*/
        Job job = resultItems.get("job");
        if (job != null) {
            System.out.println("get a data");
            jobService.insert(job);
            /*CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/my/broadcast?str=" + job.getJobName());
            try {
                httpclient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
}
