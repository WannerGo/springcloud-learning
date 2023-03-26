package com.macro.distribute;

import com.sankuai.inf.leaf.plugin.annotation.EnableLeafServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLeafServer
public class DistributeIdServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributeIdServiceApplication.class, args);
    }

}
