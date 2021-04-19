package com.zwl.job.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zwl.job.entity.Job;
import com.zwl.job.utils.MyUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JobProcessor2 implements PageProcessor {

    private Site site = Site.me().setCharset("gbk")
            .setTimeOut(10 * 1000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3);

    public static String url1 = "https://search.51job.com/list/000000,000000,0121%252c0120%252c0122%252c0124%252c0130,00,9,99,+,2,1.html?lang=c&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&ord_field=0&dibiaoid=0&line=&welfare=";

    public static String url2 = "https://search.51job.com/list/000000,000000,7701%252c7702%252c7201%252c2707%252c7901,00,9,99,+,2,1.html?lang=c&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&ord_field=0&dibiaoid=0&line=&welfare=";

    @Autowired
    private JobPipeline jobPipeline;

    @Override
    public void process(Page page) {
        System.err.println(page.getRequest().getUrl());
        if (page.getRequest().getUrl().contains("https://search.51job.com")) {
            List<Selectable> list = page.getHtml().css("script[type=\"text/javascript\"]").nodes();
            String script = list.get(2).toString();
            int l = script.indexOf("{");
            int r = script.lastIndexOf("}");
            String json = script.substring(l, r + 1);
            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("engine_search_result");
            System.out.println("当前页职位数量：" + jsonArray.size());
            if (jsonArray.size() == 0) {
                HttpClient client = new HttpClient();
                HttpMethod method = new GetMethod("http://localhost:8080/queue/test?str=0");
                try {
                    client.executeMethod(method);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(method.getStatusLine());
                try {
                    System.out.println(method.getResponseBodyAsString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                method.releaseConnection();
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String jobHref = object.getString("job_href");
                System.out.println("新的url = ：" + jobHref);
                page.addTargetRequest(jobHref);
            }
            String cur_url = page.getRequest().getUrl();
            int s = cur_url.lastIndexOf(",");
            String str = cur_url.substring(s + 1);
            int cur_num = Integer.parseInt(str.split("\\.")[0]);
            int next_num = cur_num + 1;
            String next_url = cur_url.replace(cur_num + ".html", next_num + ".html");
            page.addTargetRequest(next_url);
            System.out.println("进入下一页：" + next_url);
            System.out.println("---debug---");
        } else if (page.getRequest().getUrl().contains("https://jobs.51job.com")) {
            Html html = page.getHtml();
            Job job = new Job();
            String jobHref = page.getRequest().getUrl();
            String jobId = jobHref.substring(jobHref.lastIndexOf("/") + 1, jobHref.lastIndexOf("."));
            String jobName = Jsoup.parse(html.css("div.cn>h1").toString()).text();
            String jobWelf = Jsoup.parse(html.css("div.t1").toString()).text();
            String jobSalary = Jsoup.parse(html.css("div.cn>strong").toString()).text();
            Integer salaryMin = MyUtil.parseSalary(jobSalary)[0];
            Integer salaryMax = MyUtil.parseSalary(jobSalary)[1];
            String jobMsg = Jsoup.parse(html.css("div.bmsg.job_msg.inbox").toString()).text();
            String companyAttr = Jsoup.parse(html.css("div.com_tag").toString()).text();
            String[] attrs = companyAttr.split(" ");
            String companyType = "";
            String companySize = "";
            List<String> typeList = Arrays.asList("国企", "外资（欧美）", "外资（非欧美）", "上市公司",
                    "合资", "民营公司", "外企代表处", "政府机关", "事业单位", "非盈利组织", "创业公司");
            for (int i = 0; i < attrs.length; i++) {
                if (typeList.contains(attrs[i])) {
                    companyType = attrs[i];
                }
                if (attrs[i].contains("0人")) {
                    companySize = attrs[i];
                }
            }
            int u = companyAttr.indexOf("以上");
            String companyInd = "";
            if (u != -1) {
                companyInd = companyAttr.substring(u + 3);
            } else {
                u = companyAttr.indexOf("人");
                if (u != -1) {
                    companyInd = companyAttr.substring(u + 2);
                } else {
                    companyInd = companyAttr.substring(companyAttr.indexOf(" ") + 1);
                }
            }
            String companyName = Jsoup.parse(html.css("a.catn").toString()).text();
            String companyHref = html.css("a.catn", "href").toString();
            String jobAttr = Jsoup.parse(html.css("p.msg.ltype").toString()).text().replaceAll("  ", "").replaceAll("\\|", " ");
            String jobArea = jobAttr.split(" ")[0].split("-")[0];
            String[] infos = jobAttr.split(" ");
            String jobYear = "";
            String jobEducation = "";
            String jobDemand = "";
            String issueDate = "";
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
                if (infos[i].contains("发布")) {
                    int e = infos[i].indexOf("发");
                    issueDate = infos[i].substring(0, e);
                }
            }
            if ("".equals(jobEducation)) {
                jobEducation = "无学历要求";
            }
            if ("".equals(jobYear)) {
                jobYear = "无需经验";
            }
            String companyMsg = Jsoup.parse(html.css("div.tmsg.inbox").toString()).text();
            job.setJobId(jobId);
            job.setJobName(jobName);
            job.setJobHref(jobHref);
            job.setJobArea(jobArea);
            job.setJobWelf(jobWelf);
            job.setJobMsg(jobMsg);
            job.setJobYear(jobYear);
            job.setJobEducation(jobEducation);
            job.setJobDemand(jobDemand);
            job.setJobAttr(jobAttr);
            job.setSalaryMin(salaryMin);
            job.setSalaryMax(salaryMax);
            job.setIssueDate(issueDate);
            job.setCompanyName(companyName);
            job.setCompanyHref(companyHref);
            job.setCompanyType(companyType);
            job.setCompanySize(companySize);
            job.setCompanyInd(companyInd);
            job.setCompanyAttr(companyAttr);
            job.setCompanyMsg(companyMsg);
            String companyArea = "";
            try {
                companyArea = Jsoup.parse(html.css("div.bmsg.inbox>p.fp").toString()).text();
            } catch (Exception e) {
                System.out.println("no companyArea");
            }
            job.setCompanyArea(companyArea);
            page.putField("job", job);
            System.out.println("put a data");
            System.out.println("---");
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 24 * 60 * 60 * 1000)
    public void process(){
        Spider.create(new JobProcessor2())
                .addUrl(url1, url2)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                .addPipeline(jobPipeline)
//                .thread(5)
                .run();
    }

}
