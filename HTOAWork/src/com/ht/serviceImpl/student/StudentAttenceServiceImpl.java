package com.ht.serviceImpl.student;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentAttenceMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.mapper.student.StudentMapper;
import com.ht.popj.student.StuAttenceCount;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentAttence;
import com.ht.popj.student.StudentClass;
import com.ht.service.student.StudentAttenceService;
import com.ht.util.DateUtil;

public class StudentAttenceServiceImpl implements StudentAttenceService{

	@Autowired
	StudentAttenceMapper attenMapper;
	@Autowired
	StudentMapper studentMapper;
	@Autowired
	StudentClassMapper studentClassMapper;
	
	public int deleteById(Integer id) {
		return attenMapper.deleteByPrimaryKey(id);
	}

	public int insertByPJ(StudentAttence attence) {
		return attenMapper.insertSelective(attence);
	}

	public StudentAttence selectById(Integer id) {
		return attenMapper.selectByPrimaryKey(id);
	}

	public int updateByPJ(StudentAttence attence) {
		return attenMapper.updateByPrimaryKeySelective(attence);
	}

	public List<StudentAttence> selectAll() {
		return attenMapper.selectAll();
	}

	public List<StudentAttence> selectBystuId(Integer stuId) {
		return attenMapper.selectBystuId(stuId);
	}

	public List<StudentAttence> selectByclsId(Integer clsId) {
		return attenMapper.selectByclsId(clsId);
	}

	public List<StudentAttence> selectAllByPJ(StudentAttence attence) {
		return attenMapper.selectAllByPJ(attence);
	}

	@Override
	public int createOneDayAttence(List<Student> stuList, String createTime) {
		StudentAttence attence = new StudentAttence();
		for(Student stu : stuList){
			attence.setStuId(stu.getId());
			attence.setClsId(stu.getClassid());
			attence.setMorning("正常");
			attence.setForenoon("正常");
			attence.setAfternoon("正常");
			attence.setNight("正常");
			attence.setCreateTime(createTime);
			attenMapper.insertSelective(attence);
		}	
		return 1;
	}

	public boolean isExistTheDate(Integer clsId, String createTime) {
		Map map = new HashMap();
		map.put("clsId", clsId);
		map.put("createTime", createTime);
		int count = attenMapper.isExistTheDate(map);
		if(count>0){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public int sumByStuIdTime(Integer stuId, String state, String createTime) {
		Map map = new HashMap();
		map.put("stuId", stuId);
		map.put("state", state);
		map.put("createTime", createTime);
		return attenMapper.sumByStuIdTime(map);
	}
	public List<StuAttenceCount> CountAttenByclsId(Integer clsId,String createTime){
		StudentClass cls = studentClassMapper.selectById(clsId);
		String clsName = cls.getClassname();
		List<StuAttenceCount> sacList = new ArrayList<StuAttenceCount>();
		//通过班级号获取该班所有的学生
		List<Student> stuList = studentMapper.selectByclsId(clsId);
		for(Student stu : stuList){
			StuAttenceCount sac = new StuAttenceCount();
			sac.setClsId(clsId);
			sac.setStuId(stu.getId());
			//正常
			Map normalMap = new HashMap();
			normalMap.put("stuId", stu.getId());
			normalMap.put("state", "正常");
			normalMap.put("createTime", createTime);
			sac.setNormal(attenMapper.sumByStuIdTime(normalMap));
			//迟到
			Map lateMap = new HashMap();
			lateMap.put("stuId", stu.getId());
			lateMap.put("state", "迟到");
			lateMap.put("createTime", createTime);
			sac.setLate(attenMapper.sumByStuIdTime(lateMap));
			//旷课
			Map truantMap = new HashMap();
			truantMap.put("stuId", stu.getId());
			truantMap.put("state", "旷课");
			truantMap.put("createTime", createTime);
			sac.setTruant(attenMapper.sumByStuIdTime(truantMap));
			//请假
			Map leaveMap = new HashMap();
			leaveMap.put("stuId", stu.getId());
			leaveMap.put("state", "请假");
			leaveMap.put("createTime", createTime);
			sac.setLeave(attenMapper.sumByStuIdTime(leaveMap));
			
			sac.setStu(stu);
			sac.setClsName(clsName);
			sac.setCreateTime(createTime);
			sacList.add(sac);
		}	
		return sacList;
	}

	public List<StudentAttence> selectMonthBystuId(Integer stuId, String createTime) {
		Map map = new HashMap();
		map.put("stuId", stuId);
		map.put("createTime", createTime);
		return attenMapper.selectMonthBystuId(map);
	}
}
