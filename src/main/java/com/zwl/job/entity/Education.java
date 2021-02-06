package com.zwl.job.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_education")
public class Education {

    @Id
    @Column(name = "education_id")
    private Integer educationId;

    @Column(name = "education_name")
    private String educationName;

    public Integer getEducationId() {
        return educationId;
    }

    public void setEducationId(Integer educationId) {
        this.educationId = educationId;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    @Override
    public String toString() {
        return "Education{" +
                "educationId=" + educationId +
                ", educationName='" + educationName + '\'' +
                '}';
    }
}
