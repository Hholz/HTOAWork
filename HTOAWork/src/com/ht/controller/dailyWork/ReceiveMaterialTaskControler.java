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
import com.ht.popj.dailyWork.ReceiveMaterial;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.sysSet.ApprovalTitle;
import com.ht.popj.sysSet.ApproveDot;
import com.ht.popj.sysSet.Flowschedule;
import com.ht.service.dailyWork.ApplyMaterialService;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.MaterialService;
import com.ht.service.dailyWork.ReceiveMaterialService;
import com.ht.service.sysSet.ApprovalTitleService;
import com.ht.service.sysSet.ApproveDotService;
import com.ht.service.sysSet.FlowscheduleService;
/*
 * 操作物品申领审批
 */
@Controller
@RequestMapping("/dailyWork/ReceiveMaterialTask")
public class ReceiveMaterialTaskControler {

	@Autowired
	FlowscheduleService fschservice;
	@Autowired
	ApproveDotService apdotservice;
	@Autowired
	MaterialService materialservice;
	@Autowired
	ReceiveMaterialService receivematerialservice;
	@Autowired
	ApprovalTitleService atitleservice;

	List<ReceiveMaterial> materiallist = new ArrayList<ReceiveMaterial>();
	List<ReceiveMaterial> overtasklist = new ArrayList<ReceiveMaterial>();
	Emp e = new Emp();
	String apnum = "";
	int flowid = 0;
	
	//查询物品申领申请的所有数据,进入物品申领审批页面
	@RequestMapping("/tasklist")
	@SystemControllerLog(description = "查询物品申领申请的所有数据,进入物品申领审批页面")
	public String List(Model model) {
		materiallist = new ArrayList<ReceiveMaterial>();
		overtasklist = new ArrayList<ReceiveMaterial>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		ReceiveMaterial receivematerial = new ReceiveMaterial();
		Flowschedule fschedu = new Flowschedule();
		ApproveDot apdot = new ApproveDot();
		fschedu.setFlowtype(3);
		receivematerial.setUpset(1);

		if (null != userInfo.getEmp()) {
			e = userInfo.getEmp();
			List<Flowschedule> apmlist = fschservice.selectByName(fschedu);

			apdot.setApproveman(e.getId());
			List<ApproveDot> apdotlist = apdotservice.selectByName(apdot);
			for (int j = 0; j < apmlist.size(); j++) {
				if (apmlist.get(j).getEmpid().equals(e.getId())) {
					Flowschedule fsch = apmlist.get(j);
					for (int i = 0; i < apdotlist.size(); i++) {
						apdot = apdotlist.get(i);
						
						if (apdot.getFlowid() == fsch.getFlowdot()) {
							if(apdot.getFlowid() == 13){
								flowid = 13;
								receivematerial.setApprovalstatus(1);
								
								String anum = apmlist.get(j).getApplynum();
								receivematerial.setApplynum(anum);
								if (receivematerialservice.selectByName(receivematerial).size() > 0) {
									materiallist.addAll(receivematerialservice.selectByName(receivematerial));
								}
								
								receivematerial.setApprovalstatus(2);
								overtasklist.addAll(receivematerialservice.selectByName(receivematerial));
								receivematerial.setApprovalstatus(3);
								overtasklist.addAll(receivematerialservice.selectByName(receivematerial));
								break;
							}else if(apdot.getFlowid() == 14){
								flowid = 14;
								receivematerial.setApprovalstatus(3);
								
								String anum = apmlist.get(j).getApplynum();
								receivematerial.setApplynum(anum);
								if (receivematerialservice.selectByName(receivematerial).size() > 0) {
									materiallist.addAll(receivematerialservice.selectByName(receivematerial));
								}
								
								receivematerial.setApprovalstatus(4);
								overtasklist.addAll(receivematerialservice.selectByName(receivematerial));
								break;
							}
						}
					}
				}
			}
		}
		model.addAttribute("materiallist", materiallist);
		model.addAttribute("overtasklist", overtasklist);
		if(e.getDepid() ==12){
			return "/dailyWork/ReMLogisticsList";
		}
		return "/dailyWork/ReMTaskList";
	}
	
	//审批后提交
	@RequestMapping(value = "/over")
	@SystemControllerLog(description = "审批后提交")
	public String updata(Model model, ReceiveMaterial receivematerial) {
		
		ReceiveMaterial rematerial = new ReceiveMaterial();
	
		rematerial =receivematerialservice.selectByPrimaryKey(receivematerial.getId());
		apnum =  rematerial.getApplynum();
		ApprovalTitle title = new ApprovalTitle();
		receivematerial.setUpset(1);
		title.setEmpid(e.getId());
		if (receivematerial.getApprovalstatus() == 2) {
			title.setTitlestutas("D");
		} else if (receivematerial.getApprovalstatus() == 3) {
			title.setTitlestutas("C");
		} else if(receivematerial.getApprovalstatus() == 4){
			ReceiveMaterial apmater = receivematerialservice.selectByPrimaryKey(receivematerial.getId());
			Material material = new Material();
			material.setMaterialname(apmater.getMaterialname());
			
			
			
			List<Material> materialist = materialservice.selectByName(material);
			
			if(!materialist.isEmpty()){
				for(int i=0;i<materialist.size();i++){
					material = materialist.get(i);
					if(material.getId() ==  apmater.getMaterialid()){
						material.setCounts(material.getCounts() - apmater.getCounts());
						materialservice.updateByPrimaryKeySelective(material);
						break;
					}
				}
			}
			title.setTitlestutas("C");
		}
		
		receivematerialservice.updateByPrimaryKeySelective(receivematerial);
		
		for (int i = 0; i < materiallist.size(); i++) {
			if (receivematerial.getId() == materiallist.get(i).getId()) {
				ReceiveMaterial apm = materiallist.get(i);
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
		return "redirect:/dailyWork/ReceiveMaterialTask/tasklist";
	}
}
