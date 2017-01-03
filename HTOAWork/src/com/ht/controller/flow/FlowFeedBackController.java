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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.annotation.SystemControllerLog;
import com.ht.mapper.finance.FinanceBalanceMapper;
import com.ht.mapper.flow.FlowAllMapper;
import com.ht.mapper.flow.FlowFeedBackDetailMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.mapper.student.StudentFallMapper;
import com.ht.mapper.student.StudentMapper;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.finance.FinanceBalance;
import com.ht.popj.flow.FlowAll;
import com.ht.popj.flow.FlowFeedBack;
import com.ht.popj.flow.FlowFeedBackDetail;
import com.ht.popj.flow.FlowsModel;
import com.ht.popj.flow.FlowsModeltype;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.flow.FlowFeedBackService;
import com.ht.service.flow.FlowModeltypeService;
import com.ht.service.flow.FlowsModelService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/flow/flowFeedBack")
public class FlowFeedBackController {

	@Autowired
	FlowModeltypeService modelTypeService;
	@Autowired
	StudentFallMapper fallMapper;
	@Autowired
	StudentClassMapper classMapper;
	@Autowired
	StudentMapper studMapper;
	@Autowired
	FlowFeedBackService flowFeedBackService;
	@Autowired
	FlowAllMapper allMapper;
	@Autowired
	FlowsModelService modelService;
	@Autowired
	FlowFeedBackDetailMapper detailMapper;
	@Autowired
	FinanceBalanceMapper balanceMapper;
	String financeId = "";

	@RequestMapping("/flowFeedBackList")
	@SystemControllerLog(description = "进入班主任发起学生退费页面")
	public String flowFeedBackList(Model model) throws Exception {
		List<FlowsModeltype> modeltypes = modelTypeService.selectModelTypeByModelSelId("xstf");
		model.addAttribute("modeltypes", modeltypes);
		List<StudentFall> falls = fallMapper.selectFallAllList();
		model.addAttribute("falls", falls);
		return "flow/flowFeedBack";
	}

	@RequestMapping("/findFeedBackClassList")
	@SystemControllerLog(description = "返回所选界别的所有班级json数据")
	public @ResponseBody ResultMessage findFeedBackClassList(Model model, StudentFall fall) {
		ResultMessage rm = new ResultMessage();
		List<StudentClass> classes = new ArrayList<>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}

		if (fall.getId() == null) {
			rm.setTotal(1);
			rm.setRows(classes);
			return rm;
		}
		StudentClass studentClass = new StudentClass();
		studentClass.setId(fall.getId());
		studentClass.setClteacherId(emp.getId());
		classes = classMapper.selectClassListByTercher(studentClass);
		rm.setTotal(classes.size());
		rm.setRows(classes);
		return rm;
	}

	@RequestMapping("/findFeedBackStudentList")
	@SystemControllerLog(description = "返回所选班级中的所有学生json数据")
	public @ResponseBody ResultMessage findFeedBackStudentList(Model model, StudentClass classes) {
		ResultMessage rm = new ResultMessage();
		List<Student> students = new ArrayList<>();
		if (classes.getId() == null) {
			rm.setTotal(1);
			rm.setRows(students);
			return rm;
		}
		int classid = classes.getId();
		students = studMapper.selectStudListFeedBack(classid);
		rm.setTotal(students.size());
		rm.setRows(students);
		return rm;
	}

	@RequestMapping("/addFlowFeedBack")
	@SystemControllerLog(description = "提交学生退费申请")
	public @ResponseBody int addFlowFeedBack(FlowFeedBack feedBack) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		if (null != feedBack) {
			feedBack.setEmpid(emp.getId());
			feedBack.setUpset(1);
			feedBack.setApplyMan(emp.getEmpname());
			int count = flowFeedBackService.insertSelective(feedBack);
			addHolidayDetial(feedBack.getClassid(), count, feedBack.getFlowmodeltypeid());

			StudentClass studentClass = classMapper.selectById(feedBack.getClassid());
			Student student = studMapper.selectByPrimaryKey(feedBack.getStudentid());

			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(feedBack.getFlowmodeltypeid());
			all.setEmpid(emp.getId());
			all.setStatus(0);
			all.setRemark("班主任" + emp.getEmpname() + "替" + studentClass.getClassname() + "班的" + student.getStuname()
					+ "同学发起退费申请,退费理由为:" + feedBack.getRemark());
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}

	private void addHolidayDetial(int classid, int count, int flowMOdelTypeid) {
		StudentClass studentClass = classMapper.selectByPrimaryKey(classid);// 获取写申请学生的班级信息

		FlowsModel flowsModel = new FlowsModel();
		flowsModel.setFlowmodeltypeid(flowMOdelTypeid);
		List<FlowsModel> flowsModels = modelService.selectSelective(flowsModel);// 获取模板审批节点中的数据

		// 新增申请单的审批节点
		for (int i = 0; i < flowsModels.size(); i++) {
			FlowFeedBackDetail feedBackDetail = new FlowFeedBackDetail();
			feedBackDetail.setFeedbackid(count);
			feedBackDetail.setFlowmodelid(flowsModels.get(i).getId());
			feedBackDetail.setStatus(0);// 不出理
			if (flowsModels.get(i).getRoleid().equals("kong")) {
				feedBackDetail.setEmpid("");
				feedBackDetail.setStatus(1);// 通过
			} else if (flowsModels.get(i).getRoleid().equals("rkls")) {
				feedBackDetail.setEmpid(studentClass.getTeacherId());
			} else {
				feedBackDetail.setEmpid(flowsModels.get(i).getEmpid());
			}
			detailMapper.insertSelective(feedBackDetail);
		}

		updateHolidaydetial(count);
	}

	// 修改审批节点状态
	private void updateHolidaydetial(int count) {
		// 修改
		List<FlowFeedBackDetail> feedBackDetails = detailMapper.selectAll(count);
		if (feedBackDetails.size() > 0) {
			FlowFeedBackDetail feedBackDetail = feedBackDetails.get(0);
			feedBackDetail.setStatus(2);// 待审批
			detailMapper.updateByPrimaryKeySelective(feedBackDetail);
			if (feedBackDetails.size() == 1) {
				financeId = feedBackDetails.get(0).getEmpid();
			}
		} else {
			FlowFeedBack feedBack = new FlowFeedBack();
			feedBack.setId(count);
			feedBack.setUpset(3);// 审批通过
			flowFeedBackService.updateByPrimaryKeySelective(feedBack);

			feedBack = flowFeedBackService.selectByPrimaryKey(count);

			// 添加财务支出数据
			FinanceBalance balance = new FinanceBalance();
			balance.setTypeId(13);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String str = sdf.format(date);
			balance.setOccurtime(str);
			balance.setCreateTime(str);
			balance.setMoney(Float.parseFloat(feedBack.getMoney() + ""));
			balance.setFinanceman(financeId);
			balance.setReportman(feedBack.getEmpid());
			balance.setStudId(feedBack.getStudentid());
			balance.setStatus(3);
			balanceMapper.insertSelective(balance);
			
			// 修改总览表中对应的数据状态为已通过
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(feedBack.getFlowmodeltypeid());
			all.setStatus(1);
			allMapper.updateFlowAllStatus(all);
		}
	}

	@RequestMapping("/lookFeedBackDetial")
	@SystemControllerLog(description = "查看审批详情")
	public @ResponseBody ResultMessage lookFeedBackDetial(FlowFeedBack feedBack) {
		ResultMessage rm = new ResultMessage();
		List<FlowFeedBackDetail> list = new ArrayList<>();
		if (null != feedBack) {
			list = detailMapper.selectByFeedBackId(feedBack.getId());
		}
		rm.setRows(list);
		rm.setTotal(list.size());
		return rm;
	}

	@RequestMapping("/flowFeedBackYes")
	@SystemControllerLog(description = "同意学生退费申请")
	public @ResponseBody int flowFeedBackYes(FlowFeedBackDetail feedBackDetail) throws Exception {
		if (null != feedBackDetail) {
			feedBackDetail.setStatus(1);// 审批通过
			int count = detailMapper.updateByPrimaryKeySelective(feedBackDetail);
			updateHolidaydetial(feedBackDetail.getFeedbackid());
			return count;
		}
		// 当student为空时，运行到这里，返回0
		return 0;
	}

	@RequestMapping("/flowFeedBackNo")
	@SystemControllerLog(description = "不同意学生请假")
	public @ResponseBody int flowFeedBackNo(FlowFeedBackDetail feedBackDetail) throws Exception {
		if (null != feedBackDetail) {
			feedBackDetail.setStatus(3);// 不同意申请
			int count = detailMapper.updateByPrimaryKeySelective(feedBackDetail);

			// 修改申请单的状态
			FlowFeedBack feedBack = new FlowFeedBack();
			feedBack.setId(feedBackDetail.getFeedbackid());
			feedBack.setUpset(2);// 审批失败,申请单无效
			flowFeedBackService.updateByPrimaryKeySelective(feedBack);

			feedBack = flowFeedBackService.selectByPrimaryKey(feedBackDetail.getFeedbackid());

			// 修改总览表中对应的数据状态为未通过
			FlowAll all = new FlowAll();
			all.setApplyid(feedBackDetail.getFeedbackid());
			all.setFlowmodeltypeid(feedBack.getFlowmodeltypeid());
			all.setStatus(2);
			allMapper.updateFlowAllStatus(all);
			return count;
		}
		// 当student为空时，运行到这里，返回0
		return 0;
	}
}
