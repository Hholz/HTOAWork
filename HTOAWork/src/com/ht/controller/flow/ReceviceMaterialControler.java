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
 * 操作物品申购
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

	// 进入页面前查询物品类别
	@RequestMapping("/ReceviceMaterialList")
	@SystemControllerLog(description = "进入物品申领页面")
	public String List(Model model) {
		List<MaterialType> materialtypelist = matypeservice.selectList();
		List<FlowsModeltype> flowmodeltypelist = fmm.selectModelTypeByModelSelId("slwp");
		model.addAttribute("mlist", materialtypelist);
		model.addAttribute("flowmodeltypelist", flowmodeltypelist);
		return "/flow/ReceviceMaterialList";
	}

	// 返回所选类别的所有物品的Json数据
	@RequestMapping("/findmaterial")
	@SystemControllerLog(description = "返回所选类别的所有物品的Json数据")
	public @ResponseBody ResultMessage findmaterial(Model model, Material materialtypeid) {
		ResultMessage rm = new ResultMessage();
		List<Material> sList = new ArrayList<Material>();
		sList = materialService.selectByName(materialtypeid);

		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}

	@RequestMapping("/findmaterialdetail")
	@SystemControllerLog(description = "返回所选类别的所有物品的Json数据")
	public @ResponseBody ResultMessage findmaterialdetail(Model model, Material materialtypeid) {
		ResultMessage rm = new ResultMessage();
		materialtypeid = materialService.selectByPrimaryKey(materialtypeid.getId());

		rm.setTotal(1);
		rm.setRows(materialtypeid);
		return rm;
	}


	// 新增物品申购信息
	@RequestMapping("/add")
	@SystemControllerLog(description = "物品申领新增")
	public @ResponseBody int add(Model model, FlowReceivematerial receivematerial) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");

		Emp emp = null;
		int upset = 0;
		int a = 0;
		if (null != receivematerial.getUpset()) {
			upset = receivematerial.getUpset();
		}
		// 3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
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

	// 提交
	public int upset(FlowReceivematerial receivematerial) {

		int b = 0;
		b =receivematerial.getId();
		FlowReceivematerial frm = flowreceivematerialservice.selectByPrimaryKey(b);
		FlowAll fa = new FlowAll();
		fa.setFlowmodeltypeid(receivematerial.getFlowmodeltypeid());
		fa.setEmpid(receivematerial.getEmpid());
		fa.setStatus(0);
		fa.setApplyid(b);
		fa.setRemark(frm.getEmp().getEmpname() + "于" + frm.getCreateTime() + "申领了" + frm.getCounts() + frm.getMaterial().getUnit()
				+ frm.getMaterial().getMaterialname());
		allMapper.insertSelective(fa);
		frm.setUpset(1);
		frm.setApprovestatus(1);
		flowreceivematerialservice.updateByPrimaryKeySelective(frm);

		receivematerial.setId(b);
		upsetapply(receivematerial);
		return b;
	}

	// 进入审批
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
				frmd.setStatus(2);// 状态,0:未完成;1:已完成;2:等待完成,默认为0,3:不通过
			}
			frmd.setFlowmodelid(fm.getId());
			frmd.setRemark(fm.getRemark());
			flowreceivematerialdetailservice.insertSelective(frmd);
		}

	}

	// 修改物品申购信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改物品申购信息")
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

	// 提交物品申购信息
	@RequestMapping(value = "/upset")
	@SystemControllerLog(description = "提交物品申购信息")
	public @ResponseBody int upset(Model model, FlowReceivematerial receivematerial) {
		receivematerial = flowreceivematerialservice.selectByPrimaryKey(receivematerial.getId());
		return upset(receivematerial);
	}

	// 删除物品申购信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除物品申购信息")
	public @ResponseBody int delete(Model model,@PathVariable("id") Integer id) {
		flowreceivematerialservice.deleteByPrimaryKey(id);
		return 1;
	}
	
	// 返回所选类别的所有物品的Json数据
	@RequestMapping("/lookReceviceMaterial")
	@SystemControllerLog(description = "返回所选申购单所有审批单的Json数据")
	public @ResponseBody ResultMessage lookapplymaterial(Model model, FlowReceivematerial receivematerial) {
		ResultMessage rm = new ResultMessage();
		List<FlowReceivematerialdetail> sList = new ArrayList<FlowReceivematerialdetail>();
		sList = flowreceivematerialdetailservice.selectLength(receivematerial.getId());

		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}
	

	@RequestMapping("/flowRematerialYes")
	@SystemControllerLog(description = "同意申领")
	public @ResponseBody int flowHolidayYes(FlowReceivematerialdetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			holidaydetail.setStatus(1);//审批通过
			int count = flowreceivematerialdetailservice.updateByPrimaryKeySelective(holidaydetail);
//			updateHolidaydetial(holidaydetail.getHolidayid());
			FlowReceivematerial fsupplement = flowreceivematerialservice.selectByPrimaryKey(holidaydetail.getReceivematerid());
			fsupplement.setApprovestatus(2);//1:正在申请;2:通过;-1:不通过
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
		
		
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	@RequestMapping("/flowRematerialNo")
	@SystemControllerLog(description = "不同意申领")
	public @ResponseBody int flowHolidayNo(FlowReceivematerialdetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			
			holidaydetail.setStatus(3);//审批通过
			int count = flowreceivematerialdetailservice.updateByPrimaryKeySelective(holidaydetail);
			FlowReceivematerial fsupplement = flowreceivematerialservice.selectByPrimaryKey(holidaydetail.getReceivematerid());
			fsupplement.setApprovestatus(2);//1:正在申请;2:通过;-1:不通过
			flowreceivematerialservice.updateByPrimaryKeySelective(fsupplement);
			FlowAll all = new FlowAll();
			all.setApplyid(fsupplement.getId());
			all.setFlowmodeltypeid(fsupplement.getFlowmodeltypeid());
			all = allMapper.selectBytypeid(all);
			all.setStatus(2);
			allMapper.updateByPrimaryKeySelective(all);
			
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	// 更改库存
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
