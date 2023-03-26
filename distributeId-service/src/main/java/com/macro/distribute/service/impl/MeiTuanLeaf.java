package com.macro.distribute.service.impl;

import com.macro.distribute.service.inf.DistributeIdService;
import com.sankuai.inf.leaf.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeiTuanLeaf implements DistributeIdService {


    @Autowired
    private SegmentService segmentService;

    @Override
    public long generateDistributeId(String tableName) {
        return segmentService.getId(tableName).getId();
    }
}
