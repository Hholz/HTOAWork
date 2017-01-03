package com.ht.controller.dailyWork;

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
import com.ht.popj.dailyWork.Duty;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Holiday;
import com.ht.popj.dailyWork.Swapduty;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.sysSet.ApprovalTitle;
import com.ht.popj.sysSet.ApproveDot;
import com.ht.popj.sysSet.Flowschedule;
import com.ht.service.dailyWork.DutyService;
import com.ht.service.dailyWork.SwapdutyService;
import com.ht.service.sysSet.ApprovalTitleService;
import com.ht.service.sysSet.ApproveDotService;
import com.ht.service.sysSet.FlowscheduleService;

/*
 * ����������������
 */
@Controller
@RequestMapping("/dailyWork/Approve")
public class ApproveSwapdutyController {
	@Autowired
	private FlowscheduleService fschservice;
	@Autowired
	private ApproveDotService apdotservice;
	@Autowired
	private SwapdutyService swapdutyService;
	@Autowired
	ApprovalTitleService atitleservice;
	@Autowired
	private DutyService dutyService;
	
	List<Swapduty> dutylist;
	List<Swapduty> overlist;
	Emp emp = new Emp();
	String apnum="";
	int flowid = 0;
	@RequestMapping("/swapdutyTask")
	@SystemControllerLog(description = "������������������Ϣҳ��")
	public String swapdutyTask(Model model){
		dutylist = new ArrayList<>();
		overlist = new ArrayList<>();
		Swapduty swapduty = new Swapduty();
		Flowschedule fschedu = new Flowschedule();
		ApproveDot apdot = new ApproveDot();
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		
		fschedu.setFlowtype(12);//��������
		swapduty.setApplystatus(1);//����״̬�����ύ��
		
		if(null != userInfo.getEmp()){
			emp = userInfo.getEmp();
			// ��ѯ���ȱ�������������������
			List<Flowschedule> apmlist = fschservice.selectByName(fschedu);
			// ����������Ϊ��¼��
			apdot.setApproveman(emp.getId());
			// ��ѯ�������˵ĵ����������ڵ�
			List<ApproveDot> apdotlist = apdotservice.selectByName(apdot);
			
			for(int i=0;i<apmlist.size();i++){
				// ������Ʒ�깺�����������Ϊ��¼��
				if(apmlist.get(i).getEmpid().equals(emp.getId())){
					Flowschedule fsch = apmlist.get(i);
					for(int j=0;j<apdotlist.size();j++){
						apdot = apdotlist.get(j);
						// �������ڵ�==���Ƚڵ�
						if(apdot.getFlowid() == fsch.getFlowdot()){
							// �������ڵ�Ϊ ---�쵼����Ա������
							if(apdot.getFlowid() == 28){
								flowid = 28;
								swapduty.setApprovalstatus(1);
								String anum = apmlist.get(i).getApplynum();
								swapduty.setAppnum(anum);
								
//								if(dutylist.size() >0){
//									continue;
//								}
								if(swapdutyService.selectByName(swapduty).size() > 0){
									dutylist.addAll(swapdutyService.selectByName(swapduty));
								}
//								if(overlist.size() >0){
//									continue;
//								}
								
								swapduty.setApprovalstatus(2);
								overlist.addAll(swapdutyService.selectByName(swapduty));
								swapduty.setApprovalstatus(3);
								overlist.addAll(swapdutyService.selectByName(swapduty));
								break;
							}
						}
					}
				}
			}
		}
		model.addAttribute("dutylist", dutylist);
		model.addAttribute("overlist", overlist);
		return "/dailyWork/ApproveSwapduty";
	}
	
	//��������
	@RequestMapping(value = "/over")
	@SystemControllerLog(description = "������������")
	public String approve(Swapduty swapduty,Duty duty){
		Swapduty swap = new Swapduty();
		swap = swapdutyService.selectByPrimaryKey(swapduty.getId());
		apnum =  swap.getAppnum();
		swapduty.setApplystatus(1);
		
		ApprovalTitle title = new ApprovalTitle();
		title.setEmpid(emp.getId());
		
		//�쵼������ͨ��:2
		if (swapduty.getApprovalstatus() == 2) {
			title.setTitlestutas("D");
		}else if (swapduty.getApprovalstatus() == 3) {//�쵼����ͨ��:3
			title.setTitlestutas("C");
			
			//ͬ������󣬵���ֵ����
			List<Duty> list = new ArrayList<>();
			list = dutyService.findDutyList2();
			for(int i=0;i<list.size();i++){
				if((swap.getWeeks1().equals(list.get(i).getWeeks())) && (swap.getDutyid().equals(list.get(i).getDutyid()))){
					duty = dutyService.selectById(list.get(i).getId());
					duty.setDutyid(swap.getEmpid());
					dutyService.updateDuty(duty);
				}else if((swap.getWeeks2().equals(list.get(i).getWeeks())) && (swap.getEmpid().equals(list.get(i).getDutyid()))){
					duty = dutyService.selectById(list.get(i).getId());
					duty.setDutyid(swap.getDutyid());
					dutyService.updateDuty(duty);
				}
			}
		}
		
		
		swapdutyService.updateSwapduty(swapduty);
		
		for (int i = 0; i < dutylist.size(); i++) {
			if (swapduty.getId() == dutylist.get(i).getId()) {
				Swapduty duty1 = dutylist.get(i);
				title.setAppman(duty1.getEmpid());
				title.setCreateman(duty1.getEmpid());
				title.setApprovalnum(duty1.getAppnum());
				title.setFlowid(flowid);
				title.setApprovalman(emp.getId());
				title.setDepid(emp.getDepid());
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
		
		return "redirect:/dailyWork/Approve/swapdutyTask";
	}
}
