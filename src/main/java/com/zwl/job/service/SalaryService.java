package com.zwl.job.service;

import com.zwl.job.dao.SalaryDao;
import com.zwl.job.entity.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryDao salaryDao;

    public List<Salary> findAll() {
        return salaryDao.selectAll();
    }
}
