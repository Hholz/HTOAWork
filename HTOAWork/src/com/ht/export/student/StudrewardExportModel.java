package com.ht.export.student;

import com.ht.popj.student.Student;
import com.ht.util.ExcelAnnotation;

/**
 * 意向学生导出model
 * @author Administrator
 *
 */
public class StudrewardExportModel {

	@ExcelAnnotation(name = "学生ID", id = 0, align = 1, width = 160)
    private Integer studentid;
	@ExcelAnnotation(name = "奖惩原因", id = 0, align = 1, width = 160)
    private String reason;
	@ExcelAnnotation(name = "奖惩措施", id = 0, align = 1, width = 160)
    private String content;
	@ExcelAnnotation(name = "奖惩时间", id = 0, align = 1, width = 160)
    private String createTime;


    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
