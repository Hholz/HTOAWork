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
 * ������Ʒ�깺����
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
	
	//��ѯ��������Ʒ�깺��Ϣ,������Ʒ�깺����ҳ��
	@RequestMapping("/tasklist")
	@SystemControllerLog(description = "��ѯ��������Ʒ�깺��Ϣ,������Ʒ�깺����ҳ��")
	public String List(Model model) {
		materiallist = new ArrayList<ApplyMaterial>();
		overtasklist = new ArrayList<ApplyMaterial>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		ApplyMaterial applymaterial = new ApplyMaterial();
		Flowschedule fschedu = new Flowschedule();
		ApproveDot apdot = new ApproveDot();
		fschedu.setFlowtype(1);
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
				// ������Ʒ�깺�����������Ϊ��¼��
				if (apmlist.get(j).getEmpid().equals(e.getId())) {
					Flowschedule fsch = apmlist.get(j);
					for (int i = 0; i < apdotlist.size(); i++) {
						apdot = apdotlist.get(i);
						// �������ڵ�==���Ƚڵ�
						if (apdot.getFlowid() == fsch.getFlowdot()) {
							// �������ڵ�Ϊ ---�깺�쵼����
							if(apdot.getFlowid() == 2){
								flowid = 2;
								applymaterial.setApprovalstatus(1);
								
								String anum = apmlist.get(j).getApplynum();
								applymaterial.setApplynum(anum);
								// δ�������
								if (appmaterialservice.selectByName(applymaterial).size() > 0) {
									materiallist.add(appmaterialservice.selectByName(applymaterial).get(0));
								}
								// ���������
								applymaterial.setApprovalstatus(2);
								overtasklist.addAll(appmaterialservice.selectByName(applymaterial));
								applymaterial.setApprovalstatus(3);
								overtasklist.addAll(appmaterialservice.selectByName(applymaterial));
								break;
							}else if(apdot.getFlowid() ==3){// �������ڵ�Ϊ ---�깺��������
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
							}else if(apdot.getFlowid() == 9){// �������ڵ�Ϊ ---�깺���ڲ�ȷ��
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
		//����¼��Ϊ���ڲ���,�򵽺��ڲ�ȷ��ҳ��
		if(e.getDepid() ==12){
			return "/dailyWork/LogisticsList";
		}
		return "/dailyWork/TaskList";
	}
	
	//��������ύ
	@RequestMapping(value = "/over")
	@SystemControllerLog(description = "��������ύ")
	public String updata(Model model, ApplyMaterial applymaterial) {
		
		ApplyMaterial apmaterial = new ApplyMaterial();
	
		apmaterial =appmaterialservice.selectByPrimaryKey(applymaterial.getId());
		apnum =  apmaterial.getApplynum();
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
		} else if(applymaterial.getApprovalstatus() == 6){//���ڲ���ȷ��:6
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
