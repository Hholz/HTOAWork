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
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Familyinfo;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.dailyWork.FamilyinfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork/family")
public class FamilyController {
	@Autowired
	FamilyinfoService familyservice;

	@Autowired
	EmpService empservice;

	@RequestMapping("/familylist")
	@SystemControllerLog(description = "����Ա����ͥ��Ϣҳ��")
	public String selectempList(Model model) {
		List<Emp> sList = new ArrayList<>();
		Emp emp = new Emp();
		sList = empservice.selectEmp(emp);
		model.addAttribute("emp", sList);
		return "dailyWork/EmpInfo";
	}

	// ��ѯ��ͥ��Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "��ѯԱ����ͥ��Ϣ")
	public @ResponseBody ResultMessage empList(@PathVariable("id") String id, Integer limit, Integer offset,
			Familyinfo family) {
		ResultMessage rm = new ResultMessage();
		List<Familyinfo> sList = new ArrayList<>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = familyservice.selectFamilyinfo(family);
		PageInfo<Familyinfo> pageInfo = new PageInfo<Familyinfo>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}

	// ������ͥ��Ϣ
	@RequestMapping(value = "/add")
	@SystemControllerLog(description = "����Ա����ͥ��Ϣ")
	public @ResponseBody int addemp(Familyinfo family) {
		family.setCreateTime(new Date());
		family.setUpdateTime(new Date());
		int count=0;
		List<Familyinfo> list=new ArrayList<>();
		if(!(family.getRelationship().equals("����"))){
			Familyinfo f=new Familyinfo();
			f.setEmpid(family.getEmpid());
			f.setRelationship(family.getRelationship());
			list=familyservice.selectFamilyinfo(f);
		}
		if(null!=list && list.size()>0){
			count=-1;
		}else{
			count = familyservice.insert(family);
		}
		return count;
	}

	// �޸ļ�ͥ��Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸�Ա����ͥ��Ϣ")
	public @ResponseBody int updateemp(@PathVariable("id") String id, Familyinfo family) {
		family.setUpdateTime(new Date());
		int count = familyservice.updateByPrimaryKey(family);
		return count;
	}

	// ɾ����ͥ��Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ��Ա����ͥ��Ϣ")
	public @ResponseBody int seleteemp(@PathVariable("id") Integer id) {
		int count = familyservice.deleteByPrimaryKey(id);
		return count;
	}
}
