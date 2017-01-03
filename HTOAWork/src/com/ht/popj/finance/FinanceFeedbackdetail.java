package com.ht.popj.finance;


public class FinanceFeedbackdetail {
    private Integer id;

    private Integer feedbacksetid;

    private String detailname;

    private Integer detailscore;

    private Integer status;

    private String createtime;

    private String updatetime;

    private String remark;
    
    private FinaceFeedbackset finaceFeedbackset;

    public Integer getId() {
        return id;
    }

    public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public FinaceFeedbackset getFinaceFeedbackset() {
		return finaceFeedbackset;
	}

	public void setFinaceFeedbackset(FinaceFeedbackset finaceFeedbackset) {
		this.finaceFeedbackset = finaceFeedbackset;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFeedbacksetid() {
        return feedbacksetid;
    }

    public void setFeedbacksetid(Integer feedbacksetid) {
        this.feedbacksetid = feedbacksetid;
    }

    public String getDetailname() {
        return detailname;
    }

    public void setDetailname(String detailname) {
        this.detailname = detailname == null ? null : detailname.trim();
    }

    public Integer getDetailscore() {
        return detailscore;
    }

    public void setDetailscore(Integer detailscore) {
        this.detailscore = detailscore;
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