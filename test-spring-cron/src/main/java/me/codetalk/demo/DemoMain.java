package me.codetalk.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creator: 01380994
 * Date: 2019/3/20
 */
@SpringBootApplication
public class DemoMain implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // *   *　　*　　*　　*　　*　　
        // 秒  分　 时　 日　 月　 周
//        CronSequenceGenerator crongen = new CronSequenceGenerator("0 10/40 1,2 * * 1-5"); // 周一到周五1:10:00 1:50:00
//                                                                                                     // 周一到周五2:10:00 1:50:00
//        Date n = crongen.next(new Date());
//        LOGGER.info("N date = [{}]", n);
//
//        Date nn = crongen.next(n);
//        LOGGER.info("NN date = [{}]", nn);
//
//        Date nnn = crongen.next(nn);
//        LOGGER.info("NNN date = [{}]", nnn);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        LOGGER.info("Current: {}", sdf.format(new Date()));
    }

}
