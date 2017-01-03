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
import com.ht.service.flow.FlowsModelService;
import com.ht.service.sysSet.FinancePolicquantitysetService;
import com.ht.util.RandomGet;
import com.ht.util.ResultMessage;

/*
 * ������Ʒ�깺
 */
@Controller
@RequestMapping("/flow/ReceviceMaterial")
public class ReceviceMaterialControler {
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
	FlowReceivematerialService flowreceivematerialservice;
	@Autowired
	FlowReceivematerialdetailService flowreceivematerialdetailservice;

	// ����ҳ��ǰ��ѯ��Ʒ���
	@RequestMapping("/ReceviceMaterialList")
	@SystemControllerLog(description = "������Ʒ����ҳ��")
	public String List(Model model) {
		List<MaterialType> materialtypelist = matypeservice.selectList();
		List<FlowsModeltype> flowmodeltypelist = fmm.selectModelTypeByModelSelId("slwp");
		model.addAttribute("mlist", materialtypelist);
		model.addAttribute("flowmodeltypelist", flowmodeltypelist);
		return "/flow/ReceviceMaterialList";
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

	@RequestMapping("/findmaterialdetail")
	@SystemControllerLog(description = "������ѡ����������Ʒ��Json����")
	public @ResponseBody ResultMessage findmaterialdetail(Model model, Material materialtypeid) {
		ResultMessage rm = new ResultMessage();
		materialtypeid = materialService.selectByPrimaryKey(materialtypeid.getId());

		rm.setTotal(1);
		rm.setRows(materialtypeid);
		return rm;
	}


	// ������Ʒ�깺��Ϣ
	@RequestMapping("/add")
	@SystemControllerLog(description = "��Ʒ��������")
	public @ResponseBody int add(Model model, FlowReceivematerial receivematerial) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");

		Emp emp = null;
		int upset = 0;
		int a = 0;
		if (null != receivematerial.getUpset()) {
			upset = receivematerial.getUpset();
		}
		// 3.��userInfo��ȡ��ѧ����Ա����Ϣ//������һΪ�գ�ע���п�
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			receivematerial.setEmpid(emp.getId());
			receivematerial.setApprovestatus(0);
			receivematerial.setApplyman(emp.getEmpname());
		}
		if (upset == 0) {
			return flowreceivematerialservice.insertSelective(receivematerial);
		} else if (upset == 1) {
			int id = flowreceivematerialservice.insertSelective(receivematerial);
			receivematerial.setId(id);
			a = upset(receivematerial);
		}

		return a;
	}

	// �ύ
	public int upset(FlowReceivematerial receivematerial) {

		int b = 0;
		b =receivematerial.getId();
		FlowReceivematerial frm = flowreceivematerialservice.selectByPrimaryKey(b);
		FlowAll fa = new FlowAll();
		fa.setFlowmodeltypeid(receivematerial.getFlowmodeltypeid());
		fa.setEmpid(receivematerial.getEmpid());
		fa.setStatus(0);
		fa.setApplyid(b);
		fa.setRemark(frm.getEmp().getEmpname() + "��" + frm.getCreateTime() + "������" + frm.getCounts() + frm.getMaterial().getUnit()
				+ frm.getMaterial().getMaterialname());
		allMapper.insertSelective(fa);
		frm.setUpset(1);
		frm.setApprovestatus(1);
		flowreceivematerialservice.updateByPrimaryKeySelective(frm);

		receivematerial.setId(b);
		upsetapply(receivematerial);
		return b;
	}

	// ��������
	public void upsetapply(FlowReceivematerial receivematerial) {
		FlowsModel f = new FlowsModel();
		// FlowApplyMaterial fam = new FlowApplyMaterial();
		f.setFlowmodeltypeid(receivematerial.getFlowmodeltypeid());
		List<FlowsModel> fmList = modelService.selectSelective(f);
		for (int i = 0; i < fmList.size(); i++) {
			FlowsModel fm = fmList.get(i);

			FlowReceivematerialdetail frmd = new FlowReceivematerialdetail();
			frmd.setReceivematerid(receivematerial.getId());
			frmd.setEmpid(fm.getEmpid());
			if (i == 0) {
				frmd.setStatus(1);
			} else if (i == 1) {
				frmd.setStatus(2);// ״̬,0:δ���;1:�����;2:�ȴ����,Ĭ��Ϊ0,3:��ͨ��
			}
			frmd.setFlowmodelid(fm.getId());
			frmd.setRemark(fm.getRemark());
			flowreceivematerialdetailservice.insertSelective(frmd);
		}

	}

	// �޸���Ʒ�깺��Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���Ʒ�깺��Ϣ")
	public @ResponseBody int updata(Model model, FlowReceivematerial receivematerial) {
		int upset = 0;
		int a = 0;
		if (null != receivematerial.getUpset()) {
			upset = receivematerial.getUpset();
		}
		if (upset == 0) {
			flowreceivematerialservice.updateByPrimaryKeySelective(receivematerial);
		} else {
			a = upset(receivematerial);
		}

		return a;
	}

	// �ύ��Ʒ�깺��Ϣ
	@RequestMapping(value = "/upset")
	@SystemControllerLog(description = "�ύ��Ʒ�깺��Ϣ")
	public @ResponseBody int upset(Model model, FlowReceivematerial receivematerial) {
		receivematerial = flowreceivematerialservice.selectByPrimaryKey(receivematerial.getId());
		return upset(receivematerial);
	}

	// ɾ����Ʒ�깺��Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����Ʒ�깺��Ϣ")
	public @ResponseBody int delete(Model model,@PathVariable("id") Integer id) {
		flowreceivematerialservice.deleteByPrimaryKey(id);
		return 1;
	}
	
	// ������ѡ����������Ʒ��Json����
	@RequestMapping("/lookReceviceMaterial")
	@SystemControllerLog(description = "������ѡ�깺��������������Json����")
	public @ResponseBody ResultMessage lookapplymaterial(Model model, FlowReceivematerial receivematerial) {
		ResultMessage rm = new ResultMessage();
		List<FlowReceivematerialdetail> sList = new ArrayList<FlowReceivematerialdetail>();
		sList = flowreceivematerialdetailservice.selectLength(receivematerial.getId());

		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}
	

	@RequestMapping("/flowRematerialYes")
	@SystemControllerLog(description = "ͬ������")
	public @ResponseBody int flowHolidayYes(FlowReceivematerialdetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			holidaydetail.setStatus(1);//����ͨ��
			int count = flowreceivematerialdetailservice.updateByPrimaryKeySelective(holidaydetail);
//			updateHolidaydetial(holidaydetail.getHolidayid());
			FlowReceivematerial fsupplement = flowreceivematerialservice.selectByPrimaryKey(holidaydetail.getReceivematerid());
			fsupplement.setApprovestatus(2);//1:��������;2:ͨ��;-1:��ͨ��
			flowreceivematerialservice.updateByPrimaryKeySelective(fsupplement);
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
	
	@RequestMapping("/flowRematerialNo")
	@SystemControllerLog(description = "��ͬ������")
	public @ResponseBody int flowHolidayNo(FlowReceivematerialdetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			
			holidaydetail.setStatus(3);//����ͨ��
			int count = flowreceivematerialdetailservice.updateByPrimaryKeySelective(holidaydetail);
			FlowReceivematerial fsupplement = flowreceivematerialservice.selectByPrimaryKey(holidaydetail.getReceivematerid());
			fsupplement.setApprovestatus(2);//1:��������;2:ͨ��;-1:��ͨ��
			flowreceivematerialservice.updateByPrimaryKeySelective(fsupplement);
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
	public void count(FlowReceivematerial flowReceivematerial) {
		String empid = flowReceivematerial.getEmpid();

		flowReceivematerial = flowreceivematerialservice.selectByPrimaryKey(flowReceivematerial.getId());
		Material material = materialService.selectByPrimaryKey(flowReceivematerial.getMaterialid());
		material.setStatus(1);
		material.setCounts(material.getCounts() - flowReceivematerial.getCounts());
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
