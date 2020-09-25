package com.sf.demo;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.fastjson.JSONObject;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.validate.SqlConformance;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Creator: 01380994
 * Date: 2020/9/18
 */
@SpringBootApplication
public class SqlParseDemoMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlParseDemoMain.class);

    public static void main(String[] args) throws Exception {
        File[] files = new File("sql").listFiles();
        for(File file : files) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF-8"
            ))) {
                LOGGER.info("File: {}", file.getName());
                String sql = IOUtils.toString(reader);
//                LOGGER.info("=================Druid   Parse=================");
                parseSqlAndPrint_druid(sql);
            }
        }
    }

    /**
     * 根据sql解析得到查询的库 / 表 / 字段
     * @param sql
     * @return
     */
    private static void parseSqlAndPrint_druid(String sql) {
        LOGGER.info("SQL: {}", sql);
        List<SQLStatement> stmts = SQLUtils.parseStatements(sql, JdbcConstants.PRESTO);
        for (int i = 0; i < stmts.size(); i++) {

            SQLStatement stmt = stmts.get(i);
            SchemaStatVisitor visitor = new SchemaStatVisitor();
            stmt.accept(visitor);
            //获取表名称
            Map<TableStat.Name, TableStat> map = visitor.getTables();
            for (TableStat.Name key : map.keySet()) {
                LOGGER.info("Table: {}", key.getName().toUpperCase());
            }

            //获取字段名称
            List<TableStat.Column>   listColumn = new ArrayList<>(visitor.getColumns());
            for(TableStat.Column column:listColumn){
                LOGGER.info("Column: {}", column.getFullName().toUpperCase());
            }
        }
    }

}
