package com.ht.controller.student;

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
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Material;
import com.ht.popj.dailyWork.ReturnMaterial;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.StudentComputerApply;
import com.ht.popj.sysSet.ApprovalTitle;
import com.ht.popj.sysSet.ApproveDot;
import com.ht.popj.sysSet.Flowschedule;
import com.ht.service.dailyWork.MaterialService;
import com.ht.service.student.StudentComputerService;
import com.ht.service.sysSet.ApprovalTitleService;
import com.ht.service.sysSet.ApproveDotService;
import com.ht.service.sysSet.FlowscheduleService;

@Controller
@RequestMapping("/student/BackUpComputer")
public class BackUpComputerController {

	@Autowired
	FlowscheduleService fschservice;
	@Autowired
	ApproveDotService apdotservice;
	@Autowired
	StudentComputerService studentComputerService;
	@Autowired
	ApprovalTitleService atitleservice;
	@Autowired
	MaterialService MService;

	List<StudentComputerApply> materiallist = new ArrayList<StudentComputerApply>();
	List<StudentComputerApply> overtasklist = new ArrayList<StudentComputerApply>();

	Emp e = new Emp();
	String apnum = "";
	int flowid = 0;

	// 查询出所有物品申申领申请信息,进入物品申申领申请审批页面
	@RequestMapping("/tasklist")
	@SystemControllerLog(description = "查询备用电脑申请信息,进入物品备用电脑申请审批页面")
	public String List(Model model) {
		materiallist = new ArrayList<StudentComputerApply>();
		overtasklist = new ArrayList<StudentComputerApply>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		StudentComputerApply returnmaterial = new StudentComputerApply();
		Flowschedule fschedu = new Flowschedule();
		ApproveDot apdot = new ApproveDot();
		// 电脑维修的流程类别为13
		fschedu.setFlowtype(14);

		if (null != userInfo.getEmp()) {
			e = userInfo.getEmp();
			// 查询进度表里用品归还的所有任务
			List<Flowschedule> apmlist = fschservice.selectByName(fschedu);
			// 设置审批人为登录人
			apdot.setFlowid(30);
			apdot.setApproveman(e.getId());
			// 查询此审批人的的所有审批节点
			List<ApproveDot> apdotlist = apdotservice.selectByName(apdot);
			for (int j = 0; j < apmlist.size(); j++) {
				// 若有用品归还任务的审批人为登录人
				if (apmlist.get(j).getEmpid().equals(e.getId())) {
					Flowschedule fsch = apmlist.get(j);
					for (int i = 0; i < apdotlist.size(); i++) {
						apdot = apdotlist.get(i);
						// 若审批节点==进度节点
						if (apdot.getFlowid() == fsch.getFlowdot()) {
							// 若审批节点为 ---后勤部确认
							if (apdot.getFlowid() == 30) {
								flowid = 30;
								// 未完成任务
								returnmaterial.setStat(2);

								String anum = apmlist.get(j).getApplynum();
								returnmaterial.setApplyno(anum);
								if (materiallist.size() > 0) {
									continue;
								}
								if (studentComputerService.studentcomputermanage(returnmaterial).size() > 0) {
									materiallist.addAll(studentComputerService.studentcomputermanage(returnmaterial));
								}
								if (overtasklist.size() > 0) {
									continue;
								}

								// 已完成任务
								returnmaterial.setStat(3);
								overtasklist.addAll(studentComputerService.studentcomputermanage(returnmaterial));
								break;
							}
						}
					}
				}
			}
		}
		model.addAttribute("materiallist", materiallist);
		model.addAttribute("overtasklist", overtasklist);
		// 后勤部确认页面
		return "/student/BackUpComputerTaskList";
	}

	// 后勤部确认
	@RequestMapping(value = "/over")
	@SystemControllerLog(description = "审批确认")
	public String updata(Model model, StudentComputerApply returnmaterial) {

		StudentComputerApply rematerial = new StudentComputerApply();
		// 查询确认的归还单
		rematerial = studentComputerService.selectById(returnmaterial.getId());

		apnum = rematerial.getApplyno();
		ApprovalTitle title = new ApprovalTitle();
		// 设置审批人为登录人
		title.setEmpid(e.getId());

		// 2:老陈已确认,学生可以去领备用电脑
		if (returnmaterial.getStat() == 3) {
			
			title.setTitlestutas("C");
			Material m = new Material();
			m = MService.selectByPrimaryKey(14);
			if(m.getCounts()==0){
				return "0";
			}
			m.setCounts(m.getCounts()-1);
			MService.updateByPrimaryKeySelective(m);
			returnmaterial.setManagetype(3);
			studentComputerService.updateByPrimaryKeySelective(returnmaterial);
			
			
		}

		for (int i = 0; i < materiallist.size(); i++) {
			// 生成审批记录
			if (returnmaterial.getId() == materiallist.get(i).getId()) {
				StudentComputerApply apm = materiallist.get(i);
				title.setAppman(apm.getStudentid() + "");
				title.setCreateman(apm.getCreateman());
				title.setApprovalnum(apm.getApplyno());
				title.setFlowid(flowid);
				title.setApprovalman(e.getId());
				title.setDepid(e.getDepid());
				atitleservice.insertSelective(title);
				break;
			}
		}
		Flowschedule fschedu = new Flowschedule();
		fschedu.setApplynum(apnum);
		fschedu.setFlowdot(flowid);
		// 1:此节点已完成
		fschedu.setFlowstatus(1);
		// 查询进度表中单号为审批单号的数据
		List<Flowschedule> fschlist = fschservice.selectByName(fschedu);
		for (int i = 0; i < fschlist.size(); i++) {
			// 若进度节点的流程id == 审批节点
			if (fschlist.get(i).getFlowdot() == flowid) {
				fschedu.setId(fschlist.get(i).getId());
				break;
			}
		}
		fschservice.updateByPrimaryKeySelective(fschedu);
		// 修改进度
		fschedu.setId(null);
		fschedu.setFlowdot(32);
		fschedu.setFlowtype(15);
		fschedu.setFlowstatus(0);
		//新增备用电脑归还申请的进度
		fschservice.insertSelective(fschedu);
		
		return "redirect:/student/BackUpComputer/tasklist";
	}

}
