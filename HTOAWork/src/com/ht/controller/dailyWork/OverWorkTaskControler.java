package com.ht.controller.dailyWork;

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

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.ApplyMaterial;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Holiday;
import com.ht.popj.dailyWork.Material;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.sysSet.ApprovalTitle;
import com.ht.popj.sysSet.ApproveDot;
import com.ht.popj.sysSet.Flowschedule;
import com.ht.service.dailyWork.ApplyMaterialService;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.HolidayService;
import com.ht.service.dailyWork.MaterialService;
import com.ht.service.sysSet.ApprovalTitleService;
import com.ht.service.sysSet.ApproveDotService;
import com.ht.service.sysSet.FlowscheduleService;
/*
 * 操作加班申请
 */
@Controller
@RequestMapping("/dailyWork/OverWorkTask")
public class OverWorkTaskControler {

	@Autowired
	HolidayService holidayservice;
	@Autowired
	ApprovalTitleService atitleservice;
	@Autowired
	MaterialService materialservice;
	@Autowired
	FlowscheduleService fschservice;
	@Autowired
	ApproveDotService apdotservice;
	@Autowired
	DepService depservice;
	List<Holiday> materiallist = new ArrayList<Holiday>();
	List<Holiday> overtasklist = new ArrayList<Holiday>();
	Emp e = new Emp();
	String apnum="";
	int flowid = 0;
	
	
	//进入加班审批页面
	@RequestMapping("/tasklist")
	@SystemControllerLog(description = "进入加班审批页面")
	public String List(Model model) {
		materiallist = new ArrayList<Holiday>();
		overtasklist = new ArrayList<Holiday>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Holiday applymaterial = new Holiday();
		Flowschedule fschedu = new Flowschedule();
		ApproveDot apdot = new ApproveDot();
		fschedu.setFlowtype(10);
		applymaterial.setUpset(1);
		// 3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
		if (null != userInfo.getEmp()) {
			e = userInfo.getEmp();
			// 查询进度表里用品申购的所有任务
			List<Flowschedule> apmlist = fschservice.selectByName(fschedu);

			// 设置审批人为登录人
			apdot.setApproveman(e.getId());
			// 查询此审批人的的所有审批节点
			List<ApproveDot> apdotlist = apdotservice.selectByName(apdot);
			
			for (int j = 0; j < apmlist.size(); j++) {
				String anum = "";
				// 若有用品申购任务的审批人为登录人
				if (apmlist.get(j).getEmpid().equals(e.getId())) {
					Flowschedule fsch = apmlist.get(j);
					for (int i = 0; i < apdotlist.size(); i++) {
						apdot = apdotlist.get(i);
						// 若审批节点==进度节点
						if (apdot.getFlowid() == fsch.getFlowdot()) {
							// 若审批节点为 ---领导审批员工加班
							if(apdot.getFlowid() ==24){// 若审批节点为 ---财务部审批员工请假
								flowid = 24;
								applymaterial.setHolidaytypeid(3);
								applymaterial.setApprovalstatus(1);
								
								anum = apmlist.get(j).getApplynum();
								applymaterial.setAppnum(anum);
								if(materiallist.size() >0){
									continue;
								}
								if (holidayservice.selectByName(applymaterial).size() > 0) {
									materiallist.addAll(holidayservice.selectByName(applymaterial));
								}
								if(overtasklist.size() >0){
									continue;
								}
								applymaterial.setApprovalstatus(2);
								overtasklist.addAll(holidayservice.selectByName(applymaterial));
								applymaterial.setApprovalstatus(3);
								overtasklist.addAll(holidayservice.selectByName(applymaterial));
								break;
							}
							
						}
					}
					
				}
			}
		}

		model.addAttribute("materiallist", materiallist);
		model.addAttribute("overtasklist", overtasklist);
		return "/dailyWork/OverWorkTaskList";
	}

	//加班审批后提交
	@RequestMapping(value = "/over")
	@SystemControllerLog(description = "加班审批后提交")
	public String updata(Model model, Holiday applymaterial) {
		
		Holiday apmaterial = new Holiday();
	
		apmaterial =holidayservice.selectByPrimaryKey(applymaterial.getId());
		apnum =  apmaterial.getAppnum();
		ApprovalTitle title = new ApprovalTitle();
		applymaterial.setUpset(1);
		title.setEmpid(e.getId());
		//领导审批不通过:2
		if (applymaterial.getApprovalstatus() == 2) {
			//财务部审批不通过:4
			if(e.getDepid() == 9){
				applymaterial.setApprovalstatus(4);
			}
			title.setTitlestutas("D");
		} else if (applymaterial.getApprovalstatus() == 3) {//领导审批通过:3
			//财务部审批通过:5
			if(e.getDepid() == 9){
				applymaterial.setApprovalstatus(5);
			}
			title.setTitlestutas("C");
		}
		
		holidayservice.updateByPrimaryKeySelective(applymaterial);
		
		for (int i = 0; i < materiallist.size(); i++) {
			if (applymaterial.getId() == materiallist.get(i).getId()) {
				Holiday apm = materiallist.get(i);
				title.setAppman(apm.getEmpid());
				title.setCreateman(apm.getEmpid());
				title.setApprovalnum(apm.getAppnum());
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
		fschedu.setFlowstatus(1);
		List<Flowschedule> fschlist = fschservice.selectByName(fschedu);
		for(int i=0;i<fschlist.size();i++){
			if(fschlist.get(i).getFlowdot() == flowid){
				fschedu.setId(fschlist.get(i).getId());
				break;
			}
		}
		
		fschservice.updateByPrimaryKeySelective(fschedu);
		return "redirect:/dailyWork/OverWorkTask/tasklist";
	}

}
