package com.sf.demo;

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

    private static final String JDBC_URL = "jdbc:mysql://epm-m.db.sfdc.com.cn:3306/?useUnicode=true&characterEncoding=UTF-8&useSSL=true";
    private static final String USER = "epm4cdbi";
    private static final String PASSWD = "tT-a^AD-4aO3pcJdDt";

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        try(Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select 1 from epm.`v_op_login_pc` limit 5")) {
            while(rs.next()) {
                LOGGER.info("column 1: {}", rs.getString(1));
            }
        }
    }

}
