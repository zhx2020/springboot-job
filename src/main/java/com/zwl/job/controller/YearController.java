package com.zwl.job.controller;

import com.zwl.job.entity.Year;
import com.zwl.job.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/year")
public class YearController {

    @Autowired
    private YearService yearService;

    @RequestMapping("/findAll")
    public List<Year> findAll() {
        return yearService.findAll();
    }
}
