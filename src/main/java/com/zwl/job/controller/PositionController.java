package com.zwl.job.controller;

import com.zwl.job.entity.Position;
import com.zwl.job.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @RequestMapping("/findAll")
    public List<Position> findAll() {
        return positionService.findAll();
    }
}
