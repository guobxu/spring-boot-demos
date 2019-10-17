package me.codetalk.demo;

import me.codetalk.demo.entity.Job;
import me.codetalk.demo.mapper.JobMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.tools.jar.CommandLine;

import java.util.Arrays;

/**
 * Creator: 01380994
 * Date: 2019/3/15
 */
@SpringBootApplication
@MapperScan(basePackages = {
        "me.codetalk.demo.mapper"
})
public class DemoMain implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    @Autowired
    private JobMapper jobMapper;

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Job job1 = jobMapper.selectJob(1, null);
        Job job2 = jobMapper.selectJob(1, Arrays.asList(new String[] {"job_id", "job_title", "job_status"}));

        LOGGER.info("Test done");
    }

}
