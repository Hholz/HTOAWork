package com.ht.controller.sysSet;

import java.util.ArrayList;
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
import com.ht.mapper.dailyWork.DepMapper;
import com.ht.mapper.dailyWork.EmpMapper;
import com.ht.mapper.sysSet.FlowModelMapper;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.sysSet.ApproveDot;
import com.ht.popj.sysSet.ApproveType;
import com.ht.popj.sysSet.FlowModel;
import com.ht.popj.sysSet.FlowModelType;
import com.ht.service.sysSet.ApproveDotService;
import com.ht.service.sysSet.ApproveTypeService;
import com.ht.util.ResultMessage;
/*
 * ���������ڵ�
 */
@Controller
@RequestMapping("/sysSet/ApprovetDot")
public class ApprovetDotController {
	
	@Autowired
	ApproveDotService apdotservice;
	
	@Autowired
	DepMapper depmapper;
	@Autowired
	EmpMapper empmapper;
	@Autowired
	FlowModelMapper flodmapper;
	
	List<ApproveDot> dotlist = new ArrayList<ApproveDot>();
	List<FlowModel> flowlist = new ArrayList<FlowModel>();
	List<Dep> deplist = new ArrayList<Dep>();
	
	
	//�������̽ڵ�ҳ��
	@RequestMapping("/ApprovetDotList")
	@SystemControllerLog(description = "�������̽ڵ�ҳ��")
	public String studentList(Model model) {
		selectlist();
		model.addAttribute("deplist", deplist);
		model.addAttribute("dotlist", dotlist);
		model.addAttribute("flowlist", flowlist);
		return "/sysSet/ApprovetDotList";
	}
	public void selectlist(){
		ApproveDot dot = new ApproveDot();
		dotlist =  apdotservice.selectByName(dot);
		deplist =  depmapper.selectDepList();
		FlowModel f = new FlowModel();
		flowlist = flodmapper.selectByName(f);
	}
	
	//������ѡ���ŵ�����Ա����Json����
	@RequestMapping("/findemp")
	@SystemControllerLog(description = "������ѡ���ŵ�����Ա����Json����")
	public @ResponseBody ResultMessage findemp(Model model,Dep dep) {
		Emp e = new Emp();
		ResultMessage rm = new ResultMessage();
		e.setDepid(dep.getId());
		List<Emp> emplist =  empmapper.selectEmp(e);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	
	//���������ڵ�
	@RequestMapping("/add")
	@SystemControllerLog(description = "���������ڵ�")
	public @ResponseBody int add(Model model, ApproveDot appdot) {
		int a = apdotservice.insertSelective(appdot);
		selectlist();
		model.addAttribute("deplist", deplist);
		model.addAttribute("dotlist", dotlist);
		model.addAttribute("flowlist", flowlist);
		return a;
	}
	
	//���������ڵ��json����
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "���������ڵ��json����")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			ApproveDot appdot) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);

		List<ApproveDot> flowModelist = apdotservice.selectByName(appdot);
		List<ApproveDot> flsit = apdotservice.selectlist();
		for(int i=0;i<flowModelist.size();i++){
			ApproveDot a =flowModelist.get(i);
			if(a.getLastdot() == null){
				continue;
			}
			int lastdot = a.getLastdot();
			for(int j=0;j<flsit.size();j++){
				ApproveDot b = flsit.get(j);
				if(b.getId() == lastdot){
					a.setLastdotname(b.getDotname());
					flowModelist.set(i, a);
					continue;
				}
			}
		}
		
		
		
		
		PageInfo<ApproveDot> pageInfo = new PageInfo<ApproveDot>(flowModelist);
		long total = pageInfo.getTotal();
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(flowModelist);
		return rm;
	}
	
	//�޸������ڵ�
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸������ڵ�")
	public @ResponseBody int updata(Model model, ApproveDot appdot) {
		apdotservice.updateByPrimaryKeySelective(appdot);

		return 1;
	}
	
	//ɾ�������ڵ�
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ�������ڵ�")
	public @ResponseBody int delete(Model model, Integer id) {
		apdotservice.deleteByPrimaryKey(id);
		return 1;
	}
	
}
