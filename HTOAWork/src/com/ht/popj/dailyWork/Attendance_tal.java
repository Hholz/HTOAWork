package com.ht.popj.dailyWork;

public class Attendance_tal {
	private Integer id;

    private int fingerprint;
    
    private String  empname;
    
    private int depid;

    private String workday;

    private Integer status;
    
    private Integer late;

    private String type;
    
    private int attenstatus;
    
    private float absent;
    
    private Dep dep; 
    
    private Integer num;
    
    
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public float getAbsent() {
		return absent;
	}

	public void setAbsent(float absent) {
		this.absent = absent;
	}

	public int getAttenstatus() {
		return attenstatus;
	}

	public void setAttenstatus(int attenstatus) {
		this.attenstatus = attenstatus;
	}

	public Dep getDep() {
		return dep;
	}

	public void setDep(Dep dep) {
		this.dep = dep;
	}

	public Integer getLate() {
		return late;
	}

	public void setLate(Integer late) {
		this.late = late;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(int fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public int getDepid() {
		return depid;
	}

	public void setDepid(int depid) {
		this.depid = depid;
	}

	public String getWorkday() {
		return workday;
	}

	public void setWorkday(String workday) {
		this.workday = workday;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Attendance_tal [id=" + id + ", fingerprint=" + fingerprint
				+ ", empname=" + empname + ", depid=" + depid + ", workday="
				+ workday + ", status=" + status + ", late=" + late + ", type="
				+ type + "]";
	}
	
}
