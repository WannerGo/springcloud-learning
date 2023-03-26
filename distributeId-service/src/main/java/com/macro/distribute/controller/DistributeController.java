package com.macro.distribute.controller;


import com.macro.distribute.domain.CommonResult;
import com.macro.distribute.service.inf.DistributeIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/id")
public class DistributeController {

    @Autowired
    @Qualifier("meiTuanLeaf")
    private  DistributeIdService distributeIdService;



    @GetMapping("/{tableName}")
    public CommonResult create(@PathVariable("tableName") String tableName) {
        System.out.println(distributeIdService.generateDistributeId(tableName));
        return new CommonResult("操作成功", 200);
    }

    @GetMapping("/times/{times}")
    public CommonResult create(@PathVariable("times") Integer times) {
        for (int i = 0; i < times; i++) {
            distributeIdService.generateDistributeId("test" + i);
        }
        return new CommonResult("操作成功", 200);
    }

}
