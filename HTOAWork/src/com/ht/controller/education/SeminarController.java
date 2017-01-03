package com.ht.controller.education;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.education.EduSeminar;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.education.SeminarService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/seminar")
public class SeminarController {

	@Autowired
	SeminarService seminarService;
	@Autowired
	EmpService empservice;
	@Autowired
	DepService depservice;
	private String deptree;
	// �����б�ҳ��
	@RequestMapping("/list")
	@SystemControllerLog(description="�������ֻ�֪ͨҳ��")
	public String feedBackStartList(Model model) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = new Emp();
		if(null!=userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		model.addAttribute("empId", emp.getId());
		List<Emp> allEmp = empservice.selectEmp(new Emp());
		model.addAttribute("allEmp",allEmp);
		String deptree = findemp(0);
		model.addAttribute("deptree",deptree);
		return "/education/seminar_start";
	}

	// bootstrop table ���url������ȡJson����
	@RequestMapping("/seminarlist")
	@SystemControllerLog(description="��ȡ���ֻ�֪ͨ���json����")
	public @ResponseBody ResultMessage list(int limit, int offset, Model model, EduSeminar seminar) {
		ResultMessage rm = new ResultMessage();
		List<EduSeminar> sList = new ArrayList<EduSeminar>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if (seminar != null) {
			sList = seminarService.getSomeSeminar(seminar);
			// System.out.println("��ʱ����Ϊ��" + seminar);
		} else {
			sList = seminarService.getAllSeminar();
		}
		// ȡ��ҳ��Ϣ
		PageInfo<EduSeminar> pageInfo = new PageInfo<EduSeminar>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}

	@RequestMapping("/add")
	@SystemControllerLog(description="�������ֻ�֪ͨ")
	public @ResponseBody int add(Model model, EduSeminar seminar) {
		if (seminar != null) {
			seminar.setCreateTime(new Date().toLocaleString());
			int count = seminarService.addSeminar(seminar);
			return count;
		}
		return 0;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description="�޸����ֻ�֪ͨ")
	public @ResponseBody int update(Model model, EduSeminar seminar) {
		if (null != seminar) {
			seminar.setUpdateTime(new Date().toLocaleString());
			int count = seminarService.updateSeminar(seminar);
			return count;
		}
		return 0;
	}

	// ɾ��ѧ��
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description="ɾ�����ֻ�֪ͨ")
	public @ResponseBody int delete(Model model, @PathVariable("id") Integer id) {
		int count = seminarService.deleteSeminar(id);
		return count;
	}
	
	@RequestMapping(value = "/findemp/{depid}", method = { RequestMethod.GET }) 
	@SystemControllerLog(description="���ز�����json��")
	public String findemp(@PathVariable("depid") int depid) { 
		deptree="";
		List<Dep> childendep = depservice.selectChildenDep(depid);
		childendep(childendep);
		deptree=deptree.replace("\'","\"");
		//model.addAttribute("deptree", deptree);
		return deptree; 
	}
	private void childendep(List<Dep> childdep){
		if(null!=childdep && childdep.size()>0){
			deptree+="[";
			for(int i=0;i<childdep.size();i++){
				deptree+="{'id':"+childdep.get(i).getId();
				deptree+=",'text':'"+childdep.get(i).getDepname()+"'";
				deptree+=",'level':0";
				deptree+=",'tag':0";
				List<Dep> childendep = depservice.selectChildenDep(childdep.get(i).getId());
				if(null!=childendep && childendep.size()>0){
					deptree+=",'nodes':";
					childendep(childendep);
				}
				deptree+="}";
				if(i!=(childdep.size()-1)){
					deptree+=",";
				}
			}
			deptree+="]";
		}
	}

	@RequestMapping(value = "/findemp/{depid}", method = { RequestMethod.PUT }) 
	@SystemControllerLog(description="ͨ������ID��ȡԱ����Ϣ")
	private @ResponseBody ResultMessage initemp(@PathVariable("depid") int depid){
		ResultMessage rm = new ResultMessage();
		Emp emp=new Emp();
		emp.setDepid(depid);
		List<Emp> emplist=new ArrayList<>();
		emplist=empservice.selectEmp(emp);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	
}
