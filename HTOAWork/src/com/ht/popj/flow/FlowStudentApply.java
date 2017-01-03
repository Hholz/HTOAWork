package com.ht.popj.flow;

import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;

public class FlowStudentApply {
    private Integer id;

    private Integer flowmodeltypeid;

    private Integer studid;

    private Integer classid1;

    private Integer classid2;

    private String stratTime;

    private String studwork;

    private String updateTime;

    private String createTime;

    private String remark;

    private Integer status;

    private Integer upset;
    
    private Student student;
    
    private StudentClass studentClass;
    
    private StudentClass studentClass1; 

	public StudentClass getStudentClass1() {
		return studentClass1;
	}

	public void setStudentClass1(StudentClass studentClass1) {
		this.studentClass1 = studentClass1;
	}

	public StudentClass getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(StudentClass studentClass) {
		this.studentClass = studentClass;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlowmodeltypeid() {
        return flowmodeltypeid;
    }

    public void setFlowmodeltypeid(Integer flowmodeltypeid) {
        this.flowmodeltypeid = flowmodeltypeid;
    }

    public Integer getStudid() {
        return studid;
    }

    public void setStudid(Integer studid) {
        this.studid = studid;
    }

    public Integer getClassid1() {
        return classid1;
    }

    public void setClassid1(Integer classid1) {
        this.classid1 = classid1;
    }

    public Integer getClassid2() {
        return classid2;
    }

    public void setClassid2(Integer classid2) {
        this.classid2 = classid2;
    }

    public String getStudwork() {
        return studwork;
    }

    public void setStudwork(String studwork) {
        this.studwork = studwork == null ? null : studwork.trim();
    }

    public String getStratTime() {
		return stratTime;
	}

	public void setStratTime(String stratTime) {
		this.stratTime = stratTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUpset() {
        return upset;
    }

    public void setUpset(Integer upset) {
        this.upset = upset;
    }
}