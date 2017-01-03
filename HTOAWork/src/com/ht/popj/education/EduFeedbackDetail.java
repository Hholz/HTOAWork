package com.ht.popj.education;

import java.util.List;

import com.ht.popj.finance.FinaceFeedbackset;

public class EduFeedbackDetail {
	private Integer id;

	private Integer feedbackId;

	private Integer templateId;

	private Float score;

	private String createTime;

	private String updateTime;

	private Integer status;

	private String remark;

	private FinaceFeedbackset template;

	private List<Integer> ids;
	
	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public FinaceFeedbackset getTemplate() {
		return template;
	}

	public void setTemplate(FinaceFeedbackset template) {
		this.template = template;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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

	@Override
	public String toString() {
		return "EduFeedbackDetail [id=" + id + ", feedbackId=" + feedbackId + ", templateId=" + templateId + ", score="
				+ score + ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status
				+ ", remark=" + remark + ", template=" + template + "]";
	}
	
}