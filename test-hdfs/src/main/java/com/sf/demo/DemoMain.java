package com.sf.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Creator: 01380994
 * Date: 2019/1/21
 */
@SpringBootApplication
public class DemoMain implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    public static final String FS_DEFAULT_FS = "fs.defaultFS";
    public static final String HDFS_USER_NAME = "hdfs.user.name";

    public static final String UTF8 = "UTF-8";

    @Value("#{${hdfs.config}}")
    private Map<String, String> propMap;

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Map<String, String> propMap = new HashMap<>();
//        JSONArray jsonArray = JSONArray.parseArray(hdfsConfig);
//        jsonArray.forEach(obj -> {
//            JSONObject json = (JSONObject)obj;
//            propMap.put(json.getString("first"), json.getString("second"));
//        });

        // HDFS
        Configuration conf = new Configuration();
        for (Map.Entry<String,String> entry: propMap.entrySet()) {
            conf.set(entry.getKey(), entry.getValue());
        }

        // hfds FileSystem
        try(FileSystem fileSystem = FileSystem.get(new URI(propMap.get(FS_DEFAULT_FS)), conf, propMap.get(HDFS_USER_NAME))) {
            fileSystem.mkdirs(new Path("/rptdata/01380994/"));

            Path filePath = new Path("/rptdata/01380994/test01");
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(fileSystem.create(filePath), UTF8))) {
                writer.println("这是第一行");
                writer.println("This is second line. 第二行");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileSystem.open(filePath), UTF8))) {
                String line = null;
                while((line = reader.readLine()) != null) {
                    LOGGER.info("Line: " + line);
                }
            }
        }

    }

}
