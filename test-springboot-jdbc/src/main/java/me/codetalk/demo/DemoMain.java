package me.codetalk.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Creator: 01380994
 * Date: 2019/4/11
 */
@SpringBootApplication
public class DemoMain implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("查询所有的字典...");

        List<Dict> dictList = new ArrayList<>();
        jdbcTemplate.query("select dict_key, dict_name from fnd_dict where dict_key = ?", new Object[] { "market_job_budget" },
                // rowNum 表示当前行的序号, 从0开始, 0 1 2 ...
                (rs, rowNum) -> dictList.add(new Dict(rs.getString(1), rs.getString(2))));
        for(Dict dict : dictList) {
            LOGGER.info("{}", dict);
        }

        LOGGER.info("查询指定的字典列表...market_job_budget, market_job_duration");
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("dicts", Arrays.asList("market_job_budget", "market_job_duration"));

        NamedParameterJdbcTemplate namedParamJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        namedParamJdbcTemplate.query("select dict_key, dict_name from fnd_dict where dict_key in (:dicts)",
                parameters,
                (rs, rowNum) -> new Dict(rs.getString(1), rs.getString(2)))
                .forEach(dict -> LOGGER.info("{}", dict));

        LOGGER.info("查询指定的字典列表...market_job_budget, market_job_duration & 指定dictKey");
        parameters.addValue("dictKey", "market_job_budget");
        namedParamJdbcTemplate.query("select dict_key, dict_name from fnd_dict where dict_key in (:dicts) and dict_key = :dictKey",
                parameters,
                (rs, rowNum) -> new Dict(rs.getString(1), rs.getString(2)))
                .forEach(dict -> LOGGER.info("{}", dict));

        LOGGER.info("查询指定字典的名称...market_job_budget(单个字段 / 单行)");
        String dictKey = "market_job_budget",
                dictName = jdbcTemplate.queryForObject("select dict_name from fnd_dict where dict_key = ?", new Object[]{ dictKey }, String.class);
        LOGGER.info("字典 {} 的名称 = {}", dictKey, dictName);
    }

}

class Dict {

    String key;
    String name;

    Dict(String key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Dict: key = %s, name = %s", key, name);
    }
}
