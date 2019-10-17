package com.sf.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Creator: 01380994
 * Date: 2019/4/18
 */
@Service("dataProviderService")
public class DataProviderServiceImpl implements DataProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataProviderServiceImpl.class);

    // 数据源配置
    protected static final String CONFIG_JDBCURL = "jdbcurl";
    protected static final String CONFIG_DRIVER = "driver";
    protected static final String CONFIG_USER = "username";
    protected static final String CONFIG_PASSWD = "password";

    @Override
    public List<String[]> queryData(Map<String, String> dataSource, String sql, Integer maxRows) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection(dataSource);
            stmt = conn.createStatement();
            if(maxRows != null) {
                stmt.setMaxRows(maxRows);
            }

            rs = stmt.executeQuery(sql);

            return toList(rs);
        } finally {
            closeQuietly(rs, stmt, conn);
        }
    }

    protected Connection getConnection(Map<String, String> dataSource) throws Exception {
        String driverClass = dataSource.get(CONFIG_DRIVER);
        Class.forName(driverClass);

        String jdbcUrl = dataSource.get(CONFIG_JDBCURL), username = dataSource.get(CONFIG_USER), password = dataSource.get(CONFIG_PASSWD);

        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public static  List<String[]> toList(ResultSet rs, Integer resultLimit) throws Exception {
        List<String[]> result = new ArrayList<>();

        int cols = rs.getMetaData().getColumnCount();
        for(int i = 0; rs.next() && (resultLimit == null || i < resultLimit); i++) {
            String[] row = new String[cols];
            for(int j = 0; j < cols; j++) {
                row[j] = rs.getString(j + 1);
            }
            result.add(row);
        }

        return result;
    }

    public static  List<String[]> toList(ResultSet rs) throws Exception {
        return toList(rs, null);
    }

    public static void closeQuietly(AutoCloseable... resources) {
        for(AutoCloseable resource : resources) {
            closeQuietly(resource);
        }
    }

    public static void closeQuietly(AutoCloseable resource) {
        if(resource == null) return;
        try {
            resource.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
