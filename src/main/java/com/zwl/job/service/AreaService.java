package com.zwl.job.service;

import com.zwl.job.dao.AreaDao;
import com.zwl.job.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    @Autowired
    private AreaDao areaDao;

    public List<Area> findAll() {
        return areaDao.selectAll();
    }
}
