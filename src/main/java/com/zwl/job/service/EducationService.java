package com.zwl.job.service;

import com.zwl.job.dao.EducationDao;
import com.zwl.job.entity.Education;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {

    @Autowired
    private EducationDao educationDao;

    public List<Education> findAll() {
        return educationDao.selectAll();
    }
}
