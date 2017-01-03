package com.ht.controller.flow;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.annotation.SystemControllerLog;
import com.ht.mapper.flow.FlowAllMapper;
import com.ht.mapper.flow.FlowModeltypeMapper;
import com.ht.mapper.login.ShiroUserMapper;
import com.ht.popj.dailyWork.ApplyMaterial;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Material;
import com.ht.popj.dailyWork.MaterialType;
import com.ht.popj.flow.FlowAll;
import com.ht.popj.flow.FlowApplyMaterial;
import com.ht.popj.flow.FlowApplyMaterialdetail;
import com.ht.popj.flow.FlowReceivematerial;
import com.ht.popj.flow.FlowReceivematerialdetail;
import com.ht.popj.flow.FlowReturnmaterial;
import com.ht.popj.flow.FlowReturnmaterialdetail;
import com.ht.popj.flow.FlowSupplement;
import com.ht.popj.flow.FlowSupplementDetail;
import com.ht.popj.flow.FlowsModel;
import com.ht.popj.flow.FlowsModeltype;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.sysSet.FinancePolicquantityset;
import com.ht.service.dailyWork.ApplyMaterialService;
import com.ht.service.dailyWork.MaterialService;
import com.ht.service.dailyWork.MaterialTypeService;
import com.ht.service.flow.FlowApplyMaterialService;
import com.ht.service.flow.FlowApplyMaterialdetailService;
import com.ht.service.flow.FlowModelselService;
import com.ht.service.flow.FlowReceivematerialService;
import com.ht.service.flow.FlowReceivematerialdetailService;
import com.ht.service.flow.FlowReturnmaterialService;
import com.ht.service.flow.FlowReturnmaterialdetailService;
import com.ht.service.flow.FlowsModelService;
import com.ht.service.sysSet.FinancePolicquantitysetService;
import com.ht.util.RandomGet;
import com.ht.util.ResultMessage;

/*
 * ������Ʒ�黹
 */
@Controller
@RequestMapping("/flow/ReturnMaterial")
public class ReturnMaterialControler {
	@Autowired
	MaterialTypeService matypeservice;
	@Autowired
	FlowsModelService modelService;
	@Autowired
	MaterialService materialService;
	@Autowired
	FlowModelselService modelselService;
	@Autowired
	FlowModeltypeMapper fmm;
	@Autowired
	ShiroUserMapper usermapper;
	@Autowired
	FlowAllMapper allMapper;

	@Autowired
	FinancePolicquantitysetService finaPolicQuantitySetService;

	@Autowired
	FlowReturnmaterialService flowreturnmaterialservice;
	@Autowired
	FlowReturnmaterialdetailService flowreturnmaterialdeteailservice;

	// ����ҳ��ǰ��ѯ��Ʒ���
	@RequestMapping("/ReturnMaterialList")
	@SystemControllerLog(description = "������Ʒ����ҳ��")
	public String List(Model model) {
		List<MaterialType> materialtypelist = matypeservice.selectList();
		List<FlowsModeltype> flowmodeltypelist = fmm.selectModelTypeByModelSelId("return");
		model.addAttribute("mlist", materialtypelist);
		model.addAttribute("flowmodeltypelist", flowmodeltypelist);
		return "/flow/ReturnMaterialList";
	}

	// ������ѡ����������Ʒ��Json����
	@RequestMapping("/findmaterial")
	@SystemControllerLog(description = "������ѡ����������Ʒ��Json����")
	public @ResponseBody ResultMessage findmaterial(Model model, Material materialtypeid) {
		ResultMessage rm = new ResultMessage();
		List<Material> sList = new ArrayList<Material>();
		sList = materialService.selectByName(materialtypeid);

		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}


	// ������Ʒ�깺��Ϣ
	@RequestMapping("/add")
	@SystemControllerLog(description = "��Ʒ��������")
	public @ResponseBody int add(Model model, FlowReturnmaterial returnmaterial) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");

		Emp emp = null;
		int upset = 0;
		int a = 0;
		if (null != returnmaterial.getUpset()) {
			upset = returnmaterial.getUpset();
		}
		// 3.��userInfo��ȡ��ѧ����Ա����Ϣ//������һΪ�գ�ע���п�
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			returnmaterial.setEmpid(emp.getId());
			returnmaterial.setApplyman(emp.getEmpname());
			returnmaterial.setApprovestatus(0);
		}
		if (upset == 0) {
			return flowreturnmaterialservice.insertSelective(returnmaterial);
		} else if (upset == 1) {
			int id = flowreturnmaterialservice.insertSelective(returnmaterial);
			returnmaterial.setId(id);
			a = upset(returnmaterial);
		}

		return a;
	}

	// �ύ
	public int upset(FlowReturnmaterial returnmaterial) {

		int b = 0;
		b =returnmaterial.getId();
		FlowReturnmaterial frm = flowreturnmaterialservice.selectByPrimaryKey(b);
		FlowAll fa = new FlowAll();
		fa.setFlowmodeltypeid(returnmaterial.getFlowmodeltypeid());
		fa.setEmpid(returnmaterial.getEmpid());
		fa.setStatus(0);
		fa.setApplyid(b);
		fa.setRemark(frm.getEmp().getEmpname() + "��" + frm.getCreatetime() + "�黹��" + frm.getCounts() + frm.getMaterial().getUnit()
				+ frm.getMaterial().getMaterialname());
		allMapper.insertSelective(fa);
		frm.setUpset(1);
		frm.setApprovestatus(1);
		flowreturnmaterialservice.updateByPrimaryKeySelective(frm);

		returnmaterial.setId(b);
		upsetapply(returnmaterial);
		return b;
	}

	// ��������
	public void upsetapply(FlowReturnmaterial returnmaterial) {
		FlowsModel f = new FlowsModel();
		// FlowApplyMaterial fam = new FlowApplyMaterial();
		f.setFlowmodeltypeid(returnmaterial.getFlowmodeltypeid());
		List<FlowsModel> fmList = modelService.selectSelective(f);
		for (int i = 0; i < fmList.size(); i++) {
			FlowsModel fm = fmList.get(i);

			FlowReturnmaterialdetail frmd = new FlowReturnmaterialdetail();
			frmd.setReturnmaterid(returnmaterial.getId());
			frmd.setEmpid(fm.getEmpid());
			if (i == 0) {
				frmd.setStatus(1);
			} else if (i == 1) {
				frmd.setStatus(2);// ״̬,0:δ���;1:�����;2:�ȴ����,Ĭ��Ϊ0,3:��ͨ��
			}
			frmd.setFlowmodelid(fm.getId());
			frmd.setRemark(fm.getRemark());
			flowreturnmaterialdeteailservice.insertSelective(frmd);
		}

	}

	// �޸���Ʒ�깺��Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���Ʒ�깺��Ϣ")
	public @ResponseBody int updata(Model model, FlowReturnmaterial returnmaterial) {
		int upset = 0;
		int a = 0;
		if (null != returnmaterial.getUpset()) {
			upset = returnmaterial.getUpset();
		}
		if (upset == 0) {
			flowreturnmaterialservice.updateByPrimaryKeySelective(returnmaterial);
		} else {
			a = upset(returnmaterial);
		}

		return a;
	}

	// �ύ��Ʒ�깺��Ϣ
	@RequestMapping(value = "/upset")
	@SystemControllerLog(description = "�ύ��Ʒ�깺��Ϣ")
	public @ResponseBody int upset(Model model, FlowReturnmaterial returnmaterial) {
		returnmaterial = flowreturnmaterialservice.selectByPrimaryKey(returnmaterial.getId());
		return upset(returnmaterial);
	}

	// ɾ����Ʒ�깺��Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����Ʒ�깺��Ϣ")
	public @ResponseBody int delete(Model model,@PathVariable("id") Integer id) {
		flowreturnmaterialservice.deleteByPrimaryKey(id);
		return 1;
	}
	
	// ������ѡ����������Ʒ��Json����
	@RequestMapping("/lookReturnMaterial")
	@SystemControllerLog(description = "������ѡ�깺��������������Json����")
	public @ResponseBody ResultMessage lookapplymaterial(Model model, FlowReturnmaterial returnmaterial) {
		ResultMessage rm = new ResultMessage();
		List<FlowReturnmaterialdetail> sList = new ArrayList<FlowReturnmaterialdetail>();
		sList = flowreturnmaterialdeteailservice.selectLength(returnmaterial.getId());

		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}
	

	@RequestMapping("/flowReturnmaterialYes")
	@SystemControllerLog(description = "ͬ������")
	public @ResponseBody int flowHolidayYes(FlowReturnmaterialdetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			holidaydetail.setStatus(1);//����ͨ��
			int count = flowreturnmaterialdeteailservice.updateByPrimaryKeySelective(holidaydetail);
//			updateHolidaydetial(holidaydetail.getHolidayid());
			FlowReturnmaterial fsupplement = flowreturnmaterialservice.selectByPrimaryKey(holidaydetail.getReturnmaterid());
			fsupplement.setApprovestatus(2);//1:��������;2:ͨ��;-1:��ͨ��
			flowreturnmaterialservice.updateByPrimaryKeySelective(fsupplement);
			FlowAll all = new FlowAll();
			all.setApplyid(fsupplement.getId());
			all.setFlowmodeltypeid(fsupplement.getFlowmodeltypeid());
			all = allMapper.selectBytypeid(all);
			all.setStatus(1);
			allMapper.updateByPrimaryKeySelective(all);
			
			count(fsupplement);
			
			return count;
		}
		
		
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	@RequestMapping("/flowReturnmaterialNo")
	@SystemControllerLog(description = "��ͬ������")
	public @ResponseBody int flowHolidayNo(FlowReturnmaterialdetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			
			holidaydetail.setStatus(3);//������ͨ��
			int count = flowreturnmaterialdeteailservice.updateByPrimaryKeySelective(holidaydetail);
			FlowReturnmaterial fsupplement = flowreturnmaterialservice.selectByPrimaryKey(holidaydetail.getReturnmaterid());
			fsupplement.setApprovestatus(2);//1:��������;2:ͨ��;-1:��ͨ��
			flowreturnmaterialservice.updateByPrimaryKeySelective(fsupplement);
			FlowAll all = new FlowAll();
			all.setApplyid(fsupplement.getId());
			all.setFlowmodeltypeid(fsupplement.getFlowmodeltypeid());
			all = allMapper.selectBytypeid(all);
			all.setStatus(2);
			allMapper.updateByPrimaryKeySelective(all);
			
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	// ���Ŀ��
	public void count(FlowReturnmaterial flowReceivematerial) {
		String empid = flowReceivematerial.getEmpid();

		flowReceivematerial = flowreturnmaterialservice.selectByPrimaryKey(flowReceivematerial.getId());
		Material material = materialService.selectByPrimaryKey(flowReceivematerial.getMaterialid());
		material.setStatus(1);
		material.setCounts(material.getCounts() + flowReceivematerial.getCounts());
		materialService.updateByPrimaryKeySelective(material);

		FinancePolicquantityset score = finaPolicQuantitySetService
				.selectByMaterialid(flowReceivematerial.getMaterialid());
		if (material.getCounts() <= score.getQuantity()) {
			score.setCountstatus(1);
		} else {
			score.setCountstatus(0);
		}
		int count = finaPolicQuantitySetService.updateByPrimaryKeySelective(score);

	}
}
