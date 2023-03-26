package com.macro.distribute.service.impl;


import com.macro.distribute.service.inf.DistributeIdService;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//  分布式id  通过第三方id的服务 生成一个全局id 就可以利用java的特性来做
//  内部维护一个map key为表名 value 为 id

@Component
public class CustomIdGenerator implements DistributeIdService {

    private static ConcurrentHashMap<String, AtomicLong> ID_MAP = new ConcurrentHashMap<>();
    private static final String LOCK = "lock";

    public static ConcurrentHashMap<String, AtomicLong> getIdMap(){
        return ID_MAP;
    }

    static{
        Thread thread=new Thread(new PersistenceStorageThread());
        thread.setDaemon(true);
        thread.start();
    }


    // 该方法肯定是多线程调用
    @Override
    public long generateDistributeId(String tableName) {
        if (ID_MAP.containsKey(tableName)) {
            setNextValue(tableName);

        } else {
            if (!ID_MAP.containsKey(tableName)) {
                synchronized (LOCK) {
                    if (!ID_MAP.containsKey(tableName)) {
                        ID_MAP.put(tableName, new AtomicLong(1));
                    } else {
                        setNextValue(tableName);
                    }
                }
            } else {
                setNextValue(tableName);
            }
        }

        return ID_MAP.get(tableName).get();
    }

    private void setNextValue(String tableName) {
        ID_MAP.get(tableName).incrementAndGet();
    }
}
