package com.ht.export.dailyWork;

import com.ht.popj.student.Student;
import com.ht.util.ExcelAnnotation;

/**
 * ����ѧ������model
 * @author Administrator
 *
 */
public class AttendancesExportModel {

	@ExcelAnnotation(name = "Ա��ID", id = 0, align = 1, width = 160)
    private String empid;
	@ExcelAnnotation(name = "��ʱ��", id = 0, align = 1, width = 160)
    private String workday;
	@ExcelAnnotation(name = "״̬", id = 0, align = 1, width = 160)
    private String flag;
	@ExcelAnnotation(name = "ʱ��", id = 0, align = 1, width = 160)
    private String attentime;
	@ExcelAnnotation(name = "����", id = 0, align = 1, width = 160)
    private String remark;
	
	
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getWorkday() {
		return workday;
	}
	public void setWorkday(String workday) {
		this.workday = workday;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAttentime() {
		return attentime;
	}
	public void setAttentime(String attentime) {
		this.attentime = attentime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
