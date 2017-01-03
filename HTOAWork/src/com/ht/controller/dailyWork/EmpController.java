package com.ht.controller.dailyWork;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
import com.ht.popj.login.ShiroUser;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.login.ShiroUserRole;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.login.ShiroUserService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork")
public class EmpController {
	@Autowired
	EmpService empservice;
	
	@Autowired
	DepService depservice;
	
	@Autowired
	StudentInfoService studentInfoService;
	private String deptree;
	private String falltree;
	
	@Autowired
	ShiroUserService userService;
	
	@RequestMapping("/emp/emplist")
	@SystemControllerLog(description = "进入员工信息页面")
	public String selectempList(Model model){
		List<Dep> sList = new ArrayList<>();
		sList=depservice.selectDepList();
		model.addAttribute("dep",sList);
		return "dailyWork/Emp";
	}
	@RequestMapping("/emp/empInfo/{empId}")
	@SystemControllerLog(description = "进入员工信息页面")
	public String empInfo(Model model,@PathVariable("empId") String empId){
		List<Emp> sList = new ArrayList<>();
		Emp emp = new Emp();
		sList = empservice.selectEmp(emp);
		model.addAttribute("emp", sList);
		model.addAttribute("empId", "'"+empId+"'");
		return "dailyWork/EmpInfo";
	}
	
	//查询emp
	@RequestMapping(value = "/emp/{id}", method = { RequestMethod.POST }) 
	@SystemControllerLog(description = "查询员工信息")
	public @ResponseBody ResultMessage empList(@PathVariable("id") String id,Integer limit, Integer offset,Emp emp) { 
		ResultMessage rm = new ResultMessage();
		List<Emp> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		emp.setId("");//由于emp的id值为0,所以要设为空
		sList = empservice.selectEmp(emp);
        PageInfo<Emp> pageInfo = new PageInfo<Emp>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有员工信息：" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm; 
	}
	
	//新增emp
	@RequestMapping(value = "/emp/addemp") 
	@SystemControllerLog(description = "新增员工信息")
	public @ResponseBody int addemp(Emp emp) { 
		emp.setCreateTime(new Date());
		emp.setUpdateTime(new Date());
		//新增员工并生成账号
		int count=empservice.insert(emp);
		return count; 
	}
	
	//修改emp
	@RequestMapping(value = "/emp/{id}", method = { RequestMethod.PUT }) 
	@SystemControllerLog(description = "修改员工信息")
	public @ResponseBody int updateemp(@PathVariable("id") String id,Emp emp) { 
		System.out.println(emp.toString());
		emp.setUpdateTime(new Date());
		int count=empservice.updateByPrimaryKey(emp);
		return count; 
	}
	
	//删除emp
	@RequestMapping(value = "/emp/{id}", method = { RequestMethod.DELETE }) 
	@SystemControllerLog(description = "删除员工信息")
	public @ResponseBody int seleteemp(@PathVariable("id") String id) { 
		int count=empservice.deleteByPrimaryKey(id);
		return count; 
	}
	
	//返回员工json
	@RequestMapping(value = "/findemp", method = { RequestMethod.POST }) 
	@SystemControllerLog(description = "查询员工信息")
	public @ResponseBody ResultMessage empList(Emp emp) { 
		ResultMessage rm = new ResultMessage();
		List<Emp> sList = new ArrayList<>();
		sList = empservice.selectEmp(emp);
		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm; 
	}
	@RequestMapping(value = "/findemp/{depid}", method = { RequestMethod.GET }) 
	public String findemp(@PathVariable("depid") int depid,Model model) { 
		deptree="";
		List<Dep> childendep = depservice.selectChildenDep(depid);
		childendep(childendep);
		deptree=deptree.replace("\'","\"");
		model.addAttribute("deptree", deptree);
		return "education/feedback_start"; 
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
	public @ResponseBody ResultMessage initemp(@PathVariable("depid") int depid){
		ResultMessage rm = new ResultMessage();
		Emp emp=new Emp();
		emp.setDepid(depid);
		List<Emp> emplist=new ArrayList<>();
		emplist=empservice.selectEmp(emp);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	
	@RequestMapping(value = "/findstudentfall/{depid}", method = { RequestMethod.GET }) 
	public String findstudentfall(@PathVariable("depid") int depid,Model model) throws Exception { 
		falltree="[";
		List<StudentFall> childendep = studentInfoService.selectStudentFall();
		for(int i=0;i<childendep.size();i++){
			falltree+="{'id':"+childendep.get(i).getId();
			falltree+=",'text':'"+childendep.get(i).getLevel()+"'";
			falltree+=",'level':0";
			falltree+=",'tag':0";
			falltree+="}";
			if(i!=(childendep.size()-1)){
				falltree+=",";
			}
		}
		falltree+="]";
		falltree=falltree.replace("\'","\"");
		model.addAttribute("falltree", falltree);
		return "education/sele2"; 
	}
	
	@RequestMapping(value = "/findstudentfall/{depid}", method = { RequestMethod.PUT }) 
	public @ResponseBody ResultMessage findclass(@PathVariable("depid") int fallid,Model model){ 
		ResultMessage rm = new ResultMessage();
		List<StudentClass> classlist=new ArrayList<>();
		classlist=studentInfoService.selectStudentclass2(fallid);
		rm.setTotal(classlist.size());
		rm.setRows(classlist);
		return rm; 
	}
}
