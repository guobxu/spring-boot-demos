package me.codetalk.demo.mapper;

import me.codetalk.demo.entity.Job;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Creator: 01380994
 * Date: 2019/3/14
 */
public interface JobMapper {

    Job selectJob(@Param("jobId") Integer jobId,
                  @Param("columns") List<String> columns);

}
