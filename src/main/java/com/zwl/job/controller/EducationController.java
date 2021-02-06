package com.zwl.job.controller;

import com.zwl.job.entity.Education;
import com.zwl.job.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/education")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @RequestMapping("/findAll")
    public List<Education> findAll() {
        return educationService.findAll();
    }
}
