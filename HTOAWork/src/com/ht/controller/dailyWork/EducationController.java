package com.ht.controller.dailyWork;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Education;
import com.ht.popj.dailyWork.Emp;
import com.ht.service.dailyWork.EducationService;
import com.ht.service.dailyWork.EmpService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork/edu")
public class EducationController {
	@Autowired
	EducationService eduservice;
	
	@Autowired
	EmpService empservice;
	
	@RequestMapping("/edulist")
	@SystemControllerLog(description = "����Ա������������Ϣҳ��")
	public String selectempList(Model model){
		List<Emp> sList = new ArrayList<>();
		Emp emp=new Emp();
		sList=empservice.selectEmp(emp);
		model.addAttribute("emp",sList);
		return "dailyWork/Education";
	}
	
	//��ѯ��������
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST }) 
	@SystemControllerLog(description = "��ѯԱ������������Ϣ")
	public @ResponseBody ResultMessage empList(@PathVariable("id") String id,Integer limit, Integer offset,Education edu) { 
		ResultMessage rm = new ResultMessage();
		List<Education> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = eduservice.selectEducation(edu);
        PageInfo<Education> pageInfo = new PageInfo<Education>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm; 
	}
	
	//������������
	@RequestMapping(value = "/add") 
	@SystemControllerLog(description = "����Ա������������Ϣ")
	public @ResponseBody int addemp(Education edu) { 
		edu.setCreateTime(new Date());
		edu.setUpdateTime(new Date());
		int count=eduservice.insert(edu);
		return count; 
	}
	
	//�޸Ľ�������
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT }) 
	@SystemControllerLog(description = "�޸�Ա������������Ϣ")
	public @ResponseBody int updateemp(@PathVariable("id") String id,Education edu) { 
		edu.setUpdateTime(new Date());
		int count=eduservice.updateByPrimaryKey(edu);
		return count; 
	}
	
	//ɾ����������
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE }) 
	@SystemControllerLog(description = "ɾ��Ա������������Ϣ")
	public @ResponseBody int seleteemp(@PathVariable("id") Integer id) { 
		int count=eduservice.deleteByPrimaryKey(id);
		return count; 
	}
}
