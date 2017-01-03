package com.ht.serviceImpl.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StuHolidayMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.mapper.student.StudentMapper;
import com.ht.popj.student.StuHoliday;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.service.student.StuHolidayService;

public class StuHolidayServiceImpl implements StuHolidayService{
	
	@Autowired
	StudentClassMapper studentClassMapper;
	@Autowired
	StudentMapper studentMapper;
	@Autowired
	StuHolidayMapper sutHolidayMapper;
	@Override
	public int deleteById(Integer id) {
		return sutHolidayMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(StuHoliday stuHoliday) {
		return sutHolidayMapper.insertSelective(stuHoliday);
	}

	@Override
	public StuHoliday selectById(Integer id) {
		return sutHolidayMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(StuHoliday stuHoliday) {
		return sutHolidayMapper.updateByPrimaryKeySelective(stuHoliday);
	}

	@Override
	public int delByUpdate(Integer id) {
		return sutHolidayMapper.delByUpdate(id);
	}

	@Override
	public List<StuHoliday> selectAll() {
		return sutHolidayMapper.selectAll();
	}

	@Override
	public List<StuHoliday> selectByStuId(Integer stuId) {
		return sutHolidayMapper.selectByStuId(stuId);
	}

	@Override
	public List<StuHoliday> selectByEmpId(String empId) {
		StudentClass cls = new StudentClass();
		cls.setClteacherId(empId);
		//ͨ��Ա��id����������Щ�༶
		List<StudentClass> clsList = studentClassMapper.selectStudentclass(cls);
		//ͨ���༶id�������Ա������ʦ���̵�����ѧ��
		Map map = new HashMap();
		List<Integer> clsIds = new ArrayList<Integer>();
		//��ȡ���༶id
		for(StudentClass sc:clsList){
			clsIds.add(sc.getId());
		}
		clsIds.add(0);//��ֹΪ�գ�sql����
		map.put("clsIds", clsIds);
		//ȡ����Щ���ѧ��
		List<Student> stuList = studentMapper.selectByClsIds(map);
		List<Integer> ids = new ArrayList<Integer>();//����ѧ��id
		for(Student s:stuList){
			ids.add(s.getId());
		}
		ids.add(0);//��ֹΪ�գ�sql����
		Map map2 = new HashMap();
		//����id��map
		map2.put("ids", ids);
		//ͨ��ѧ��id������ٱ�
		return sutHolidayMapper.selectbyStuIds(map2);
	}
	@Override
	public List<StuHoliday> selectByEmpIdNodel(String empId) {
		StudentClass cls = new StudentClass();
		cls.setClteacherId(empId);
		//ͨ��Ա��id����������Щ�༶
		List<StudentClass> clsList = studentClassMapper.selectStudentclass(cls);
		//ͨ���༶id�������Ա������ʦ���̵�����ѧ��
		Map map = new HashMap();
		List<Integer> clsIds = new ArrayList<Integer>();
		//��ȡ���༶id
		for(StudentClass sc:clsList){
			clsIds.add(sc.getId());
		}
		clsIds.add(0);//��ֹΪ�գ�sql����
		map.put("clsIds", clsIds);
		//ȡ����Щ���ѧ��
		List<Student> stuList = studentMapper.selectByClsIds(map);
		List<Integer> ids = new ArrayList<Integer>();//����ѧ��id
		for(Student s:stuList){
			ids.add(s.getId());
		}
		ids.add(0);//��ֹΪ�գ�sql����
		Map map2 = new HashMap();
		//����id��map
		map2.put("ids", ids);
		//ͨ��ѧ��id������ٱ�
		return sutHolidayMapper.selectbyStuIdsNoDel(map2);
	}

}
