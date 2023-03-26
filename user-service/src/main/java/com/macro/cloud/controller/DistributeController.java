package com.macro.cloud.controller;

import com.macro.cloud.domain.CommonResult;
import com.macro.cloud.service.DistributeIdService;
import com.macro.cloud.service.impl.CustomIdGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/id")
public class DistributeController {

    private static DistributeIdService distributeIdService = new CustomIdGenerator();


    @GetMapping("/{tableName}")
    public CommonResult create(@PathVariable("tableName") String tableName) {
        System.out.println(distributeIdService.generateDistributeId(tableName));
        return new CommonResult("操作成功", 200);
    }
}
