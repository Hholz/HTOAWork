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
import org.springframework.web.bind.annotation.RequestMapping;

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.ApplyMaterial;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Material;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.sysSet.ApprovalTitle;
import com.ht.popj.sysSet.ApproveDot;
import com.ht.popj.sysSet.Flowschedule;
import com.ht.service.dailyWork.ApplyMaterialService;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.MaterialService;
import com.ht.service.sysSet.ApprovalTitleService;
import com.ht.service.sysSet.ApproveDotService;
import com.ht.service.sysSet.FlowscheduleService;
/*
 * 操作物品申购审批
 */
@Controller
@RequestMapping("/dailyWork/task")
public class DailyTaskControler {

	@Autowired
	ApplyMaterialService appmaterialservice;
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
	List<ApplyMaterial> materiallist = new ArrayList<ApplyMaterial>();
	List<ApplyMaterial> overtasklist = new ArrayList<ApplyMaterial>();
	Emp e = new Emp();
	String apnum="";
	int flowid = 0;
	
	//查询出所有物品申购信息,进入物品申购审批页面
	@RequestMapping("/tasklist")
	@SystemControllerLog(description = "查询出所有物品申购信息,进入物品申购审批页面")
	public String List(Model model) {
		materiallist = new ArrayList<ApplyMaterial>();
		overtasklist = new ArrayList<ApplyMaterial>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		ApplyMaterial applymaterial = new ApplyMaterial();
		Flowschedule fschedu = new Flowschedule();
		ApproveDot apdot = new ApproveDot();
		fschedu.setFlowtype(1);
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
				// 若有用品申购任务的审批人为登录人
				if (apmlist.get(j).getEmpid().equals(e.getId())) {
					Flowschedule fsch = apmlist.get(j);
					for (int i = 0; i < apdotlist.size(); i++) {
						apdot = apdotlist.get(i);
						// 若审批节点==进度节点
						if (apdot.getFlowid() == fsch.getFlowdot()) {
							// 若审批节点为 ---申购领导审批
							if(apdot.getFlowid() == 2){
								flowid = 2;
								applymaterial.setApprovalstatus(1);
								
								String anum = apmlist.get(j).getApplynum();
								applymaterial.setApplynum(anum);
								// 未完成任务
								if (appmaterialservice.selectByName(applymaterial).size() > 0) {
									materiallist.add(appmaterialservice.selectByName(applymaterial).get(0));
								}
								// 已完成任务
								applymaterial.setApprovalstatus(2);
								overtasklist.addAll(appmaterialservice.selectByName(applymaterial));
								applymaterial.setApprovalstatus(3);
								overtasklist.addAll(appmaterialservice.selectByName(applymaterial));
								break;
							}else if(apdot.getFlowid() ==3){// 若审批节点为 ---申购财务部审批
								flowid = 3;
								applymaterial.setApprovalstatus(3);
								String anum = apmlist.get(j).getApplynum();
								applymaterial.setApplynum(anum);
								if (appmaterialservice.selectByName(applymaterial).size() > 0) {
									materiallist.add(appmaterialservice.selectByName(applymaterial).get(0));
								}
								
								applymaterial.setApprovalstatus(4);
								overtasklist.addAll(appmaterialservice.selectByName(applymaterial));
								applymaterial.setApprovalstatus(5);
								overtasklist.addAll(appmaterialservice.selectByName(applymaterial));
								break;
							}else if(apdot.getFlowid() == 9){// 若审批节点为 ---申购后勤部确认
								flowid = 9;
								applymaterial.setApprovalstatus(5);
								String anum = apmlist.get(j).getApplynum();
								applymaterial.setApplynum(anum);
								if (appmaterialservice.selectByName(applymaterial).size() > 0) {
									materiallist.add(appmaterialservice.selectByName(applymaterial).get(0));
								}
								applymaterial.setApprovalstatus(6);
								overtasklist.addAll(appmaterialservice.selectByName(applymaterial));
								break;
							}
							
						}
					}
					
				}
				
			}
		}

		model.addAttribute("materiallist", materiallist);
		model.addAttribute("overtasklist", overtasklist);
		//若登录人为后勤部的,则到后勤部确认页面
		if(e.getDepid() ==12){
			return "/dailyWork/LogisticsList";
		}
		return "/dailyWork/TaskList";
	}
	
	//审批后的提交
	@RequestMapping(value = "/over")
	@SystemControllerLog(description = "审批后的提交")
	public String updata(Model model, ApplyMaterial applymaterial) {
		
		ApplyMaterial apmaterial = new ApplyMaterial();
	
		apmaterial =appmaterialservice.selectByPrimaryKey(applymaterial.getId());
		apnum =  apmaterial.getApplynum();
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
		} else if(applymaterial.getApprovalstatus() == 6){//后勤部已确认:6
			ApplyMaterial apmater = appmaterialservice.selectByPrimaryKey(applymaterial.getId());
			Material material = new Material();
			material.setMaterialname(apmater.getMaterialname());
			
			
			
			
			List<Material> materialist = materialservice.selectByName(material);
			
			if(materialist.isEmpty()){
				material.setCounts(apmater.getCounts());
				material.setUnit(apmater.getUnit());
				material.setPrice(apmater.getPrice());
				material.setStyle(apmater.getStyle());
				material.setMeterialRemark(apmater.getApplymaterialRemark());
				material.setMaterialtypeid(apmater.getMaterialtypeid());
				materialservice.insertSelective(material);
			}else{
				for(int i=0;i<materialist.size();i++){
					material = materialist.get(i);
					if(material.getMaterialname().equals(apmater.getMaterialname())){
						material.setCounts(material.getCounts() + apmater.getCounts());
						materialservice.updateByPrimaryKeySelective(material);
						break;
					}else{
						material.setId(null);
						material.setMaterialname(apmater.getMaterialname());
						material.setCounts(apmater.getCounts());
						material.setUnit(apmater.getUnit());
						material.setPrice(apmater.getPrice());
						material.setStyle(apmater.getStyle());
						material.setMeterialRemark(apmater.getApplymaterialRemark());
						material.setMaterialtypeid(apmater.getMaterialtypeid());
						materialservice.insertSelective(material);
						break;
					}
				}
			}
			
			title.setTitlestutas("C");
		}
		
		appmaterialservice.updateByPrimaryKeySelective(applymaterial);
		
		for (int i = 0; i < materiallist.size(); i++) {
			if (applymaterial.getId() == materiallist.get(i).getId()) {
				ApplyMaterial apm = materiallist.get(i);
				title.setAppman(apm.getEmpid());
				title.setCreateman(apm.getEmpid());
				title.setApprovalnum(apm.getApplynum());
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
		return "redirect:/dailyWork/task/tasklist";
	}

}
