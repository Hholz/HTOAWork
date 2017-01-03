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
import com.ht.mapper.dailyWork.EmpMapper;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.education.EduCourseTeacher;
import com.ht.popj.education.EduFeedbackStart;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.education.FeedBackStartService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/feedbackstart")
public class FeedBackStartController {

	@Autowired
	FeedBackStartService startService;
	@Autowired
	EmpMapper empmapper;
	@Autowired
	EmpService empservice;
	
	@Autowired
	DepService depservice;
	
	@Autowired
	StudentInfoService studentInfoService;
	private String deptree;
	private String falltree;
	// 访问列表页面
	/*@RequestMapping("/list")
	public String feedBackStartList(Model model) throws Exception {
		String deptree = findemp(0);
		String falltree = findstudentfall(0);
		model.addAttribute("deptree",deptree);
		model.addAttribute("falltree",falltree);
		return "/education/feedback_start";
	}*/
	@RequestMapping("/list")
	@SystemControllerLog(description = "进入发起反馈通知页面")
	public String feedBackStartList(Model model) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = new Emp();
		if(null!=userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		model.addAttribute("empId", emp.getId());
		List<Emp> allEmp = empservice.selectEmp(new Emp());
		List<StudentClass> allClass = studentInfoService.selectStudentclass(new StudentClass());
		model.addAttribute("allEmp",allEmp);
		model.addAttribute("allClass",allClass);
		return "/education/feedback_start";
	}

	// bootstrop table 里的url用来获取Json数据
	@RequestMapping("/startlist")
	@SystemControllerLog(description = "返回发起反馈通知列表json数据")
	public @ResponseBody ResultMessage list(int limit, int offset, Model model, EduFeedbackStart fdstart) {
		ResultMessage rm = new ResultMessage();
		List<EduFeedbackStart> sList = new ArrayList<>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		if (fdstart != null) {
			sList = startService.getSomeFeedBackStart(fdstart);
			 //System.out.println("此时对象不为空" + fdstart);
		} else {
			sList = startService.getAllFeedBackStart();
		}
		// 取分页信息
		PageInfo<EduFeedbackStart> pageInfo = new PageInfo<EduFeedbackStart>(sList);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}

	@RequestMapping("/add")
	@SystemControllerLog(description = "新增反馈通知")
	public @ResponseBody int add(Model model, EduFeedbackStart fdstart) {
		if (fdstart != null) {
			fdstart.setCreateTime(new Date().toLocaleString());
			System.out.println(fdstart);
			int count = startService.addFeedBackStart(fdstart);
			return count;
		}
		return 0;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改反馈通知")
	public @ResponseBody int update(Model model, EduFeedbackStart fdstart) {
		if (null != fdstart) {
			fdstart.setUpdateTime(new Date().toLocaleString());
			int count = startService.updateFeedBackStart(fdstart);
			return count;
		}
		return 0;
	}

	// 删除学生
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除反馈通知")
	public @ResponseBody int delete(Model model, @PathVariable("id") Integer id) {
		int count = startService.deleteFeedBackStart(id);
		return count;
	}
	
	
	
	@RequestMapping(value = "/findemp/{depid}", method = { RequestMethod.GET }) 
	@SystemControllerLog(description = "返回部门树json串")
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

	//返回所选部门的所有员工的Json数据
	@RequestMapping(value = "/findemp/{depid}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "根据部门ID返回员工信息")
	public @ResponseBody ResultMessage findemp(@PathVariable("depid") Integer depid) {
		Emp e = new Emp();
		ResultMessage rm = new ResultMessage();
		e.setDepid(depid);
		List<Emp> emplist =  empservice.selectEmp(e);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	
	@RequestMapping(value = "/findstudentfall/{depid}", method = { RequestMethod.GET }) 
	@SystemControllerLog(description = "返回届别树json串")
	public String findstudentfall(@PathVariable("depid") int depid) throws Exception { 
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
		//System.out.println(falltree);
		//model.addAttribute("falltree", falltree);
		return falltree; 
	}
	@RequestMapping(value = "/findstudentfall/{depid}", method = { RequestMethod.PUT }) 
	@SystemControllerLog(description = "根据届别ID返回班级")
	public @ResponseBody ResultMessage findclass(@PathVariable("depid") int fallid){ 
		ResultMessage rm = new ResultMessage();
		List<StudentClass> classlist=new ArrayList<>();
		classlist=studentInfoService.selectStudentclass2(fallid);
		rm.setTotal(classlist.size());
		rm.setRows(classlist);
		return rm; 
	}
	
	@RequestMapping(value = "/getTeacherByClassId", method={RequestMethod.PUT})
	@SystemControllerLog(description = "根据班级ID返回任课教师")
	public @ResponseBody ResultMessage getTeacherByClassId(StudentClass stuClass){
		ResultMessage rm = new ResultMessage();
		List<StudentClass> someEmp = new ArrayList<StudentClass>();
		someEmp = studentInfoService.selectStudentclass(stuClass);
		rm.setTotal(someEmp.size());
		rm.setRows(someEmp);
		return rm;
	}
}
