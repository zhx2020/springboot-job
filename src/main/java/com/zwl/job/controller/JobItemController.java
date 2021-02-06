package com.zwl.job.controller;

import com.zwl.job.entity.JobItem;
import com.zwl.job.entity.Result;
import com.zwl.job.service.JobItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/jobitem")
public class JobItemController {

    @Autowired
    private JobItemService jobItemService;

    @RequestMapping("/find")
    public Page<JobItem> find(String keyWord, int page) {
        return jobItemService.find(keyWord, page);
    }

    @RequestMapping("/findByJobName")
    public Page<JobItem> findByJobName(String jobName, int page) {
        return jobItemService.findByJobName(jobName, page);
    }

    @RequestMapping("/findByIssueDate")
    public Page<JobItem> findByIssueDate(String issueDate, int page) {
        return jobItemService.findByIssueDate(issueDate, page);
    }

    @RequestMapping("/jobEducation")
    public Result jobEducation() {
        return jobItemService.getJobEducation();
    }

    @RequestMapping("/jobYear")
    public Result jobYear() {
        return jobItemService.getJobYear();
    }

    @RequestMapping("/jobAmount")
    public Result jobAmount() {
        return jobItemService.getJobAmount();
    }

    @RequestMapping("/jobArea")
    public Result jobArea() {
        return jobItemService.getJobArea();
    }

    @RequestMapping("/jobSalary")
    public Result jobSalary() {
        return jobItemService.getJobSalary();
    }

    @RequestMapping("/companyType")
    public Result companyType() {
        return jobItemService.getCompanyType();
    }

    @RequestMapping("/jobWelf")
    public Result jobWelf() {
        return jobItemService.getJobWelf();
    }

    @RequestMapping("/jobName")
    public Result jobName() {
        return jobItemService.getJobName();
    }

    @RequestMapping("/companyInd")
    public Result companyInd() {
        return jobItemService.getCompanyInd();
    }

    @RequestMapping("/findById")
    public Result findById(String id) {
        return jobItemService.findById(id);
    }

    @RequestMapping("/search")
    public Result search(String keyWord, String jobArea, Integer salaryMin, Integer salaryMax, String jobYear, String jobEducation,
                         String companyType, String companySize, Integer page, Integer size) {
        return jobItemService.search(keyWord, jobArea, salaryMin, salaryMax, jobYear, jobEducation, companyType, companySize, page, size);
    }

    @RequestMapping("/search2")
    public Result search2(String keyWord, String jobArea, Integer salaryMin, Integer salaryMax, String jobYear, String jobEducation,
                         String companyType, String companySize, Integer page, Integer size) {
        return jobItemService.search2(keyWord, jobArea, salaryMin, salaryMax, jobYear, jobEducation, companyType, companySize, page, size);
    }

    @RequestMapping("/push")
    public Result push(String id) {
        return jobItemService.push(id);
    }
}
