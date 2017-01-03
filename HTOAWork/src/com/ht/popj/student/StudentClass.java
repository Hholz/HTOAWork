package com.ht.popj.student;

import java.util.Date;
import java.util.List;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.education.EduMajor;

public class StudentClass {
    private Integer id;

    private String classno;

    private String classname;

    private Integer count;

    private String teacherId;

    private String leaderId;

    private String clteacherId;

    private String addr;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String remark;

    private String levelId;
    
    private Integer majorId;
    
    private Integer clsStatus;
    
    private StudentFall studentfall;
    
    private Emp emp;
    
    private Emp empl;
    
    private Emp empc;
    
    private int countstu;//正常班现有人数
    private int counttest;//测试班现有人数
    
    private EduMajor eduMajor;
    
    private List<Student> student;
    
	public List<Student> getStudent() {
		return student;
	}

	public void setStudent(List<Student> student) {
		this.student = student;
	}

	public int getCountstu() {
		return countstu;
	}

	public void setCountstu(int countstu) {
		this.countstu = countstu;
	}

	public Emp getEmpl() {
		return empl;
	}

	public void setEmpl(Emp empl) {
		this.empl = empl;
	}

	public Emp getEmpc() {
		return empc;
	}

	public void setEmpc(Emp empc) {
		this.empc = empc;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public StudentFall getStudentfall() {
		return studentfall;
	}

	public void setStudentfall(StudentFall studentfall) {
		this.studentfall = studentfall;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassno() {
        return classno;
    }

    public void setClassno(String classno) {
        this.classno = classno == null ? null : classno.trim();
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname == null ? null : classname.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId == null ? null : leaderId.trim();
    }

    public String getClteacherId() {
        return clteacherId;
    }

    public void setClteacherId(String clteacherId) {
        this.clteacherId = clteacherId == null ? null : clteacherId.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId == null ? null : levelId.trim();
    }

	public Integer getMajorId() {
		return majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}

	public Integer getClsStatus() {
		return clsStatus;
	}

	public void setClsStatus(Integer clsStatus) {
		this.clsStatus = clsStatus;
	}

	public EduMajor getEduMajor() {
		return eduMajor;
	}

	public void setEduMajor(EduMajor eduMajor) {
		this.eduMajor = eduMajor;
	}

	public int getCounttest() {
		return counttest;
	}

	public void setCounttest(int counttest) {
		this.counttest = counttest;
	}
}