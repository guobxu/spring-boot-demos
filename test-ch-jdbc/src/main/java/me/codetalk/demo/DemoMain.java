package me.codetalk.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class DemoMain implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Class.forName("ru.yandex.clickhouse.ClickHouseDriver");

        try(Connection conn = DriverManager.getConnection("jdbc:clickhouse://10.202.76.206:8123" ,"cdbi", "cdbi");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select groupUniqArray(order_no) from demo.test ")) {
            if(rs.next()) {
                String str = rs.getString(1);

                LOGGER.info("Result: {}", str); // Result: [0,122,2,10,3]
            }
        }
    }

}
