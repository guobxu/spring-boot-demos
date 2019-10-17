package com.sf.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Creator: 01380994
 * Date: 2019/4/18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID = 9008962008644249721L;

    private String empCode; // 员工号
    private String empName; // 员工名

    private String hqCode;
    private String areaCode;
    private String divisionCode;

    private String deptCode; // 员工部门代码
    private String deptName; // 员工部门
    private String deptLevel; // 部门级别

    private String mail;        // 员工邮箱

    private String orgName; // 部门
    private String positionName; // 职务

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getHqCode() {
        return hqCode;
    }

    public void setHqCode(String hqCode) {
        this.hqCode = hqCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(String deptLevel) {
        this.deptLevel = deptLevel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
