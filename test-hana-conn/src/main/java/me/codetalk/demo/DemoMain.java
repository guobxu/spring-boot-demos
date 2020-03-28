package me.codetalk.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class DemoMain implements CommandLineRunner, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String jdbcUrl = "jdbc:sap://10.88.3.58:30059?reconnect=true";
        String user = "ETL_ENTER", passwd = "etlHANA1234";

        // schema list
        try(Connection conn = DriverManager.getConnection(jdbcUrl, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from schemas")) {
                while(rs.next()) {
                    LOGGER.info(rs.getString(1));
                }
        }

        // table list
//        try(Connection conn = DriverManager.getConnection(jdbcUrl, user, passwd);
//            ResultSet rs = conn.getMetaData().getTables(null, "ETL_ENTER",null,
//                    new String[] { "TABLE" })) {
//               while(rs.next()) {
//                LOGGER.info("{}", rs.getString(3));
//               }
//        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Class.forName("com.sap.db.jdbc.Driver");
    }
}
