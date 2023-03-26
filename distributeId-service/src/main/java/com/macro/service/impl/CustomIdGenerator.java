package com.macro.service.impl;

import com.macro.service.inf.DistributeIdService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//  分布式id  通过第三方id的服务 生成一个全局id 就可以利用java的特性来做
//  内部维护一个map key为表名 value 为 id

public class CustomIdGenerator implements DistributeIdService {

    private static Map<String, AtomicLong> idMap = new ConcurrentHashMap<>();
    private static final String lock = "lock";

    // 该方法肯定是多线程调用
    @Override
    public long generateDistributeId(String tableName) {
        if (idMap.containsKey(tableName)) {
            setNextValue(tableName);

        } else {
            if (!idMap.containsKey(tableName)) {
                synchronized (lock) {
                    if (!idMap.containsKey(tableName)) {
                        idMap.put(tableName, new AtomicLong(1));
                    } else {
                        setNextValue(tableName);
                    }
                }
            } else {
                setNextValue(tableName);
            }
        }

        return idMap.get(tableName).get();
    }

    private void setNextValue(String tableName) {
        idMap.get(tableName).incrementAndGet();
    }
}
