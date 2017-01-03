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
 * ������Ʒ�黹��������
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
	
	//��ѯ��������Ʒ������������Ϣ,������Ʒ��������������ҳ��
	@RequestMapping("/tasklist")
	@SystemControllerLog(description = "��ѯ��������Ʒ������������Ϣ,������Ʒ��������������ҳ��")
	public String List(Model model) {
		materiallist = new ArrayList<ReturnMaterial>();
		overtasklist = new ArrayList<ReturnMaterial>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		ReturnMaterial returnmaterial = new ReturnMaterial();
		Flowschedule fschedu = new Flowschedule();
		ApproveDot apdot = new ApproveDot();
		// ��Ʒ�黹���������Ϊ6
		fschedu.setFlowtype(6);

		returnmaterial.setUpset(1);

		if (null != userInfo.getEmp()) {
			e = userInfo.getEmp();
			// ��ѯ���ȱ�����Ʒ�黹����������
			List<Flowschedule> apmlist = fschservice.selectByName(fschedu);
			// ����������Ϊ��¼��
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
							if (apdot.getFlowid() == 20) {
								flowid = 20;
								// δ�������
								returnmaterial.setApprovalstatus(1);

								String anum = apmlist.get(j).getApplynum();
								returnmaterial.setAppnum(anum);
								if (returnmaterialservice.selectByName(returnmaterial).size() > 0) {
									materiallist.addAll(returnmaterialservice.selectByName(returnmaterial));
								}
								// ���������
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
		// ���ڲ�ȷ��ҳ��
		return "/dailyWork/ReMLogisticsList";
	}

	// ���ڲ�ȷ��
	@RequestMapping(value = "/over")
	@SystemControllerLog(description = "���ڲ�ȷ��")
	public String updata(Model model, ReturnMaterial returnmaterial) {

		ReturnMaterial rematerial = new ReturnMaterial();
		// ��ѯȷ�ϵĹ黹��
		rematerial = returnmaterialservice.selectByPrimaryKey(returnmaterial.getId());

		apnum = rematerial.getAppnum();
		ApprovalTitle title = new ApprovalTitle();
		returnmaterial.setUpset(1);

		//����������Ϊ��¼��
		title.setEmpid(e.getId());

		// 2:���ڲ���ȷ��
		if (returnmaterial.getApprovalstatus() == 2) {
			Material material = new Material();
			material = materialservice.selectByPrimaryKey(rematerial.getMaterialid());
			// ���黹��ƷidΪ������Ʒid
			// �޸Ŀ��
			material.setCounts(material.getCounts() + rematerial.getCounts());
			materialservice.updateByPrimaryKeySelective(material);
			title.setTitlestutas("C");
		}
		// �޸Ĺ黹��������״̬
		returnmaterialservice.updateByPrimaryKeySelective(returnmaterial);

		for (int i = 0; i < materiallist.size(); i++) {
			// ����������¼
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
		// �޸Ľ���
		fschservice.updateByPrimaryKeySelective(fschedu);
		return "redirect:/dailyWork/ReturnMaterialTask/tasklist";
	}
}
