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
import org.springframework.web.bind.annotation.RequestMapping;

import com.ht.annotation.SystemControllerLog;
import com.ht.mapper.flow.FlowAllMapper;
import com.ht.mapper.flow.FlowFeedBackDetailMapper;
import com.ht.mapper.flow.FlowModeltypeMapper;
import com.ht.mapper.flow.FlowStudentApplyDetailMapper;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.finance.FinanceBalance;
import com.ht.popj.flow.FlowAll;
import com.ht.popj.flow.FlowApplyMaterial;
import com.ht.popj.flow.FlowApplyMaterialdetail;
import com.ht.popj.flow.FlowBaoxiaodetail;
import com.ht.popj.flow.FlowFeedBackDetail;
import com.ht.popj.flow.FlowHolidaydetail;
import com.ht.popj.flow.FlowStudentApplyDetail;
import com.ht.popj.flow.FlowReceivematerialdetail;
import com.ht.popj.flow.FlowReturnmaterialdetail;
import com.ht.popj.flow.FlowSupplementDetail;
import com.ht.popj.flow.Waitformaterial;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.dailyWork.MaterialService;
import com.ht.service.finance.FinanceBalanceService;
import com.ht.service.finance.FinanceTypeService;
import com.ht.service.flow.FlowApplyMaterialService;
import com.ht.service.flow.FlowApplyMaterialdetailService;
import com.ht.service.flow.FlowBaoxiaoService;
import com.ht.service.flow.FlowBaoxiaodetailService;
import com.ht.service.flow.FlowHolidaydetailService;
import com.ht.service.flow.FlowReceivematerialService;
import com.ht.service.flow.FlowReceivematerialdetailService;
import com.ht.service.flow.FlowReturnmaterialService;
import com.ht.service.flow.FlowReturnmaterialdetailService;
import com.ht.service.flow.FlowSupplementDetailService;
import com.ht.service.flow.FlowsModelService;
import com.ht.service.flow.WaitformaterialService;
import com.ht.service.sysSet.FinancePolicquantitysetService;

@Controller
@RequestMapping("/flow/approveapplymaterial")
public class ApproveApplyMaterialController {
	@Autowired
	FlowApplyMaterialService flowapplymaterialservice;
	@Autowired
	FlowModeltypeMapper fmm;
	@Autowired
	FlowApplyMaterialdetailService flowapplymaterialdetailservice;
	@Autowired
	FlowsModelService modelService;
	@Autowired
	FinanceBalanceService financeBalanceService;
	@Autowired
	MaterialService materialService;
	@Autowired
	EmpService empservice;
	@Autowired
	FinanceTypeService typeService;
	@Autowired
	FinancePolicquantitysetService finaPolicQuantitySetService;
	@Autowired
	FlowHolidaydetailService holidaydetailService;
	@Autowired
	WaitformaterialService waitformaterialService;
	@Autowired
	FlowFeedBackDetailMapper feedBackDetialMapper;
	@Autowired
	FlowSupplementDetailService flowsupplementdetailservice;
	@Autowired
	FlowReceivematerialService flowreceivematerialservice;
	@Autowired
	FlowReceivematerialdetailService flowreceivematerialdetailservice;
	@Autowired
	FlowReturnmaterialService flowreturnmaterialservice;
	@Autowired
	FlowReturnmaterialdetailService flowreturnmaterialdeteailservice;
	@Autowired
	FlowBaoxiaoService flowbaoxiaoservice;
	@Autowired
	FlowBaoxiaodetailService flowbaoxiaodetailservice;

	@Autowired
	FlowAllMapper allMapper;
	@Autowired
	FlowStudentApplyDetailMapper applyDetailMapper;
	String empid = "";
	Integer studid;

	// 进入页面前查询物品类别
	@RequestMapping("/ApplyMaterialList")
	@SystemControllerLog(description = "进入我的审批任务页面")
	public String List(Model model) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		List<FlowApplyMaterialdetail> famdlist = new ArrayList<FlowApplyMaterialdetail>();
		List<FlowHolidaydetail> holidaydetails = new ArrayList<>();
		List<FlowStudentApplyDetail> applyDetails = new ArrayList<>();
		List<FlowFeedBackDetail> feedBackDetails = new ArrayList<>();
		List<FlowHolidaydetail> EmpHolidayList = new ArrayList<>();
		List<FlowSupplementDetail> SupplementdeatilList = new ArrayList<>();
		List<FlowReceivematerialdetail> receivemateriallist = new ArrayList<>();
		List<FlowReturnmaterialdetail> returnmateriallist = new ArrayList<>();
		List<FlowBaoxiaodetail> Reimburselist = new ArrayList<>();
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			empid = emp.getId();

			famdlist = flowapplymaterialdetailservice.selectTaskList(emp.getId());
			holidaydetails = holidaydetailService.selectByPrimaryKey(empid);
			EmpHolidayList = holidaydetailService.selectEmpHolidayList(empid);
			feedBackDetails = feedBackDetialMapper.selectFeedBackDetail(empid);
			SupplementdeatilList = flowsupplementdetailservice.selectSupplementList(empid);
			applyDetails = applyDetailMapper.selectStudentApply(empid);
			receivemateriallist = flowreceivematerialdetailservice.selectReceiveMateriallist(empid);
			returnmateriallist = flowreturnmaterialdeteailservice.selectReturnMateriallist(empid);
			Reimburselist = flowbaoxiaodetailservice.selectBaoxiaodetaillist(empid);
		}
		// 办公用品申购审批
		model.addAttribute("famdlist", famdlist);

		// 办公用品申领审批
		model.addAttribute("receivemateriallist", receivemateriallist);

		// 办公用品归还审批
		model.addAttribute("returnmateriallist", returnmateriallist);

		// 员工请假审批
		model.addAttribute("EmpHolidayList", EmpHolidayList);

		// 员工补签审批
		model.addAttribute("SupplementdeatilList", SupplementdeatilList);

		// 员工报销审批
		model.addAttribute("Reimburselist", Reimburselist);
		
		//发起学生退费申请审批
		model.addAttribute("feedBackDetails", feedBackDetails);

		// 学生请假申请审批
		model.addAttribute("holidaydetails", holidaydetails);

		// 学生调班申请审批
		model.addAttribute("applyDetails", applyDetails);

		return "/flow/ApproveApplyMaterialList";
	}

	// 审批后的提交
	@RequestMapping(value = "/over")
	@SystemControllerLog(description = "审批后的提交")
	public String updata(Model model, FlowApplyMaterialdetail flowapplymaterialdetail) {
		int a = flowapplymaterialdetail.getId();
		int b = 0;
		int tr = flowapplymaterialdetail.getFlowmodelid();
		String remark = flowapplymaterialdetail.getRemark();
		// 申购单
		FlowAll fa = new FlowAll();

		FlowApplyMaterialdetail f = flowapplymaterialdetailservice.selectByPrimaryKey(a);
		FlowApplyMaterial flowApplyMaterial = flowapplymaterialservice.selectByPrimaryKey(f.getMaterialid());
		List<FlowApplyMaterialdetail> famdlist = flowapplymaterialdetailservice.selectTaskList(empid);
		// 此申购单的审批单
		List<FlowApplyMaterialdetail> flist = flowapplymaterialdetailservice.selectSelective(f);
		for (int i = 0; i < flist.size(); i++) {
			flowapplymaterialdetail = flist.get(i);
			if (flowapplymaterialdetail.getStatus() == 1) {
				continue;
			}
			if (tr == 1) {// 通过为1
				if (flowapplymaterialdetail.getStatus() == 2) {
					if (i == flist.size() - 1) {
						flowapplymaterialdetail = flowapplymaterialdetailservice
								.selectByPrimaryKey(flowapplymaterialdetail.getId());
						finish(flowapplymaterialdetail);
					}
					flowapplymaterialdetail.setStatus(1);// 审批单已通过
					// money(flowapplymaterialdetail);
					// count(flowapplymaterialdetail);
					flowapplymaterialdetailservice.updateByPrimaryKeySelective(flowapplymaterialdetail);
					b = 1;

					continue;
				}
				if (b == 1) {
					flowapplymaterialdetail.setStatus(2);// 审批单等待审批
					flowapplymaterialdetailservice.updateByPrimaryKeySelective(flowapplymaterialdetail);
					break;
				}
			} else {
				flowApplyMaterial.setApprovalstatus(-1);// 申购单不通过
				flowapplymaterialservice.updateByPrimaryKeySelective(flowApplyMaterial);
				flowapplymaterialdetail.setStatus(3);// 审批单不通过
				flowapplymaterialdetail.setRemark(remark);
				flowapplymaterialdetailservice.updateByPrimaryKeySelective(flowapplymaterialdetail);

				fa.setApplyid(flowApplyMaterial.getId());

				fa.setFlowmodeltypeid(flowApplyMaterial.getFlowmodeltypeid());
				fa = allMapper.selectBytypeid(fa);
				fa.setStatus(2);
				allMapper.updateByPrimaryKeySelective(fa);
				break;
			}

		}

		return "redirect:/flow/approveapplymaterial/ApplyMaterialList";
	}

	public void finish(FlowApplyMaterialdetail flowapplymaterialdetail) {
		FlowAll fa = new FlowAll();
		fa.setApplyid(flowapplymaterialdetail.getFlowApplyMaterial().getId());
		fa.setFlowmodeltypeid(flowapplymaterialdetail.getFlowApplyMaterial().getFlowmodeltypeid());
		fa = allMapper.selectBytypeid(fa);
		fa.setStatus(1);

		allMapper.updateFlowAllStatus(fa);
		FlowApplyMaterial flam = new FlowApplyMaterial();
		flam.setId(flowapplymaterialdetail.getFlowApplyMaterial().getId());
		flam.setApprovalstatus(2);
		flowapplymaterialservice.updateByPrimaryKeySelective(flam);

		Waitformaterial waitformaterial = new Waitformaterial();
		waitformaterial.setCount(flowapplymaterialdetail.getFlowApplyMaterial().getCounts());
		waitformaterial.setApplyman(flowapplymaterialdetail.getFlowApplyMaterial().getEmpid());
		waitformaterial.setMaterialid(flowapplymaterialdetail.getMaterial().getId());
		waitformaterial.setApplystatus(0);
		waitformaterial.setApplymaterialid(flowapplymaterialdetail.getMaterialid());
		int a = waitformaterialService.insertSelective(waitformaterial);
		if (a != 0) {
			money(flowapplymaterialdetail);
		}

	}

	// 财务支出
	public void money(FlowApplyMaterialdetail flowapplymaterialdetail) {
		String empid = flowapplymaterialdetail.getEmpid();// 审批人
		int a = flowapplymaterialdetail.getId();
		flowapplymaterialdetail = flowapplymaterialdetailservice.selectByPrimaryKey(a);
		FinanceBalance fb = new FinanceBalance();
		Emp e = empservice.selectByPrimaryKey(empid);
		float f = Float.parseFloat(flowapplymaterialdetail.getMaterial().getPrice() + "");
		int count = flowapplymaterialdetail.getFlowApplyMaterial().getCounts();
		float money = f * count;// 数量*单价

		System.out.println(money);
		fb.setMoney(money);
		fb.setFinanceman(empid);
		fb.setReportman(flowapplymaterialdetail.getFlowApplyMaterial().getEmpid());// 申请人

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String str = sdf.format(date);
		fb.setOccurtime(str);
		fb.setTypeId(12);/////////

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
