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
 * �����Ӱ�����
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
	
	
	//����Ӱ�����ҳ��
	@RequestMapping("/tasklist")
	@SystemControllerLog(description = "����Ӱ�����ҳ��")
	public String List(Model model) {
		materiallist = new ArrayList<Holiday>();
		overtasklist = new ArrayList<Holiday>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Holiday applymaterial = new Holiday();
		Flowschedule fschedu = new Flowschedule();
		ApproveDot apdot = new ApproveDot();
		fschedu.setFlowtype(10);
		applymaterial.setUpset(1);
		// 3.��userInfo��ȡ��ѧ����Ա����Ϣ//������һΪ�գ�ע���п�
		if (null != userInfo.getEmp()) {
			e = userInfo.getEmp();
			// ��ѯ���ȱ�����Ʒ�깺����������
			List<Flowschedule> apmlist = fschservice.selectByName(fschedu);

			// ����������Ϊ��¼��
			apdot.setApproveman(e.getId());
			// ��ѯ�������˵ĵ����������ڵ�
			List<ApproveDot> apdotlist = apdotservice.selectByName(apdot);
			
			for (int j = 0; j < apmlist.size(); j++) {
				String anum = "";
				// ������Ʒ�깺�����������Ϊ��¼��
				if (apmlist.get(j).getEmpid().equals(e.getId())) {
					Flowschedule fsch = apmlist.get(j);
					for (int i = 0; i < apdotlist.size(); i++) {
						apdot = apdotlist.get(i);
						// �������ڵ�==���Ƚڵ�
						if (apdot.getFlowid() == fsch.getFlowdot()) {
							// �������ڵ�Ϊ ---�쵼����Ա���Ӱ�
							if(apdot.getFlowid() ==24){// �������ڵ�Ϊ ---��������Ա�����
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

	//�Ӱ��������ύ
	@RequestMapping(value = "/over")
	@SystemControllerLog(description = "�Ӱ��������ύ")
	public String updata(Model model, Holiday applymaterial) {
		
		Holiday apmaterial = new Holiday();
	
		apmaterial =holidayservice.selectByPrimaryKey(applymaterial.getId());
		apnum =  apmaterial.getAppnum();
		ApprovalTitle title = new ApprovalTitle();
		applymaterial.setUpset(1);
		title.setEmpid(e.getId());
		//�쵼������ͨ��:2
		if (applymaterial.getApprovalstatus() == 2) {
			//����������ͨ��:4
			if(e.getDepid() == 9){
				applymaterial.setApprovalstatus(4);
			}
			title.setTitlestutas("D");
		} else if (applymaterial.getApprovalstatus() == 3) {//�쵼����ͨ��:3
			//��������ͨ��:5
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
