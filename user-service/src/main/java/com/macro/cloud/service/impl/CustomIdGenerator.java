package com.macro.cloud.service.impl;


import com.macro.cloud.service.DistributeIdService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//  分布式id  通过第三方id的服务 生成一个全局id 就可以利用java的特性来做
//  内部维护一个map key为表名 value 为 id

public class CustomIdGenerator implements DistributeIdService {

    private static ConcurrentHashMap<String, AtomicLong> idMap = new ConcurrentHashMap<>();
    private static final String lock = "lock";

    public static ConcurrentHashMap<String, AtomicLong> getIdMap(){
        return idMap;
    }

    static{
        Thread thread=new Thread(new PersistenceStorageThread());
        thread.setDaemon(true);
        thread.start();
    }


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
