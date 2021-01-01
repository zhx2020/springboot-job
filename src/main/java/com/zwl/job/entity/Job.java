package com.zwl.job.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tab_job")
public class Job implements Serializable {

    @Id
    @Column(name = "job_id")
    private String jobId;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_href")
    private String jobHref;

    @Column(name = "job_area")
    private String jobArea;

    @Column(name = "job_welf")
    private String jobWelf;

    @Column(name = "job_msg")
    private String jobMsg;

    @Column(name = "job_year")
    private String jobYear;

    @Column(name = "job_education")
    private String jobEducation;

    @Column(name = "job_demand")
    private String jobDemand;

    @Column(name = "job_attr")
    private String jobAttr;

    @Column(name = "salary_min")
    private Integer salaryMin;

    @Column(name = "salary_max")
    private Integer salaryMax;

    @Column(name = "issue_date")
    private String issueDate;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_href")
    private String companyHref;

    @Column(name = "company_area")
    private String companyArea;

    @Column(name = "company_type")
    private String companyType;

    @Column(name = "company_size")
    private String companySize;

    @Column(name = "company_ind")
    private String companyInd;

    @Column(name = "company_attr")
    private String companyAttr;

    @Column(name = "company_msg")
    private String companyMsg;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobHref() {
        return jobHref;
    }

    public void setJobHref(String jobHref) {
        this.jobHref = jobHref;
    }

    public String getJobArea() {
        return jobArea;
    }

    public void setJobArea(String jobArea) {
        this.jobArea = jobArea;
    }

    public String getJobWelf() {
        return jobWelf;
    }

    public void setJobWelf(String jobWelf) {
        this.jobWelf = jobWelf;
    }

    public String getJobMsg() {
        return jobMsg;
    }

    public void setJobMsg(String jobMsg) {
        this.jobMsg = jobMsg;
    }

    public String getJobYear() {
        return jobYear;
    }

    public void setJobYear(String jobYear) {
        this.jobYear = jobYear;
    }

    public String getJobEducation() {
        return jobEducation;
    }

    public void setJobEducation(String jobEducation) {
        this.jobEducation = jobEducation;
    }

    public String getJobDemand() {
        return jobDemand;
    }

    public void setJobDemand(String jobDemand) {
        this.jobDemand = jobDemand;
    }

    public String getJobAttr() {
        return jobAttr;
    }

    public void setJobAttr(String jobAttr) {
        this.jobAttr = jobAttr;
    }

    public Integer getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Integer salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Integer getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Integer salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyHref() {
        return companyHref;
    }

    public void setCompanyHref(String companyHref) {
        this.companyHref = companyHref;
    }

    public String getCompanyArea() {
        return companyArea;
    }

    public void setCompanyArea(String companyArea) {
        this.companyArea = companyArea;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getCompanyInd() {
        return companyInd;
    }

    public void setCompanyInd(String companyInd) {
        this.companyInd = companyInd;
    }

    public String getCompanyAttr() {
        return companyAttr;
    }

    public void setCompanyAttr(String companyAttr) {
        this.companyAttr = companyAttr;
    }

    public String getCompanyMsg() {
        return companyMsg;
    }

    public void setCompanyMsg(String companyMsg) {
        this.companyMsg = companyMsg;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobHref='" + jobHref + '\'' +
                ", jobArea='" + jobArea + '\'' +
                ", jobWelf='" + jobWelf + '\'' +
                ", jobMsg='" + jobMsg + '\'' +
                ", jobYear='" + jobYear + '\'' +
                ", jobEducation='" + jobEducation + '\'' +
                ", jobDemand='" + jobDemand + '\'' +
                ", jobAttr='" + jobAttr + '\'' +
                ", salaryMin=" + salaryMin +
                ", salaryMax=" + salaryMax +
                ", issueDate='" + issueDate + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyHref='" + companyHref + '\'' +
                ", companyArea='" + companyArea + '\'' +
                ", companyType='" + companyType + '\'' +
                ", companySize='" + companySize + '\'' +
                ", companyInd='" + companyInd + '\'' +
                ", companyAttr='" + companyAttr + '\'' +
                ", companyMsg='" + companyMsg + '\'' +
                '}';
    }
}
