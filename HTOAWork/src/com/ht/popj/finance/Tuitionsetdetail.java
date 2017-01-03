package com.ht.popj.finance;

public class Tuitionsetdetail {
    private Integer id;

    private Integer tuitionid;

    private String tuitionname;

    private Float money;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTuitionid() {
        return tuitionid;
    }

    public void setTuitionid(Integer tuitionid) {
        this.tuitionid = tuitionid;
    }

    public String getTuitionname() {
        return tuitionname;
    }

    public void setTuitionname(String tuitionname) {
        this.tuitionname = tuitionname == null ? null : tuitionname.trim();
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}