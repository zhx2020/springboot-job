package com.zwl.job.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zwl.job.entity.Job;
import com.zwl.job.utils.MyUtil;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JobProcessor1 implements PageProcessor {

    private Site site = Site.me().setCharset("gbk")
            .setTimeOut(10 * 1000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3);

    private String url = "https://search.51job.com/list/000000,000000,0121%252c7201%252c7701%252c2707%252c7901,00,9,99,+,2,1.html?lang=c&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&ord_field=0&dibiaoid=0&line=&welfare=";

    @Autowired
    private JobPipeline jobPipeline;

    @Override
    public void process(Page page) {
        System.err.println(page.getRequest().getUrl());
        if (page.getRequest().getUrl().contains("search.51job.com")) {
            List<Selectable> list = page.getHtml().css("script[type=\"text/javascript\"]").nodes();
            String script = list.get(2).toString();
            int l = script.indexOf("{");
            int r = script.lastIndexOf("}");
            String json = script.substring(l, r + 1);
            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("engine_search_result");
            List<Job> jobList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String jobId = object.getString("jobid");
                String jobName = object.getString("job_name");
                String jobHref = object.getString("job_href");
                String jobWelf = object.getString("jobwelf");
                String jobArea = object.getString("workarea_text").split("-")[0];
                Integer salary_min = MyUtil.parseSalary(object.getString("providesalary_text"))[0];
                Integer salary_max = MyUtil.parseSalary(object.getString("providesalary_text"))[1];
                String issueDate = object.getString("updatedate");
                String companyName = object.getString("company_name");
                String companyHref = object.getString("company_href");
                String companyType = object.getString("companytype_text");
                String companySize = object.getString("companysize_text");
                String companyInd = object.getString("companyind_text").replaceAll("/", " ");
                Job job = new Job();
                job.setJobId(jobId);
                job.setJobName(jobName);
                job.setJobHref(jobHref);
                job.setJobWelf(jobWelf);
                job.setJobArea(jobArea);
                job.setSalaryMin(salary_min);
                job.setSalaryMax(salary_max);
                job.setIssueDate(issueDate);
                job.setCompanyName(companyName);
                job.setCompanyHref(companyHref);
                job.setCompanyType(companyType);
                job.setCompanySize(companySize);
                job.setCompanyInd(companyInd);
                jobList.add(job);
                System.out.println("有新数据：" + jobHref);
                page.addTargetRequest(jobHref);
            }
            page.putField("job_list", jobList);
            System.out.println("发送 " + jobList.size() + "条数据");
            String cur_url = page.getRequest().getUrl();
            int s = cur_url.lastIndexOf(",");
            String str = cur_url.substring(s + 1);
            int cur_num = Integer.parseInt(str.split("\\.")[0]);
            int next_num = cur_num + 1;
            String next_url = cur_url.replace(cur_num + ".html", next_num + ".html");
            page.addTargetRequest(next_url);
            System.out.println("进入下一页：" + next_url);
        } else if (page.getRequest().getUrl().contains("jobs.51job.com")) {
            Html html = page.getHtml();
            List<Job> jobList = new ArrayList<>();
            Job job = new Job();
            String jobHref = page.getRequest().getUrl();
            String jobId = jobHref.substring(jobHref.lastIndexOf("/") + 1, jobHref.lastIndexOf("."));
            String jobMsg = Jsoup.parse(html.css("div.bmsg.job_msg.inbox").toString()).text();
            String jobAttr = Jsoup.parse(html.css("p.msg.ltype").toString()).text().replaceAll("  ", "").replaceAll("\\|", " ");
            String[] infos = jobAttr.split(" ");
            String jobYear = "";
            String jobEducation = "";
            String jobDemand = "";
            List<String> academicList = Arrays.asList("初中及以下", "高中", "中技", "中专", "大专", "本科", "硕士", "博士");
            for (int i = 0; i < infos.length; i++) {
                if (infos[i].contains("经验") || infos[i].contains("在校生/应届生")) {
                    jobYear = infos[i];
                }
                if (academicList.contains(infos[i])) {
                    jobEducation = infos[i];
                }
                if (infos[i].contains("招")) {
                    jobDemand = infos[i];
                }
            }
            if ("".equals(jobEducation)) {
                jobEducation = "无学历要求";
            }
            String companyMsg = Jsoup.parse(html.css("div.tmsg.inbox").toString()).text();
            job.setJobId(jobId);
            job.setJobMsg(jobMsg);
            job.setCompanyMsg(companyMsg);
            job.setJobAttr(jobAttr);
            job.setJobYear(jobYear);
            job.setJobEducation(jobEducation);
            job.setJobDemand(jobDemand);
            String companyArea = "";
            try {
                companyArea = Jsoup.parse(html.css("div.bmsg.inbox>p.fp").toString()).text();
            } catch (Exception e) {
                System.out.println("bug");
            }
            job.setCompanyArea(companyArea);
            jobList.add(job);
            page.putField("job_list", jobList);
            System.out.println("发送 " + jobList.size() + "条数据");
            System.out.println("---");
        } else {
            System.out.println("error page!");
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

//    @Scheduled(initialDelay = 1000, fixedDelay = 24 * 60 * 60 * 1000)
    public void process(){
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("36.249.49.11", 9999)));
        Spider.create(new JobProcessor1())
                .addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                .addPipeline(jobPipeline)
//                .thread(5)
//                .setDownloader(httpClientDownloader)
                .run();
    }

}
