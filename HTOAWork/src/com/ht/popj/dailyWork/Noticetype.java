package com.ht.popj.dailyWork;

import java.util.Date;

public class Noticetype {
    private Integer id;

    private String noticetypename;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String noticeRemark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoticetypename() {
        return noticetypename;
    }

    public void setNoticetypename(String noticetypename) {
        this.noticetypename = noticetypename == null ? null : noticetypename.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNoticeRemark() {
        return noticeRemark;
    }

    public void setNoticeRemark(String noticeRemark) {
        this.noticeRemark = noticeRemark == null ? null : noticeRemark.trim();
    }
}