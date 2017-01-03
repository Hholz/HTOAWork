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

	// ��ѯ��������Ʒ������������Ϣ,������Ʒ��������������ҳ��
	@RequestMapping("/tasklist")
	@SystemControllerLog(description = "��ѯ���õ���������Ϣ,������Ʒ���õ�����������ҳ��")
	public String List(Model model) {
		materiallist = new ArrayList<StudentComputerApply>();
		overtasklist = new ArrayList<StudentComputerApply>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		StudentComputerApply returnmaterial = new StudentComputerApply();
		Flowschedule fschedu = new Flowschedule();
		ApproveDot apdot = new ApproveDot();
		// ����ά�޵��������Ϊ13
		fschedu.setFlowtype(14);

		if (null != userInfo.getEmp()) {
			e = userInfo.getEmp();
			// ��ѯ���ȱ�����Ʒ�黹����������
			List<Flowschedule> apmlist = fschservice.selectByName(fschedu);
			// ����������Ϊ��¼��
			apdot.setFlowid(30);
			apdot.setApproveman(e.getId());
			// ��ѯ�������˵ĵ����������ڵ�
			List<ApproveDot> apdotlist = apdotservice.selectByName(apdot);
			for (int j = 0; j < apmlist.size(); j++) {
				// ������Ʒ�黹�����������Ϊ��¼��
				if (apmlist.get(j).getEmpid().equals(e.getId())) {
					Flowschedule fsch = apmlist.get(j);
					for (int i = 0; i < apdotlist.size(); i++) {
						apdot = apdotlist.get(i);
						// �������ڵ�==���Ƚڵ�
						if (apdot.getFlowid() == fsch.getFlowdot()) {
							// �������ڵ�Ϊ ---���ڲ�ȷ��
							if (apdot.getFlowid() == 30) {
								flowid = 30;
								// δ�������
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

								// ���������
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
		// ���ڲ�ȷ��ҳ��
		return "/student/BackUpComputerTaskList";
	}

	// ���ڲ�ȷ��
	@RequestMapping(value = "/over")
	@SystemControllerLog(description = "����ȷ��")
	public String updata(Model model, StudentComputerApply returnmaterial) {

		StudentComputerApply rematerial = new StudentComputerApply();
		// ��ѯȷ�ϵĹ黹��
		rematerial = studentComputerService.selectById(returnmaterial.getId());

		apnum = rematerial.getApplyno();
		ApprovalTitle title = new ApprovalTitle();
		// ����������Ϊ��¼��
		title.setEmpid(e.getId());

		// 2:�ϳ���ȷ��,ѧ������ȥ�챸�õ���
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
			// ����������¼
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
		// 1:�˽ڵ������
		fschedu.setFlowstatus(1);
		// ��ѯ���ȱ��е���Ϊ�������ŵ�����
		List<Flowschedule> fschlist = fschservice.selectByName(fschedu);
		for (int i = 0; i < fschlist.size(); i++) {
			// �����Ƚڵ������id == �����ڵ�
			if (fschlist.get(i).getFlowdot() == flowid) {
				fschedu.setId(fschlist.get(i).getId());
				break;
			}
		}
		fschservice.updateByPrimaryKeySelective(fschedu);
		// �޸Ľ���
		fschedu.setId(null);
		fschedu.setFlowdot(32);
		fschedu.setFlowtype(15);
		fschedu.setFlowstatus(0);
		//�������õ��Թ黹����Ľ���
		fschservice.insertSelective(fschedu);
		
		return "redirect:/student/BackUpComputer/tasklist";
	}

}
