package com.ht.export.student;

import com.ht.popj.student.Student;
import com.ht.util.ExcelAnnotation;

/**
 * ����ѧ������model
 * @author Administrator
 *
 */
public class StudrewardExportModel {

	@ExcelAnnotation(name = "ѧ��ID", id = 0, align = 1, width = 160)
    private Integer studentid;
	@ExcelAnnotation(name = "����ԭ��", id = 0, align = 1, width = 160)
    private String reason;
	@ExcelAnnotation(name = "���ʹ�ʩ", id = 0, align = 1, width = 160)
    private String content;
	@ExcelAnnotation(name = "����ʱ��", id = 0, align = 1, width = 160)
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
