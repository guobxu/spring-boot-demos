package me.codetalk.demo;

import me.codetalk.demo.cache.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Creator: 01380994
 * Date: 2019/3/12
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "me.codetalk.demo.cache",
})
public class DemoMain implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    private static final String TEST_KEY = "test_brpop_list";

    @Autowired
    private RedisCache cache;

    private ExecutorService executor = Executors.newFixedThreadPool(3);

    private Random rand = new Random();

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        executor.submit(new LpushTask()); // lPush

        executor.submit(new BrpopTask()); // bRpop
        executor.submit(new BrpopTask()); // bRpop
    }

    class LpushTask implements Runnable {

        @Override
        public void run() {
            int i = 0;
            try {
                while(true) {
                    Thread.sleep(3000L + rand.nextInt(600));

                    String val = String.valueOf(++i);
                    LOGGER.info("lPush value = {}", val);
                    cache.lPush(TEST_KEY, val);
                }
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }

    }

    class BrpopTask implements Runnable {

        @Override
        public void run() {
            try {
                while(true) {
                    String val = cache.bRpop(TEST_KEY, Integer.MAX_VALUE);
                    LOGGER.info("Thread = {} bRpop value = {}", Thread.currentThread().getId(), val);
                }
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }

    }

}


