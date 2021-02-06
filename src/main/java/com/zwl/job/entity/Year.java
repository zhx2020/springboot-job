package com.zwl.job.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_year")
public class Year {

    @Id
    @Column(name = "year_id")
    private Integer yearId;

    @Column(name = "year_name")
    private String yearName;

    public Integer getYearId() {
        return yearId;
    }

    public void setYearId(Integer yearId) {
        this.yearId = yearId;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    @Override
    public String toString() {
        return "Year{" +
                "yearId=" + yearId +
                ", yearName='" + yearName + '\'' +
                '}';
    }
}
