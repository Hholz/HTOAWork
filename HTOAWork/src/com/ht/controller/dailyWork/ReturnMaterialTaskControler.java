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
import com.ht.popj.dailyWork.ReturnMaterial;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.sysSet.ApprovalTitle;
import com.ht.popj.sysSet.ApproveDot;
import com.ht.popj.sysSet.Flowschedule;
import com.ht.service.dailyWork.ApplyMaterialService;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.MaterialService;
import com.ht.service.dailyWork.ReceiveMaterialService;
import com.ht.service.dailyWork.ReturnMaterialService;
import com.ht.service.sysSet.ApprovalTitleService;
import com.ht.service.sysSet.ApproveDotService;
import com.ht.service.sysSet.FlowscheduleService;
/*
 * 操作物品归还申请审批
 */
@Controller
@RequestMapping("/dailyWork/ReturnMaterialTask")
public class ReturnMaterialTaskControler {

	@Autowired
	FlowscheduleService fschservice;
	@Autowired
	ApproveDotService apdotservice;
	@Autowired
	MaterialService materialservice;
	@Autowired
	ReturnMaterialService returnmaterialservice;
	@Autowired
	ApprovalTitleService atitleservice;

	List<ReturnMaterial> materiallist = new ArrayList<ReturnMaterial>();
	List<ReturnMaterial> overtasklist = new ArrayList<ReturnMaterial>();
	Emp e = new Emp();
	String apnum = "";
	int flowid = 0;
	
	//查询出所有物品申申领申请信息,进入物品申申领申请审批页面
	@RequestMapping("/tasklist")
	@SystemControllerLog(description = "查询出所有物品申申领申请信息,进入物品申申领申请审批页面")
	public String List(Model model) {
		materiallist = new ArrayList<ReturnMaterial>();
		overtasklist = new ArrayList<ReturnMaterial>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		ReturnMaterial returnmaterial = new ReturnMaterial();
		Flowschedule fschedu = new Flowschedule();
		ApproveDot apdot = new ApproveDot();
		// 用品归还的流程类别为6
		fschedu.setFlowtype(6);

		returnmaterial.setUpset(1);

		if (null != userInfo.getEmp()) {
			e = userInfo.getEmp();
			// 查询进度表里用品归还的所有任务
			List<Flowschedule> apmlist = fschservice.selectByName(fschedu);
			// 设置审批人为登录人
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
							if (apdot.getFlowid() == 20) {
								flowid = 20;
								// 未完成任务
								returnmaterial.setApprovalstatus(1);

								String anum = apmlist.get(j).getApplynum();
								returnmaterial.setAppnum(anum);
								if (returnmaterialservice.selectByName(returnmaterial).size() > 0) {
									materiallist.addAll(returnmaterialservice.selectByName(returnmaterial));
								}
								// 已完成任务
								returnmaterial.setApprovalstatus(2);
								overtasklist.addAll(returnmaterialservice.selectByName(returnmaterial));
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
		return "/dailyWork/ReMLogisticsList";
	}

	// 后勤部确认
	@RequestMapping(value = "/over")
	@SystemControllerLog(description = "后勤部确认")
	public String updata(Model model, ReturnMaterial returnmaterial) {

		ReturnMaterial rematerial = new ReturnMaterial();
		// 查询确认的归还单
		rematerial = returnmaterialservice.selectByPrimaryKey(returnmaterial.getId());

		apnum = rematerial.getAppnum();
		ApprovalTitle title = new ApprovalTitle();
		returnmaterial.setUpset(1);

		//设置审批人为登录人
		title.setEmpid(e.getId());

		// 2:后勤部已确认
		if (returnmaterial.getApprovalstatus() == 2) {
			Material material = new Material();
			material = materialservice.selectByPrimaryKey(rematerial.getMaterialid());
			// 若归还物品id为所查物品id
			// 修改库存
			material.setCounts(material.getCounts() + rematerial.getCounts());
			materialservice.updateByPrimaryKeySelective(material);
			title.setTitlestutas("C");
		}
		// 修改归还单的审批状态
		returnmaterialservice.updateByPrimaryKeySelective(returnmaterial);

		for (int i = 0; i < materiallist.size(); i++) {
			// 生成审批记录
			if (returnmaterial.getId() == materiallist.get(i).getId()) {
				ReturnMaterial apm = materiallist.get(i);
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
		// 修改进度
		fschservice.updateByPrimaryKeySelective(fschedu);
		return "redirect:/dailyWork/ReturnMaterialTask/tasklist";
	}
}
