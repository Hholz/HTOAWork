package com.ht.controller.finance;

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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.finance.FinanceBalance;
import com.ht.popj.finance.FinanceFeestandard;
import com.ht.popj.finance.FinanceShouldcharge;
import com.ht.popj.finance.FinanceType;
import com.ht.popj.flow.FlowComputerApply;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.finance.FinanceBalanceService;
import com.ht.service.finance.FinanceFeeStandardService;
import com.ht.service.finance.FinanceShouldchargeService;
import com.ht.service.finance.FinanceTypeService;
import com.ht.service.flow.FlowComputerApplyService;
import com.ht.service.student.StudentService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/finance/financebalance")
public class FinanceBalanceController {

	@Autowired
	FinanceBalanceService financeBalanceService;

	@Autowired
	EmpService empService;

	@Autowired
	FinanceTypeService typeService;

	@Autowired
	FinanceShouldchargeService shouldChangeService;

	@Autowired
	FinanceFeeStandardService feeStandardService;

	@Autowired
	StudentService studentService;

	@Autowired
	FinanceShouldchargeService financeShouldchargeService;

	@Autowired
	FlowComputerApplyService flowComputerApplyService;

	@RequestMapping("balanceIndex")
	@SystemControllerLog(description = "������֧������Ϣҳ��")
	public String balanceIndex(Model model) {
		// ȡsessionʵ��
		// 1.��ȡsession����
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		// �����¼��session����
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		model.addAttribute("emp", emp);
		List<Emp> empList = empService.selectEmp(null);
		model.addAttribute("empList", empList);

		FinanceType type = new FinanceType();
		// type.setIdentifying(1);
		List<FinanceType> typeList = typeService.selectByDynamic(type);
		model.addAttribute("typeList", typeList);
		return "finance/finance_balance";
	}

	@RequestMapping("tuitionIndex")
	@SystemControllerLog(description = "����ѧ�ӷ��ս���Ϣҳ��")
	public String tuitionIndex(Model model) {
		// ȡsessionʵ��
		// 1.��ȡsession����
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		// �����¼��session����
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		model.addAttribute("emp", emp);
		List<FinanceShouldcharge> studentList = shouldChangeService.selectAllList(null);
		model.addAttribute("studentList", studentList);

		List<Student> stuList = studentService.selectStudentAll();
		model.addAttribute("stuList", stuList);

		List<FinanceFeestandard> typeList = feeStandardService.selectByDynamic(null);
		model.addAttribute("typeList", typeList);
		return "finance/finance_tuition";
	}

	@RequestMapping(value = "studTuitionList", method = { RequestMethod.POST })
	@SystemControllerLog(description = "��ѯѧ�ӷ���ȡ��Ϣ��(json)")
	public @ResponseBody ResultMessage studTuitionList(int limit, int offset, Model model, FinanceBalance balance) {
		ResultMessage rm = new ResultMessage();
		List<FinanceBalance> sList = new ArrayList<FinanceBalance>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = financeBalanceService.selectByDynamicOfStudent(balance);
		// ȡ��ҳ��Ϣ
		PageInfo<FinanceBalance> pageInfo = new PageInfo<FinanceBalance>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}

	@RequestMapping("balanceList")
	@SystemControllerLog(description = "��ѯ��֧�������Ϣ(json)")
	public @ResponseBody ResultMessage balanceList(int limit, int offset, Model model, FinanceBalance balance) {
		ResultMessage rm = new ResultMessage();
		List<FinanceBalance> sList = new ArrayList<FinanceBalance>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = financeBalanceService.selectByDynamic(balance);
		// ȡ��ҳ��Ϣ
		PageInfo<FinanceBalance> pageInfo = new PageInfo<FinanceBalance>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}

	@RequestMapping("balanceInfo")
	@SystemControllerLog(description = "��ѯ��֧�������ϢbalanceInfo(json)")
	public @ResponseBody ResultMessage balanceInfo(int limit, int offset, Model model, FinanceBalance balance) {
		// System.out.println(balance);
		ResultMessage rm = new ResultMessage();
		List<FinanceBalance> sList = new ArrayList<FinanceBalance>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = financeBalanceService.selectByDynamicOfBalance(balance);
		// ȡ��ҳ��Ϣ
		PageInfo<FinanceBalance> pageInfo = new PageInfo<FinanceBalance>(sList);
		// System.out.println("��������==="+sList.size());
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}

	@RequestMapping("/addBalance")
	@SystemControllerLog(description = "������֧��Ϣ")
	public @ResponseBody int addBalance(Model model, FinanceBalance balance, int chargeid) {
		// 1.��ȡsession����
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		// �����¼��session����
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			if (balance.getFinanceman() == null || "".equals(balance.getFinanceman())) {
				balance.setFinanceman(emp.getId());
			}
		}
		// ��ȡϵͳ��ǰʱ��
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		// ��Ӵ���ʱ��
		if (balance.getOccurtime() == null || "".equals(balance.getOccurtime())) {
			balance.setOccurtime(sdf.format(date));
		}
		balance.setCreateTime(str);
		// �����ϵͳʱ��
		balance.setSystime(str);
		balance.setStatus(0);
		balance.setTypeId(11);
		int count = financeBalanceService.insert(balance);
		if (balance.getTypeId() == 11) {
			FinanceShouldcharge fs = financeShouldchargeService.selectById(chargeid);
			FinanceShouldcharge f = new FinanceShouldcharge();
			if (fs.getMoney() == (fs.getPayment() + balance.getMoney())) {
				f.setIspay(1);// �ѽ���
				f.setPayment(fs.getPayment() + balance.getMoney());
				if (null != fs.getTermname() && !"".equals(fs.getTermname()) && "��һѧ��".equals(fs.getTermname())) {
					FlowComputerApply fca = new FlowComputerApply();
					fca.setStudid(balance.getStudId());
					flowComputerApplyService.insertSelective(fca);
				}
			} else if (fs.getMoney() > (fs.getPayment() + balance.getMoney())) {
				f.setIspay(3);// δ����
				f.setPayment(fs.getPayment() + balance.getMoney());
			}
			f.setId(chargeid);
			financeShouldchargeService.updateByPrimaryKey(f);
		}
		return count;
	}

	@RequestMapping("/addBalance2")
	@SystemControllerLog(description = "������֧��Ϣ") // ���˽�ѧ������Ĳ������
	public @ResponseBody int addBalance2(FinanceBalance balance) {
		// 1.��ȡsession����
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		// �����¼��session����
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
			if (balance.getFinanceman() == null || "".equals(balance.getFinanceman())) {
				balance.setFinanceman(emp.getId());
			}
		}
		Date date = new Date();
		balance.getTypeId();
		FinanceType ft = typeService.selectByPrimaryKey(balance.getTypeId());
		if (ft.getIdentifying() == 1) {
			balance.setStatus(5);// ������
		} else {
			balance.setStatus(6);// ���տ�
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		balance.setCreateTime(str);
		balance.setSystime(str);
		int count = financeBalanceService.insert(balance);
		return count;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���֧��Ϣ��")
	public @ResponseBody int updateBalance(Model model, FinanceBalance balance) {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		balance.setUpdateTime(str);
		int count = financeBalanceService.updateByPrimaryKeySelective(balance);
		return count;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	@SystemControllerLog(description = "�޸���֧��Ϣ��,ȷ���տ�͸������")
	public @ResponseBody int updateBalance2(FinanceBalance balance) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		balance.setUpdateTime(sdf.format(new Date()));
		if (balance.getStatus() == 4 || balance.getStatus() == 6) {// ���տ��Ϊ���տ�
			balance.setStatus(0);
		} else if (balance.getStatus() == 3 || balance.getStatus() == 5) {// �������Ϊ�Ѹ���
			balance.setStatus(2);
		}
		int count = financeBalanceService.updateByPrimaryKeySelective(balance);
		return count;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����֧��Ϣ��")
	public @ResponseBody int deleteBalance(@PathVariable("id") Integer id) {
		int count = financeBalanceService.deleteByPrimaryKey(id);
		return count;
	}

	@RequestMapping("feeIndex")
	@SystemControllerLog(description = "�鿴ѧ�ӷ��ս���ϸ��")
	public String feeIndex(Model model) {

		List<FinanceFeestandard> typeList = feeStandardService.selectByDynamic(null);
		model.addAttribute("typeList", typeList);
		return "finance/finance_tuitioniframe";
	}

	@RequestMapping(value = "statisticsAllFee", method = { RequestMethod.POST })
	@SystemControllerLog(description = "��ѯѧ�ӷ���ȡ��Ϣ��(json)")
	public String statisticsAllFee(int limit, int offset, Model model, FinanceBalance balance) {
		ResultMessage rm = new ResultMessage();
		List<FinanceBalance> sList = new ArrayList<FinanceBalance>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = financeBalanceService.statisticsAllFee(balance);
		// ȡ��ҳ��Ϣ
		PageInfo<FinanceBalance> pageInfo = new PageInfo<FinanceBalance>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return "";
	}

	@RequestMapping("/batchPay")
	@SystemControllerLog(description = "�����ɷѲ���")
	public @ResponseBody int batchPay(String ids, String moneys, String stuids,String termnames, int typeid) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = userInfo.getEmp();
		emp = userInfo.getEmp();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);

		String[] cids = ids.split(",");
		String[] sids = stuids.split(",");
		String[] mids = moneys.split(",");
		String[] terms = termnames.split(",");
		FinanceBalance balance = new FinanceBalance();
		int count = 0;
		for (int i = 0; i < mids.length; i++) {
			balance.setFinanceman(emp.getId());
			balance.setOccurtime(str);
			balance.setCreateTime(str);
			balance.setSystime(str);
			balance.setStatus(0);
			balance.setTypeId(11);
			balance.setMoney(Float.parseFloat(mids[i]));
			balance.setStudId(Integer.parseInt(sids[i]));
			balance.setTypeId(typeid);
			count = financeBalanceService.insert(balance);
			// ��״̬��1Ϊ�ѽ���
			FinanceShouldcharge f = new FinanceShouldcharge();
			f.setIspay(1);
			f.setPayment(Float.parseFloat(mids[i]));
			f.setId(Integer.parseInt(cids[i]));
			financeShouldchargeService.updateByPrimaryKey(f);
			if (null != terms[i] && !"".equals(terms[i]) && "��һѧ��".equals(terms[i])) {
				FlowComputerApply fca = new FlowComputerApply();
				fca.setStudid(balance.getStudId());
				flowComputerApplyService.insertSelective(fca);
			}
		}
		return count;
	}

}
