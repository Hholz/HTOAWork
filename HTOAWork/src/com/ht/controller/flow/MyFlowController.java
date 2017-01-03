package com.ht.controller.flow;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ht.annotation.SystemControllerLog;
import com.ht.mapper.flow.FlowFeedBackMapper;
import com.ht.mapper.flow.FlowHolidayMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.flow.FlowApplyMaterial;
import com.ht.popj.flow.FlowBaoxiao;
import com.ht.popj.flow.FlowFeedBack;
import com.ht.popj.flow.FlowHoliday;
import com.ht.popj.flow.FlowStudentApply;
import com.ht.popj.flow.FlowReceivematerial;
import com.ht.popj.flow.FlowReturnmaterial;
import com.ht.popj.flow.FlowSupplement;
import com.ht.popj.flow.FlowSwaparrange;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.service.flow.FlowApplyMaterialService;
import com.ht.service.flow.FlowBaoxiaoService;
import com.ht.service.flow.FlowStudentApplyService;
import com.ht.service.flow.FlowReceivematerialService;
import com.ht.service.flow.FlowReturnmaterialService;
import com.ht.service.flow.FlowSupplementService;
import com.ht.service.flow.FlowSwaparrangeService;

@Controller
@RequestMapping("/flow")
public class MyFlowController {

	@Autowired
	FlowHolidayMapper holidayMapper;
	String empid = "";
	Integer studid;
	@Autowired
	FlowApplyMaterialService flowapplymaterialservice;
	@Autowired
	FlowSupplementService flowsupplementservice;
	@Autowired
	FlowFeedBackMapper feedBackMapper;
	@Autowired
	FlowStudentApplyService studentApplyService;
	@Autowired
	StudentClassMapper classMapper;
	@Autowired
	FlowReceivematerialService flowreceivematerialservice;
	@Autowired
	FlowReturnmaterialService flowreturnmaterialservice;
	@Autowired
	FlowBaoxiaoService flowbaoxiaoservice;
	@Autowired
	FlowSwaparrangeService flowswaparrangeservice;

	@RequestMapping("/myFlowList")
	@SystemControllerLog(description = "进入我的申请列表")
	public String myFlowList(Model model) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");

		// 学生请假
		List<FlowHoliday> holiday0 = new ArrayList<>();// 未提交
		List<FlowHoliday> holiday1 = new ArrayList<>();// 已提交
		FlowHoliday flowHoliday = new FlowHoliday();
		
		//学生调班申请
		List<FlowStudentApply> studentApplies0 = new ArrayList<>();// 未提交
		List<FlowStudentApply> studentApplies1 = new ArrayList<>();// 已提交
		FlowStudentApply studentApply = new FlowStudentApply();
		// 学生类申请
		Student student = null;
		if (null != userInfo.getStudent()) {
			student = userInfo.getStudent();
			studid = student.getId();

			flowHoliday.setStudid(studid);
			flowHoliday.setUpset(0);
			holiday0 = holidayMapper.selectMyHoliday(flowHoliday);
			flowHoliday.setUpset(1);
			holiday1 = holidayMapper.selectMyHoliday(flowHoliday);
			
			studentApply.setStudid(studid);
			studentApply.setUpset(0);
			studentApplies0 = studentApplyService.selectStudentApplyList(studentApply);
			studentApply.setUpset(1);
			studentApplies1 = studentApplyService.selectStudentApplyList(studentApply);
			for (int i = 0; i < studentApplies0.size(); i++) {
				StudentClass studentClass = classMapper.selectByPrimaryKey(studentApplies0.get(i).getClassid2());
				studentApplies0.get(i).setStudentClass1(studentClass);
			}
			for (int i = 0; i < studentApplies1.size(); i++) {
				StudentClass studentClass = classMapper.selectByPrimaryKey(studentApplies1.get(i).getClassid2());
				studentApplies1.get(i).setStudentClass1(studentClass);
			}
			
		}
		model.addAttribute("holiday0", holiday0);
		model.addAttribute("holiday1", holiday1);
		model.addAttribute("studentApplies0", studentApplies0);
		model.addAttribute("studentApplies1", studentApplies1);
		// 员工类申请
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			empid = emp.getId();
		}
		List<FlowApplyMaterial> flist1 = new ArrayList<>();// 未提交
		List<FlowApplyMaterial> flist2 = new ArrayList<>();// 已提交
		
		List<FlowHoliday> empholidaylist1 = new ArrayList<>();// 未提交
		List<FlowHoliday> empholidaylist2 = new ArrayList<>();// 已提交
		
		//发起学生退费申请
		List<FlowFeedBack> feedBacks1 = new ArrayList<>();// 已提交
		
		List<FlowSupplement> supplementlist1 = new ArrayList<>();// 未提交
		List<FlowSupplement> supplementlist2 = new ArrayList<>();// 已提交
		
		List<FlowReceivematerial> receivemateriallist1 = new ArrayList<>();// 未提交
		List<FlowReceivematerial> receivemateriallist2 = new ArrayList<>();// 已提交
		
		List<FlowReturnmaterial> returnmateriallist1 = new ArrayList<>();// 未提交
		List<FlowReturnmaterial> returnmateriallist2 = new ArrayList<>();// 已提交
		
		List<FlowBaoxiao> Reimburselist1 = new ArrayList<>();// 未提交
		List<FlowBaoxiao> Reimburselist2 = new ArrayList<>();// 已提交
		
		List<FlowSwaparrange> Swaparrangelist1 = new ArrayList<>();// 未提交
		List<FlowSwaparrange> Swaparrangelist2 = new ArrayList<>();// 已提交
		
		FlowApplyMaterial fam = new FlowApplyMaterial();
		FlowHoliday fh = new FlowHoliday();
		FlowFeedBack feedBack = new FlowFeedBack();
		FlowSupplement flowSupplement = new FlowSupplement();
		FlowReceivematerial frm = new FlowReceivematerial();
		FlowReturnmaterial fReturnmaterial = new FlowReturnmaterial();
		FlowBaoxiao flowBaoxiao = new FlowBaoxiao();
		FlowSwaparrange flowSwaparrange = new FlowSwaparrange();
		// 员工类申请
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			empid = emp.getId();
			
			feedBack.setEmpid(empid);
			feedBack.setUpset(1);
			feedBacks1 = feedBackMapper.selectMyFeedBack(feedBack);
			
			fh.setEmpid(empid);
			fh.setUpset(0);
			empholidaylist1 =holidayMapper.selectMyEmpHoliday(fh);
			fh.setUpset(1);
			empholidaylist2 =holidayMapper.selectMyEmpHoliday(fh);
			
			fam.setEmpid(empid);
			fam.setUpset(0);
			fam.setApprovalstatus(0);
			flist1 = flowapplymaterialservice.selectSelective(fam);
			fam.setUpset(1);
			fam.setApprovalstatus(1);
			flist2 = flowapplymaterialservice.selectSelective(fam);
			
			flowSupplement.setEmpid(empid);
			flowSupplement.setApprovalstatus(0);//未提交
			supplementlist1 = flowsupplementservice.selectMySupplement(flowSupplement);
			flowSupplement.setApprovalstatus(1);//已提交,正在审批
			supplementlist2 = flowsupplementservice.selectMySupplement(flowSupplement);

			frm.setEmpid(empid);
			frm.setUpset(0);
			frm.setApprovestatus(0);
			receivemateriallist1 = flowreceivematerialservice.selectSelective(frm);
			frm.setUpset(1);
			frm.setApprovestatus(1);
			receivemateriallist2 = flowreceivematerialservice.selectSelective(frm);
			
			fReturnmaterial.setEmpid(empid);
			fReturnmaterial.setUpset(0);
			fReturnmaterial.setApprovestatus(0);
			returnmateriallist1 = flowreturnmaterialservice.selectSelective(fReturnmaterial);
			fReturnmaterial.setUpset(1);
			fReturnmaterial.setApprovestatus(1);
			returnmateriallist2 = flowreturnmaterialservice.selectSelective(fReturnmaterial);
		
			flowBaoxiao.setApprovalStatus(0);
			flowBaoxiao.setUpset(0);
			flowBaoxiao.setEmpid(empid);
			Reimburselist1 = flowbaoxiaoservice.selectSelective(flowBaoxiao);
			flowBaoxiao.setUpset(1);
			flowBaoxiao.setApprovalStatus(1);
			Reimburselist2 = flowbaoxiaoservice.selectSelective(flowBaoxiao);
			
			flowSwaparrange.setApprovalstatus(0);
			flowSwaparrange.setUpset(0);
			flowSwaparrange.setChangeOne(empid);
			Swaparrangelist1 = flowswaparrangeservice.selectSelective(flowSwaparrange);
			flowSwaparrange.setApprovalstatus(1);
			flowSwaparrange.setUpset(1);
			Swaparrangelist2 =  flowswaparrangeservice.selectSelective(flowSwaparrange);
			
		}
		model.addAttribute("flist1", flist1);
		model.addAttribute("flist2", flist2);
		
		model.addAttribute("empholidaylist1", empholidaylist1);
		model.addAttribute("empholidaylist2", empholidaylist2);
		
		model.addAttribute("feedBacks1", feedBacks1);
		
		model.addAttribute("supplementlist1", supplementlist1);
		model.addAttribute("supplementlist2", supplementlist2);
		
		model.addAttribute("receivemateriallist1", receivemateriallist1);
		model.addAttribute("receivemateriallist2", receivemateriallist2);
		
		model.addAttribute("returnmateriallist1", returnmateriallist1);
		model.addAttribute("returnmateriallist2", returnmateriallist2);
		
		model.addAttribute("Reimburselist1", Reimburselist1);
		model.addAttribute("Reimburselist2", Reimburselist2);
		
		model.addAttribute("Swaparrangelist1", Swaparrangelist1);
		model.addAttribute("Swaparrangelist2", Swaparrangelist2);
		
		return "/flow/myFlow";
	}

}
