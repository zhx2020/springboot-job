package com.zwl.job.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "test", type = "job", shards = 1, replicas = 0)
public class JobItem {

    @Id
    @Field(store = true, type = FieldType.Keyword)
    private String jobId;

    @Field(store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String jobName;

    @Field(index = false, store = true, type = FieldType.Keyword)
    private String jobHref;

    @Field(store = true, type = FieldType.Keyword)
    private String jobArea;

    @Field(store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String jobWelf;

    @Field(store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String jobMsg;

    @Field(store = true, type = FieldType.Keyword)
    private String jobYear;

    @Field(store = true, type = FieldType.Keyword)
    private String jobEducation;

    @Field(store = true, type = FieldType.Keyword)
    private String jobDemand;

    @Field(store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String jobAttr;

    @Field(store = true, type = FieldType.Integer)
    private Integer salaryMin;

    @Field(store = true, type = FieldType.Integer)
    private Integer salaryMax;

    @Field(store = true, type = FieldType.Keyword)
    private String issueDate;

    @Field(store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String companyName;

    @Field(index = false, store = true, type = FieldType.Keyword)
    private String companyHref;

    @Field(store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String companyArea;

    @Field(store = true, type = FieldType.Keyword)
    private String companyType;

    @Field(store = true, type = FieldType.Keyword)
    private String companySize;

    @Field(store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String companyInd;

    @Field(store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String companyAttr;

    @Field(store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", type = FieldType.Text)
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
        return "JobItem{" +
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
