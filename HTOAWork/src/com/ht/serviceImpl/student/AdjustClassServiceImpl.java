package com.ht.serviceImpl.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.AdjustClassMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.mapper.student.StudentMapper;
import com.ht.popj.student.AdjustClass;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.service.student.AdjustClassService;

public class AdjustClassServiceImpl implements AdjustClassService{

	@Autowired
	AdjustClassMapper adjustClassMapper;
	@Autowired
	StudentClassMapper studentClassMapper;
	@Autowired
	StudentMapper studentMapper;
	@Override
	public int deleteById(Integer id) {
		return adjustClassMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(AdjustClass record) {
		return adjustClassMapper.insertSelective(record);
	}

	@Override
	public AdjustClass selectById(Integer id) {
		return adjustClassMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(AdjustClass record) {
		return adjustClassMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<AdjustClass> selectbyStuId(Integer stuId) {
		return adjustClassMapper.selectbyStuId(stuId);
	}

	/*
	 * 通过员工的id来查他所教的班的所有学生的请假记录
	 * @see com.ht.service.student.AdjustClassService#selectbyTeacId(java.lang.String)
	 */
	@Override
	public List<AdjustClass> selectbyTeacId(String empId) {
		StudentClass cls = new StudentClass();
		cls.setClteacherId(empId);
		//通过员工id来查他教那些班级
		List<StudentClass> clsList = studentClassMapper.selectStudentclass(cls);
		//通过班级id来查出该员工（老师）教的所有学生
		Map map = new HashMap();
		List<Integer> clsIds = new ArrayList<Integer>();
		//获取到班级id
		for(StudentClass sc:clsList){
			clsIds.add(sc.getId());
		}
		clsIds.add(0);
		map.put("clsIds", clsIds);
		//取出这些班的学生
//		List<Student> stuList = studentMapper.selectByClsIds(map);
//		List<Integer> ids = new ArrayList<Integer>();//保存学生id
//		for(Student s:stuList){
//			ids.add(s.getId());
//		}
//		Map map2 = new HashMap();
//		//保存id到map
//		map2.put("ids", ids);
		//通过学生id来查请假表
//		return adjustClassMapper.selectbyStuIds(map2);
		return adjustClassMapper.selectAllbyClsId(map);
	}

	@Override
	public List<AdjustClass> selectbyTeacIdNodel(String empId) {
		StudentClass cls = new StudentClass();
		cls.setClteacherId(empId);
		//通过员工id来查他教那些班级
		List<StudentClass> clsList = studentClassMapper.selectStudentclass(cls);
		//通过班级id来查出该员工（老师）教的所有学生
		Map map = new HashMap();
		List<Integer> clsIds = new ArrayList<Integer>();
		//获取到班级id
		for(StudentClass sc:clsList){
			clsIds.add(sc.getId());
		}
		clsIds.add(0);
		map.put("clsIds", clsIds);
//		//取出这些班的学生
//		List<Student> stuList = studentMapper.selectByClsIds(map);
//		List<Integer> ids = new ArrayList<Integer>();//保存学生id
//		for(Student s:stuList){
//			ids.add(s.getId());
//		}
//		Map map2 = new HashMap();
//		//保存id到map
//		map2.put("ids", ids);
//		//通过学生id来查请假表
//		return adjustClassMapper.selectbyStuIdsNodel(map2);
		return adjustClassMapper.selectbyClsIdsNodel(map);
	}
	@Override
	public List<AdjustClass> selectbyTeacIdNodel2(String empId) {
		StudentClass cls = new StudentClass();
		cls.setClteacherId(empId);
		//通过员工id来查他教那些班级
		List<StudentClass> clsList = studentClassMapper.selectStudentclass(cls);
		//通过班级id来查出该员工（老师）教的所有学生
		Map map = new HashMap();
		List<Integer> clsIds = new ArrayList<Integer>();
		//获取到班级id
		for(StudentClass sc:clsList){
			clsIds.add(sc.getId());
		}
		clsIds.add(0);
		map.put("clsIds", clsIds);
		return adjustClassMapper.selectbyToClsIdsNodel(map);
	}
}
