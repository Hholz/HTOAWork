package com.ht.controller.sysSet;

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
import com.ht.popj.sysSet.ApprovalTitle;
import com.ht.popj.sysSet.ApproveDot;
import com.ht.popj.sysSet.FlowModel;
import com.ht.service.sysSet.ApprovalTitleService;
import com.ht.service.sysSet.ApproveDotService;
import com.ht.util.ResultMessage;
/*
 * ����������¼
 */
@Controller
@RequestMapping("/sysSet/Approvaltitle")
public class ApprovalTitleController {
	
	
	@Autowired
	ApprovalTitleService aptitleservice;
	
	@Autowired
	DepMapper depmapper;
	@Autowired
	EmpMapper empmapper;
	@Autowired
	FlowModelMapper flodmapper;
	
	//����������¼ҳ��
	@RequestMapping("/ApprovaltitleList")
	@SystemControllerLog(description = "����������¼ҳ��")
	public String studentList(Model model) {
		List<Dep> deplist =  depmapper.selectDepList();
		FlowModel f = new FlowModel();
		List<FlowModel> flowlist = flodmapper.selectByName(f);
		model.addAttribute("deplist", deplist);
		model.addAttribute("flowlist", flowlist);
		return "/sysSet/ApprovalTitleList";
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
	
	//����������¼
	@RequestMapping("/add")
	@SystemControllerLog(description = "����������¼")
	public @ResponseBody int add(Model model, ApprovalTitle apptitle) {
		int a = aptitleservice.insertSelective(apptitle);
		return a;
	}
	
	//����������¼��json����
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "����������¼��json����")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			ApprovalTitle apptitle) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);

		List<ApprovalTitle> flowModelist = aptitleservice.selectByName(apptitle);

		PageInfo<ApprovalTitle> pageInfo = new PageInfo<ApprovalTitle>(flowModelist);
		long total = pageInfo.getTotal();
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(flowModelist);
		return rm;
	}
	
	//�޸�������¼
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸�������¼")
	public @ResponseBody int updata(Model model, ApprovalTitle apptitle) {
		aptitleservice.updateByPrimaryKeySelective(apptitle);

		return 1;
	}
	
	//ɾ��������¼
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ��������¼")
	public @ResponseBody int delete(Model model, Integer id) {
		aptitleservice.deleteByPrimaryKey(id);
		return 1;
	}
	
}
