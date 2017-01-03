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
import com.ht.popj.dailyWork.MaterialType;
import com.ht.popj.flow.FlowAll;
import com.ht.popj.flow.FlowApplyMaterial;
import com.ht.popj.flow.FlowApplyMaterialdetail;
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
import com.ht.service.flow.FlowsModelService;
import com.ht.service.sysSet.FinancePolicquantitysetService;
import com.ht.util.RandomGet;
import com.ht.util.ResultMessage;

/*
 * 操作物品申购
 */
@Controller
@RequestMapping("/flow/ApplyMaterial")
public class ApplyMaterialControler {
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
	FlowApplyMaterialService flowapplymaterialservice;
	@Autowired
	FlowApplyMaterialdetailService flowapplymaterialdetailservice;

	// 进入页面前查询物品类别
	@RequestMapping("/ApplyMaterialList")
	@SystemControllerLog(description = "进入物品申购页面")
	public String List(Model model) {
		List<MaterialType> materialtypelist = matypeservice.selectList();
		List<FlowsModeltype> flowmodeltypelist = fmm.selectModelTypeByModelSelId("bgypsg");
		model.addAttribute("mlist", materialtypelist);
		model.addAttribute("flowmodeltypelist", flowmodeltypelist);
		return "/flow/ApplyMaterialList";
	}

	// 返回所选类别的所有物品的Json数据
	@RequestMapping("/findmaterial")
	@SystemControllerLog(description = "返回所选类别的所有物品的Json数据")
	public @ResponseBody ResultMessage findmaterial(Model model, FinancePolicquantityset fp) {
		ResultMessage rm = new ResultMessage();
		List<FinancePolicquantityset> sList = new ArrayList<FinancePolicquantityset>();
		fp.setCountstatus(1);
		sList = finaPolicQuantitySetService.selectSelective(fp);

		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}

	// 返回所选类别的所有物品的Json数据
	@RequestMapping("/findless")
	@SystemControllerLog(description = "返回所选类别的所有物品的Json数据")
	public @ResponseBody ResultMessage findless(Model model, FinancePolicquantityset fp) {
		ResultMessage rm = new ResultMessage();
		List<FinancePolicquantityset> sList = new ArrayList<FinancePolicquantityset>();
		fp.setCountstatus(1);
		sList = finaPolicQuantitySetService.selectSelective(fp);

		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}

	// 新增物品申购信息
	@RequestMapping("/add")
	@SystemControllerLog(description = "物品申购新增")
	public @ResponseBody int add(Model model, FlowApplyMaterial applymaterial) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");

		Emp emp = null;
		int upset = 0;
		int a = 0;
		if (null != applymaterial.getUpset()) {
			upset = applymaterial.getUpset();
		}
		// 3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			applymaterial.setEmpid(emp.getId());
			applymaterial.setApplyman(emp.getEmpname());
		}
		if (upset == 0) {
			return flowapplymaterialservice.insertSelective(applymaterial);
		} else if (upset == 1) {
			int id = flowapplymaterialservice.insertSelective(applymaterial);
			applymaterial.setId(id);
			a = upset(applymaterial);
		}

		return a;
	}

	// 提交
	public int upset(FlowApplyMaterial applymaterial) {

		int b = 0;
		b =applymaterial.getId();
		FlowApplyMaterial fam = flowapplymaterialservice.selectByPrimaryKey(b);
		FlowAll fa = new FlowAll();
		fa.setFlowmodeltypeid(applymaterial.getFlowmodeltypeid());
		fa.setEmpid(applymaterial.getEmpid());
		fa.setStatus(0);
		fa.setApplyid(b);
		fa.setRemark(fam.getEmpname() + "于" + fam.getCreateTime() + "申购了" + fam.getCounts() + fam.getUnit()
				+ fam.getMaterialname());
		allMapper.insertSelective(fa);
		fam.setUpset(1);
		fam.setApprovalstatus(1);
		flowapplymaterialservice.updateByPrimaryKeySelective(fam);

		applymaterial.setId(b);
		upsetapply(applymaterial);
		return b;
	}

	// 进入审批
	public void upsetapply(FlowApplyMaterial applymaterial) {
		FlowsModel f = new FlowsModel();
		// FlowApplyMaterial fam = new FlowApplyMaterial();
		f.setFlowmodeltypeid(applymaterial.getFlowmodeltypeid());
		List<FlowsModel> fmList = modelService.selectSelective(f);
		for (int i = 0; i < fmList.size(); i++) {
			FlowsModel fm = fmList.get(i);

			FlowApplyMaterialdetail famd = new FlowApplyMaterialdetail();
			famd.setMaterialid(applymaterial.getId());
			famd.setEmpid(fm.getEmpid());
			// String empid = fm.getEmpid();
			// int e = usermapper.selectShiroByUserInfo(empid);
			// if (e == 0) {
			// famd.setEmpid("");
			// } else {
			// famd.setEmpid(e + "");
			// }
			if (i == 0) {
				famd.setStatus(1);
			} else if (i == 1) {
				famd.setStatus(2);// 状态,0:未完成;1:已完成;2:等待完成,默认为0,3:不通过
			}
			famd.setFlowmodelid(fm.getId());
			famd.setRemark(fm.getRemark());
			flowapplymaterialdetailservice.insertSelective(famd);
		}

	}

	// 修改物品申购信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改物品申购信息")
	public @ResponseBody int updata(Model model, FlowApplyMaterial applymaterial) {
		int upset = 0;
		int a = 0;
		if (null != applymaterial.getUpset()) {
			upset = applymaterial.getUpset();
		}
		if (upset == 0) {
			flowapplymaterialservice.updateByPrimaryKeySelective(applymaterial);
		} else {
			a = upset(applymaterial);
		}

		return a;
	}

	// 提交物品申购信息
	@RequestMapping(value = "/upset")
	@SystemControllerLog(description = "提交物品申购信息")
	public @ResponseBody int upset(Model model, FlowApplyMaterial applymaterial) {
		applymaterial = flowapplymaterialservice.selectByPrimaryKey(applymaterial.getId());
		return upset(applymaterial);
	}

	// 删除物品申购信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除物品申购信息")
	public @ResponseBody int delete(Model model,@PathVariable("id") Integer id) {
		flowapplymaterialservice.deleteByPrimaryKey(id);
		return 1;
	}
	
	// 返回所选类别的所有物品的Json数据
	@RequestMapping("/lookapplymaterial")
	@SystemControllerLog(description = "返回所选申购单所有审批单的Json数据")
	public @ResponseBody ResultMessage lookapplymaterial(Model model, FlowApplyMaterial fp) {
		ResultMessage rm = new ResultMessage();
		List<FlowApplyMaterialdetail> sList = new ArrayList<FlowApplyMaterialdetail>();
		FlowApplyMaterialdetail fam = new FlowApplyMaterialdetail();
		fam.setMaterialid(fp.getId());
		sList = flowapplymaterialdetailservice.selectlength(fp.getId());

		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}
}
