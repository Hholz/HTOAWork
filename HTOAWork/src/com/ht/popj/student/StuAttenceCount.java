package com.ht.popj.student;

/*
 * ѧ������ͳ��
 */
public class StuAttenceCount {

	private int stuId;//ѧ��id
	private int clsId;//�༶id
	private int normal;//��������
	private int late;//�ٵ�����
	private int truant;//���δ���
	private int leave;//��ٴ���
	private String createTime;//2016-12 ĳ��
	
	private Student stu;
	private String clsName;
	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public int getClsId() {
		return clsId;
	}
	public void setClsId(int clsId) {
		this.clsId = clsId;
	}
	public int getNormal() {
		return normal;
	}
	public void setNormal(int normal) {
		this.normal = normal;
	}
	public int getLate() {
		return late;
	}
	public void setLate(int late) {
		this.late = late;
	}
	public int getTruant() {
		return truant;
	}
	public void setTruant(int truant) {
		this.truant = truant;
	}
	public int getLeave() {
		return leave;
	}
	public void setLeave(int leave) {
		this.leave = leave;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Student getStu() {
		return stu;
	}
	public void setStu(Student stu) {
		this.stu = stu;
	}
	public String getClsName() {
		return clsName;
	}
	public void setClsName(String clsName) {
		this.clsName = clsName;
	}
}
