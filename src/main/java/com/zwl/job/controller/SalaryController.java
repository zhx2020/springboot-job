package com.zwl.job.controller;

import com.zwl.job.entity.Salary;
import com.zwl.job.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @RequestMapping("/findAll")
    public List<Salary> findAll() {
        return salaryService.findAll();
    }
}
