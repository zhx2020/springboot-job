package com.zwl.job.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tab_store")
public class Store {

    @Id
    @Column(name = "job_id")
    private String jobId;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "create_time")
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Store{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
