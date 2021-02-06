package com.zwl.job.service;

import com.zwl.job.dao.PositionDao;
import com.zwl.job.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    @Autowired
    private PositionDao positionDao;

    public List<Position> findAll() {
        return positionDao.selectAll();
    }
}
