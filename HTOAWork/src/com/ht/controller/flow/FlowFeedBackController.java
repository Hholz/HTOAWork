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
	@SystemControllerLog(description = "��������η���ѧ���˷�ҳ��")
	public String flowFeedBackList(Model model) throws Exception {
		List<FlowsModeltype> modeltypes = modelTypeService.selectModelTypeByModelSelId("xstf");
		model.addAttribute("modeltypes", modeltypes);
		List<StudentFall> falls = fallMapper.selectFallAllList();
		model.addAttribute("falls", falls);
		return "flow/flowFeedBack";
	}

	@RequestMapping("/findFeedBackClassList")
	@SystemControllerLog(description = "������ѡ�������а༶json����")
	public @ResponseBody ResultMessage findFeedBackClassList(Model model, StudentFall fall) {
		ResultMessage rm = new ResultMessage();
		List<StudentClass> classes = new ArrayList<>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
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
	@SystemControllerLog(description = "������ѡ�༶�е�����ѧ��json����")
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
	@SystemControllerLog(description = "�ύѧ���˷�����")
	public @ResponseBody int addFlowFeedBack(FlowFeedBack feedBack) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
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
			all.setRemark("������" + emp.getEmpname() + "��" + studentClass.getClassname() + "���" + student.getStuname()
					+ "ͬѧ�����˷�����,�˷�����Ϊ:" + feedBack.getRemark());
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}

	private void addHolidayDetial(int classid, int count, int flowMOdelTypeid) {
		StudentClass studentClass = classMapper.selectByPrimaryKey(classid);// ��ȡд����ѧ���İ༶��Ϣ

		FlowsModel flowsModel = new FlowsModel();
		flowsModel.setFlowmodeltypeid(flowMOdelTypeid);
		List<FlowsModel> flowsModels = modelService.selectSelective(flowsModel);// ��ȡģ�������ڵ��е�����

		// �������뵥�������ڵ�
		for (int i = 0; i < flowsModels.size(); i++) {
			FlowFeedBackDetail feedBackDetail = new FlowFeedBackDetail();
			feedBackDetail.setFeedbackid(count);
			feedBackDetail.setFlowmodelid(flowsModels.get(i).getId());
			feedBackDetail.setStatus(0);// ������
			if (flowsModels.get(i).getRoleid().equals("kong")) {
				feedBackDetail.setEmpid("");
				feedBackDetail.setStatus(1);// ͨ��
			} else if (flowsModels.get(i).getRoleid().equals("rkls")) {
				feedBackDetail.setEmpid(studentClass.getTeacherId());
			} else {
				feedBackDetail.setEmpid(flowsModels.get(i).getEmpid());
			}
			detailMapper.insertSelective(feedBackDetail);
		}

		updateHolidaydetial(count);
	}

	// �޸������ڵ�״̬
	private void updateHolidaydetial(int count) {
		// �޸�
		List<FlowFeedBackDetail> feedBackDetails = detailMapper.selectAll(count);
		if (feedBackDetails.size() > 0) {
			FlowFeedBackDetail feedBackDetail = feedBackDetails.get(0);
			feedBackDetail.setStatus(2);// ������
			detailMapper.updateByPrimaryKeySelective(feedBackDetail);
			if (feedBackDetails.size() == 1) {
				financeId = feedBackDetails.get(0).getEmpid();
			}
		} else {
			FlowFeedBack feedBack = new FlowFeedBack();
			feedBack.setId(count);
			feedBack.setUpset(3);// ����ͨ��
			flowFeedBackService.updateByPrimaryKeySelective(feedBack);

			feedBack = flowFeedBackService.selectByPrimaryKey(count);

			// ��Ӳ���֧������
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
			
			// �޸��������ж�Ӧ������״̬Ϊ��ͨ��
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(feedBack.getFlowmodeltypeid());
			all.setStatus(1);
			allMapper.updateFlowAllStatus(all);
		}
	}

	@RequestMapping("/lookFeedBackDetial")
	@SystemControllerLog(description = "�鿴��������")
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
	@SystemControllerLog(description = "ͬ��ѧ���˷�����")
	public @ResponseBody int flowFeedBackYes(FlowFeedBackDetail feedBackDetail) throws Exception {
		if (null != feedBackDetail) {
			feedBackDetail.setStatus(1);// ����ͨ��
			int count = detailMapper.updateByPrimaryKeySelective(feedBackDetail);
			updateHolidaydetial(feedBackDetail.getFeedbackid());
			return count;
		}
		// ��studentΪ��ʱ�����е��������0
		return 0;
	}

	@RequestMapping("/flowFeedBackNo")
	@SystemControllerLog(description = "��ͬ��ѧ�����")
	public @ResponseBody int flowFeedBackNo(FlowFeedBackDetail feedBackDetail) throws Exception {
		if (null != feedBackDetail) {
			feedBackDetail.setStatus(3);// ��ͬ������
			int count = detailMapper.updateByPrimaryKeySelective(feedBackDetail);

			// �޸����뵥��״̬
			FlowFeedBack feedBack = new FlowFeedBack();
			feedBack.setId(feedBackDetail.getFeedbackid());
			feedBack.setUpset(2);// ����ʧ��,���뵥��Ч
			flowFeedBackService.updateByPrimaryKeySelective(feedBack);

			feedBack = flowFeedBackService.selectByPrimaryKey(feedBackDetail.getFeedbackid());

			// �޸��������ж�Ӧ������״̬Ϊδͨ��
			FlowAll all = new FlowAll();
			all.setApplyid(feedBackDetail.getFeedbackid());
			all.setFlowmodeltypeid(feedBack.getFlowmodeltypeid());
			all.setStatus(2);
			allMapper.updateFlowAllStatus(all);
			return count;
		}
		// ��studentΪ��ʱ�����е��������0
		return 0;
	}
}
