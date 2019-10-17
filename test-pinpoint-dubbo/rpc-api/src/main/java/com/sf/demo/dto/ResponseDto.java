package com.sf.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
/**
 * Creator: 01380994
 * Date: 2019/4/18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> implements Serializable {

    @JsonProperty("ret_code")
    private int code;
    @JsonProperty("ret_msg")
    private String msg;
    @JsonProperty("ret_data")
    private T data;

    // General Codes
    public static final int CODE_SUCCESS = 1;       // 成功
    public static final int CODE_ERROR = 2;         // 错误

    public static final int CODE_INVALID_PARAMS = 101;      // 错误的请求参数
    public static final int CODE_AUTH_ERROR = 102;          // 身份认证错误

    @JsonIgnore
    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }

    @JsonIgnore
    public boolean isFailure() {
        return code != CODE_SUCCESS;
    }

    public static final ResponseDto success() {
        ResponseDto dto = new ResponseDto();
        dto.setCode(CODE_SUCCESS);

        return dto;
    }

    public static final <T> ResponseDto success(T data) {
        ResponseDto dto = new ResponseDto();
        dto.setCode(CODE_SUCCESS);
        dto.setData(data);

        return dto;
    }

    public static final ResponseDto error(int code, String msg) {
        if(code == CODE_SUCCESS) {
            throw new IllegalArgumentException("code cannot = 1");
        }

        ResponseDto dto = new ResponseDto();
        dto.setCode(code);
        dto.setMsg(msg);

        return dto;
    }

    public static final ResponseDto error(String msg) {
        return error(CODE_ERROR, msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
