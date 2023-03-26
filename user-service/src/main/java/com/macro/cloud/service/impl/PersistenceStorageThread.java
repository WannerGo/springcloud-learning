package com.macro.cloud.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PersistenceStorageThread implements Runnable {

    private static String idMapPath = System.getProperty("user.dir") + File.separator + "idMap.json";

    @Override
    public void run() {
        // 检查是否有缓存map的文件,如果有就刷新存储数据的Map
        initIdMap();
        TimerTask task = null;
        try {
            task = new UpdateId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Timer timer = new Timer(Boolean.TRUE);
        timer.scheduleAtFixedRate(task, 1000, 5000);
    }

    private void initIdMap() {
        UpdateId.fileRead(idMapPath);

    }

    static class UpdateId extends TimerTask {

        static ObjectMapper objectMapper = new ObjectMapper();

        private UpdateId() throws IOException {

        }

        @Override
        public void run() {
            // 刷新已经更新的数据 初步json
            try {
                UpdateId.fileWrite(idMapPath, objectMapper.writeValueAsString(CustomIdGenerator.getIdMap()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        public static void fileWrite(String filePath, String content) {
            FileOutputStream outputStream = null;
            try {
                File file = new File(filePath);
                //表示在原有文件末尾追加信息
                outputStream = new FileOutputStream(file);//形参里面可追加true参数，表示在原有文件末尾追加信息
                outputStream.write(content.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void fileRead(String filePath) {
            File file = new File(filePath);
            if (file.exists()) {
                try {

                    //创建FileInputStream对象，读取文件内容
                    FileInputStream fis = new FileInputStream(file);
                    Map<String, AtomicLong> concurrentHashMap = objectMapper.readValue(fis, new TypeReference<Map<String, AtomicLong>>() {
                    });
                    ConcurrentHashMap<String, AtomicLong> idMap = CustomIdGenerator.getIdMap();
                    idMap.putAll(concurrentHashMap);


                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }


}


