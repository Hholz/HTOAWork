package com.ht.popj.flow;

public class FlowHuorApplyDetail {
    private Integer id;

    private Integer huorapplyid;

    private Integer flowmodelid;

    private Integer empid;

    private Integer status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHuorapplyid() {
        return huorapplyid;
    }

    public void setHuorapplyid(Integer huorapplyid) {
        this.huorapplyid = huorapplyid;
    }

    public Integer getFlowmodelid() {
        return flowmodelid;
    }

    public void setFlowmodelid(Integer flowmodelid) {
        this.flowmodelid = flowmodelid;
    }

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
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