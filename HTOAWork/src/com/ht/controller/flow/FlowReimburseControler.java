package com.ht.controller.flow;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.annotation.SystemControllerLog;
import com.ht.mapper.dailyWork.DepMapper;
import com.ht.mapper.flow.FlowAllMapper;
import com.ht.mapper.flow.FlowModeltypeMapper;
import com.ht.mapper.login.ShiroUserMapper;
import com.ht.popj.dailyWork.ApplyMaterial;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.MaterialType;
import com.ht.popj.finance.FinanceBalance;
import com.ht.popj.flow.FlowAll;
import com.ht.popj.flow.FlowApplyMaterial;
import com.ht.popj.flow.FlowApplyMaterialdetail;
import com.ht.popj.flow.FlowBaoxiao;
import com.ht.popj.flow.FlowBaoxiaodetail;
import com.ht.popj.flow.FlowReceivematerial;
import com.ht.popj.flow.FlowReceivematerialdetail;
import com.ht.popj.flow.FlowsModel;
import com.ht.popj.flow.FlowsModeltype;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.sysSet.FinancePolicquantityset;
import com.ht.service.dailyWork.ApplyMaterialService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.dailyWork.MaterialService;
import com.ht.service.dailyWork.MaterialTypeService;
import com.ht.service.finance.FinanceBalanceService;
import com.ht.service.flow.FlowApplyMaterialService;
import com.ht.service.flow.FlowApplyMaterialdetailService;
import com.ht.service.flow.FlowBaoxiaoService;
import com.ht.service.flow.FlowBaoxiaodetailService;
import com.ht.service.flow.FlowModelselService;
import com.ht.service.flow.FlowsModelService;
import com.ht.service.sysSet.FinancePolicquantitysetService;
import com.ht.util.RandomGet;
import com.ht.util.ResultMessage;

/*
 * 操作物品申购
 */
@Controller
@RequestMapping("/flow/Reimburse")
public class FlowReimburseControler {
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
	DepMapper depMapper;
	@Autowired
	ShiroUserMapper usermapper;
	@Autowired
	FlowAllMapper allMapper;
	@Autowired
	EmpService empservice;
	@Autowired
	FinancePolicquantitysetService finaPolicQuantitySetService;
	@Autowired
	FinanceBalanceService financeBalanceService;
	@Autowired
	FlowApplyMaterialService flowapplymaterialservice;
	@Autowired
	FlowApplyMaterialdetailService flowapplymaterialdetailservice;
	@Autowired
	FlowBaoxiaoService flowbaoxiaoservice;
	@Autowired
	FlowBaoxiaodetailService flowbaoxiaodetailservice;

	// 进入页面前查询物品类别
	@RequestMapping("/ApplyBaoxiaoList")
	@SystemControllerLog(description = "进入报销申请页面")
	public String List(Model model) {
		List<FlowsModeltype> flowmodeltypelist = fmm.selectModelTypeByModelSelId("bxlc");
		model.addAttribute("flowmodeltypelist", flowmodeltypelist);
		return "/flow/ApplyBaoXiaoList";
	}

	// 新增物品申购信息
	@RequestMapping("/add")
	@SystemControllerLog(description = "报销申请新增")
	public @ResponseBody int add(Model model, FlowBaoxiao applymaterial) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");

		Emp emp = null;
		int upset = 0;
		int a = 0;
		applymaterial.setApprovalStatus(0);
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
			return flowbaoxiaoservice.insertSelective(applymaterial);
		} else if (upset == 1) {
			int id = flowbaoxiaoservice.insertSelective(applymaterial);
			applymaterial.setId(id);
			a = upset(applymaterial);
		}

		return a;
	}

	// 提交
	public int upset(FlowBaoxiao applymaterial) {

		int b = 0;
		b =applymaterial.getId();
		FlowBaoxiao fam = flowbaoxiaoservice.selectByPrimaryKey(b);
		FlowAll fa = new FlowAll();
		fa.setFlowmodeltypeid(applymaterial.getFlowmodeltypeid());
		fa.setEmpid(applymaterial.getEmpid());
		fa.setStatus(0);
		fa.setApplyid(b);
		fa.setRemark(fam.getApplyman() + "于" + fam.getCreateTime() + "申请报销" + fam.getPrice()+"元");
		allMapper.insertSelective(fa);
		fam.setUpset(1);
		fam.setApprovalStatus(1);
		flowbaoxiaoservice.updateByPrimaryKeySelective(fam);

		applymaterial.setId(b);
		upsetapply(applymaterial);
		return b;
	}

	// 进入审批
	public void upsetapply(FlowBaoxiao applymaterial) {
		FlowsModel f = new FlowsModel();
		// FlowApplyMaterial fam = new FlowApplyMaterial();
		f.setFlowmodeltypeid(applymaterial.getFlowmodeltypeid());
		List<FlowsModel> fmList = modelService.selectSelective(f);
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if(null != userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		for (int i = 0; i < fmList.size(); i++) {
			FlowsModel fm = fmList.get(i);

			FlowBaoxiaodetail famd = new FlowBaoxiaodetail();
			famd.setBaoxiaoid(applymaterial.getId());
			famd.setEmpid(fm.getEmpid());
			famd.setStatus(0);
			if(fmList.get(i).getRoleid().equals("kong")){
				famd.setEmpid("");
				famd.setStatus(1);//通过/完成
			}else if(fmList.get(i).getRoleid().equals("bmld")){
				Dep dep = depMapper.selectByPrimaryKey(emp.getDepid());
				famd.setEmpid(dep.getChairman());
			}else {
				famd.setEmpid(fmList.get(i).getEmpid());
			}
			if(i == 1){
				famd.setStatus(2);
			}
			famd.setFlowmodelid(fm.getId());
			famd.setRemark(fm.getRemark());
			flowbaoxiaodetailservice.insertSelective(famd);
		}

	}

	// 修改物品申购信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改报销申请信息")
	public @ResponseBody int updata(Model model, FlowBaoxiao applymaterial) {
		int upset = 0;
		int a = 0;
		if (null != applymaterial.getUpset()) {
			upset = applymaterial.getUpset();
		}
		if (upset == 0) {
			flowbaoxiaoservice.updateByPrimaryKeySelective(applymaterial);
		} else {
			a = upset(applymaterial);
		}

		return a;
	}

	// 提交物品申购信息
	@RequestMapping(value = "/upset")
	@SystemControllerLog(description = "提交报销申请信息")
	public @ResponseBody int upset(Model model, FlowBaoxiao applymaterial) {
		applymaterial = flowbaoxiaoservice.selectByPrimaryKey(applymaterial.getId());
		return upset(applymaterial);
	}

	// 删除物品申购信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除报销申请信息")
	public @ResponseBody int delete(Model model,@PathVariable("id") Integer id) {
		flowbaoxiaoservice.deleteByPrimaryKey(id);
		return 1;
	}
	
	// 返回所选类别的所有物品的Json数据
	@RequestMapping("/lookbaoxiao")
	@SystemControllerLog(description = "返回所选报销单所有审批单的Json数据")
	public @ResponseBody ResultMessage lookapplymaterial(Model model, FlowBaoxiaodetail fp) {
		ResultMessage rm = new ResultMessage();
		List<FlowBaoxiaodetail> sList = new ArrayList<FlowBaoxiaodetail>();
		FlowBaoxiaodetail fam = new FlowBaoxiaodetail();
		fam.setBaoxiaoid(fp.getId());
		sList = flowbaoxiaodetailservice.selectlength(fp.getId());

		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}

	@RequestMapping("/flowbaoxiaoYes")
	@SystemControllerLog(description = "同意报销")
	public @ResponseBody int flowHolidayYes(FlowBaoxiaodetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			int id = holidaydetail.getId();
			holidaydetail.setStatus(1);//审批通过
			int count = flowbaoxiaodetailservice.updateByPrimaryKeySelective(holidaydetail);
//			FlowBaoxiaodetail fb = new FlowBaoxiaodetail();
			holidaydetail.setStatus(0);
			holidaydetail.setId(null);
			List<FlowBaoxiaodetail> weilist = flowbaoxiaodetailservice.selectSelective(holidaydetail);
			if(!weilist.isEmpty()){//审批未完成
				holidaydetail = weilist.get(0);
				holidaydetail.setStatus(2);
				flowbaoxiaodetailservice.updateByPrimaryKeySelective(holidaydetail);
				return 1;
			}
		
			//查找此审批单状态为2(待审批)
			FlowBaoxiaodetail flowBaoxiaodetail =new FlowBaoxiaodetail();
			flowBaoxiaodetail.setBaoxiaoid(holidaydetail.getBaoxiaoid());
			flowBaoxiaodetail.setStatus(2);
			List<FlowBaoxiaodetail> list = flowbaoxiaodetailservice.selectSelective(flowBaoxiaodetail);
			if(list.isEmpty()){//审批已结束
				FlowBaoxiao fsupplement = flowbaoxiaoservice.selectByPrimaryKey(holidaydetail.getBaoxiaoid());
				//申请单已通过
				fsupplement.setApprovalStatus(2);//1:正在申请;2:通过;-1:不通过
				flowbaoxiaoservice.updateByPrimaryKeySelective(fsupplement);
				FlowAll all = new FlowAll();
				all.setApplyid(fsupplement.getId());
				all.setFlowmodeltypeid(fsupplement.getFlowmodeltypeid());
				all.setStatus(1);
				allMapper.updateFlowAllStatus(all);
				money(id);//审批结束
			}
			
			return count;
		}
		
		
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	@RequestMapping("/flowbaoxiaoNo")
	@SystemControllerLog(description = "不同意报销")
	public @ResponseBody int flowHolidayNo(FlowBaoxiaodetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			
			holidaydetail.setStatus(3);//审批不通过
			int count = flowbaoxiaodetailservice.updateByPrimaryKeySelective(holidaydetail);
			FlowBaoxiao fsupplement = flowbaoxiaoservice.selectByPrimaryKey(holidaydetail.getBaoxiaoid());
			fsupplement.setApprovalStatus(2);//1:正在申请;2:通过;-1:不通过
			flowbaoxiaoservice.updateByPrimaryKeySelective(fsupplement);
			FlowAll all = new FlowAll();
			all.setApplyid(fsupplement.getId());
			all.setFlowmodeltypeid(fsupplement.getFlowmodeltypeid());
			all.setStatus(2);
			allMapper.updateFlowAllStatus(all);
			
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	public void money(int id) {
		FlowBaoxiaodetail flowapplymaterialdetail = new FlowBaoxiaodetail();
		
		flowapplymaterialdetail = flowbaoxiaodetailservice.selectByPrimaryKey(id);
		String empid = flowapplymaterialdetail.getEmpid();// 审批人
		int a = flowapplymaterialdetail.getId();
		FinanceBalance fb = new FinanceBalance();
		Emp e = empservice.selectByPrimaryKey(empid);
		float f = Float.parseFloat(flowapplymaterialdetail.getFlowBaoxiao().getPrice() + "");
		fb.setMoney(f);
		fb.setFinanceman(empid);
		fb.setReportman(flowapplymaterialdetail.getFlowBaoxiao().getEmpid());// 申请人

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String str = sdf.format(date);
		fb.setOccurtime(str);
		fb.setTypeId(12);/////////报销类别

		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		// 保存登录的session对象
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			if (fb.getFinanceman() == null || "".equals(fb.getFinanceman())) {
				fb.setFinanceman(emp.getId());
			}
		}
		// Date date = new Date();
		// fb.getTypeId();
		fb.setStatus(4);// 待付款
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str1 = sdf.format(date);
		fb.setCreateTime(str);
		fb.setSystime(str);
		int count1 = financeBalanceService.insert(fb);

	}
}
