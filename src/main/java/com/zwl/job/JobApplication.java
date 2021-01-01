package com.zwl.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.zwl.job.dao")
//@EnableScheduling
@EnableJms
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
