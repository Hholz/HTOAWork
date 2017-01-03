package com.ht.controller.student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.popj.student.StuAttenceCount;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentAttence;
import com.ht.popj.student.StudentClass;
import com.ht.service.student.StudentAttenceService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
import com.ht.util.DateUtil;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;
/*
 * ѧ������
 */
@Controller
@RequestMapping("/student/attence")
public class StudentAttenceController {
	
	@Autowired
	StudentAttenceService stuAttenService;
	@Autowired
	StudentService studentService;//ѧ����
	@Autowired
	StudentInfoService studentInfoService;//���Ͱ༶��service
	
	@RequestMapping("/page")
	public String page(Model model){
		List<StudentClass> clsList = new ArrayList<StudentClass>();
		//��Ӧ�����˵Ĺ���İ༶
		if(null != UserInfoUtil.getEmp()){
			//��ѯ��ǰ�����εķǱ�ҵ�༶
			clsList = studentInfoService.selectOnByClteac(UserInfoUtil.getEmp().getId());
		}
		model.addAttribute("clsList", clsList);
		return "student/studentAttence";
	}
	@RequestMapping("/countPage")
	public String countPage(Model model){
		List<StudentClass> clsList = new ArrayList<StudentClass>();
		//��Ӧ�����˵Ĺ���İ༶
		if(null != UserInfoUtil.getEmp()){
			//��ѯ��ǰ�����εķǱ�ҵ�༶
			clsList = studentInfoService.selectOnByClteac(UserInfoUtil.getEmp().getId());
		}
		model.addAttribute("clsList", clsList);
		return "student/studentAttenceStatistics";
	}
	@RequestMapping("/attencelistJson")
	public @ResponseBody ResultMessage saystudentheart(int limit, int offset,Model model,StudentAttence attence) throws Exception{
		String createTime = attence.getCreateTime();
		if(null==createTime||"".equals(createTime)){
			createTime = DateUtil.DateToString(new Date()).substring(0, 10);
			attence.setCreateTime(createTime);
		}
		
		ResultMessage rm = new ResultMessage();
		List<StudentAttence> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = stuAttenService.selectAllByPJ(attence);
		 // ȡ��ҳ��Ϣ
        PageInfo<StudentAttence> pageInfo = new PageInfo<StudentAttence>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	//����ĳ��һ��������п��ڣ�Ĭ�϶�������
	@RequestMapping("/createTodayAttence")
	public @ResponseBody int createTodayAttence(Integer clsId,String createTime){
		//��ʱ��δ��ʱ��Ĭ������δ����
		if(null==createTime||"".equals(createTime)){
			createTime = DateUtil.DateToString(new Date()).substring(0, 10);
		}
		//�鿴����ð��Ƿ������ɿ���
		if(stuAttenService.isExistTheDate(clsId,createTime)){
			return -1;
		}
		//ͨ���༶�Ż�ȡ�ð����е�ѧ��
		List<Student> stuList = studentService.selectByclsId(clsId);
		//��ÿһ��ѧ��������������п��ڣ�Ĭ�϶�������
		return stuAttenService.createOneDayAttence(stuList,createTime);
	}
	//�޸�һ������״̬(���ڱ�id��ʱ�䣨������������������������ٵ�..��)
	@RequestMapping("/upOnetimeStatus")
	public @ResponseBody int upOnetimeStatus(Integer id,String time,String state){
		StudentAttence attence = new StudentAttence();
		attence.setId(id);
		if(time.equals("morning")){
			attence.setMorning(state);
		}else if(time.equals("forenoon")){
			attence.setForenoon(state);
		}else if(time.equals("afternoon")){
			attence.setAfternoon(state);
		}else if(time.equals("night")){
			attence.setNight(state);
		}
		return stuAttenService.updateByPJ(attence);
	}
	//����ͳ�Ʊ�json
	/**
	 * @param limit
	 * @param offset
	 * @param model
	 * @param clsId
	 * @param createTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/stuAttenceCountlistJson")
	public @ResponseBody ResultMessage stuAttenceCountlistJson(int limit, int offset,Model model,Integer clsId,String createTime) throws Exception{
		if(null==createTime||"".equals(createTime)){
			createTime = DateUtil.DateToString(new Date()).substring(0, 7);
		}
		ResultMessage rm = new ResultMessage();
		List<StuAttenceCount> sacList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sacList = stuAttenService.CountAttenByclsId(clsId, createTime);
		//ȡ��ҳ��Ϣ
        PageInfo<StuAttenceCount> pageInfo = new PageInfo<StuAttenceCount>(sacList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sacList);
		return rm;
	}
	//һ��ѧ��һ���µĿ�����ϸ
	@RequestMapping("/stuMonthTbJson")
	public @ResponseBody ResultMessage stuMonth(Model model,Integer stuId,String createTime) throws Exception{
		if(null==createTime||"".equals(createTime)){
			createTime = DateUtil.DateToString(new Date()).substring(0, 7);
		}
		ResultMessage rm = new ResultMessage();
		List<StudentAttence> saList = new ArrayList<StudentAttence>();
		saList = stuAttenService.selectMonthBystuId(stuId, createTime);
		rm.setRows(saList);
		return rm;
	}
}
