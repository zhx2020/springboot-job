package com.zwl.job.service;

import com.zwl.job.dao.YearDao;
import com.zwl.job.entity.Year;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearService {

    @Autowired
    private YearDao yearDao;

    public List<Year> findAll() {
        return yearDao.selectAll();
    }
}
