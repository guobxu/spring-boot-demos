package me.codetalk.demo.entity;

/**
 * Creator: 01380994
 * Date: 2019/3/14
 */
public class Job {

    private Integer id;
    private Long userId;
    private String title;
    private String detail;
    private Integer status; // 状态: 1 打开(已发布) 2 进行中 3 发包方已完成 4 已完成

    private Integer categoryId;
    private Integer duration; // 预估周期(dict) 1 一周内 2 <1月, 3 1-3月, 4 大于3月
    private Integer budget; // 预算(dict) 1 3k以内 2 <10k 3 10k-30k 4 30k-60k 5 >60k

    private Integer fulltime; // 0/1

    private Long deadline;

    private Long contractor; // 承包方

    private Long startDate;
    private Long deliveryDate;
    private Double actualOffer; // 实际价格

    private String contractorRating; // 承包方评价
    private String employerRating; // 雇主评价

    private Integer deleteMark; // 0-1
    private Long deleteBy;
    private Integer deleteReason; // dict

    private Long createDate;
    private Long lastUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getFulltime() {
        return fulltime;
    }

    public void setFulltime(Integer fulltime) {
        this.fulltime = fulltime;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public Long getContractor() {
        return contractor;
    }

    public void setContractor(Long contractor) {
        this.contractor = contractor;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getActualOffer() {
        return actualOffer;
    }

    public void setActualOffer(Double actualOffer) {
        this.actualOffer = actualOffer;
    }

    public String getContractorRating() {
        return contractorRating;
    }

    public void setContractorRating(String contractorRating) {
        this.contractorRating = contractorRating;
    }

    public String getEmployerRating() {
        return employerRating;
    }

    public void setEmployerRating(String employerRating) {
        this.employerRating = employerRating;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Integer getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(Integer deleteReason) {
        this.deleteReason = deleteReason;
    }

    public Long getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(Long deleteBy) {
        this.deleteBy = deleteBy;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
