package com.ht.controller.finance;

import java.util.ArrayList;
import java.util.List;

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
import com.ht.popj.education.EduMajor;
import com.ht.popj.education.EduTerm;
import com.ht.popj.finance.FinanceShouldcharge;
import com.ht.popj.finance.Tuitionset;
import com.ht.popj.finance.Tuitionsetdetail;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.education.MajorService;
import com.ht.service.education.TermService;
import com.ht.service.finance.FinanceShouldchargeService;
import com.ht.service.finance.TuitionsetService;
import com.ht.service.finance.TuitionsetdetailService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;
import com.ht.util.TuitionData;


@Controller
@RequestMapping("finance/financefee")
public class FinanceFeeController {

	@Autowired
	StudentInfoService studentInfoService;
	
	@Autowired
	FinanceShouldchargeService shouldChangeService;
	
	@Autowired 
	MajorService majorService;
	
	@Autowired
	TermService termService;
	
	@Autowired 
	TuitionsetdetailService tuitionsetdetailService;
	
	@Autowired 
	TuitionsetService tuitionsetService;
	
	@RequestMapping("financeFeeIndex")
	@SystemControllerLog(description = "����ѧ��Ӧ�ɷѵ�(iframe)")
	public String financeFeeIndex(Model model) throws Exception{
		//��ѯ���еĽ��
		List<StudentFall> falllist = studentInfoService.selectStudentFall();
		model.addAttribute("fallList", falllist);
		//��ѯ���е�רҵ
		List<EduMajor> majorlist = majorService.selectMajorAll();
		model.addAttribute("majorlist", majorlist);
		//��ѯ���еİ༶
		List<StudentClass> classlist =  studentInfoService.selectallstduentclass();
		model.addAttribute("classlist", classlist);
		return "finance/finance_fee";
	}
	@RequestMapping("getData")
	@SystemControllerLog(description = "����ѧ��Ӧ�ɷѵ�(iframe)")
	public @ResponseBody TuitionData getData(Model model,FinanceShouldcharge charge) throws Exception{
		List<FinanceShouldcharge> sList = shouldChangeService.selectByDynamic(charge);
		float sumPay = 0;//�ѽ��ܽ��
		float sumNonpay = 0;//δ���ܽ��
		int countPay = 0;//�Ѹ�������
		int countNonpay = 0;//δ��������
		float money= 0;//Ӧ���ʽ�
		if(sList.size()>0){
			for(int i=0;i<sList.size();i++){
				if(sList.get(i).getPayment()==0){
					countPay++;
				}else{
					countNonpay++;
					sumPay = sList.get(i).getPayment();
				}
				money += sList.get(i).getMoney();
			}
			sumNonpay = money - sumPay;
		};
		//��ѯ�ɷѼ�¼
		TuitionData tuidata = new TuitionData();
		tuidata.setCountNonpay(countNonpay);
		tuidata.setCountPay(countPay);
		tuidata.setSumPay(sumPay);
		tuidata.setSumNonpay(sumNonpay);
		return tuidata;
	}
	@RequestMapping(value="feeList/{termid}")
	@SystemControllerLog(description = "��ѯѧ����ϸ��(json)")
	public @ResponseBody ResultMessage feeList(@PathVariable("termid")Integer term,Integer limit, Integer offset){
		ResultMessage rm = new ResultMessage();
		
		//��ѯ�ɷѼ�¼
		Tuitionset tui = new Tuitionset();
		tui.setTerm(term);
		Tuitionset tuition = tuitionsetService.selectByOnlyTuition(tui);
		Tuitionsetdetail detail = new Tuitionsetdetail();
		detail.setTuitionid(tuition.getId());//tuitionid=tuition��ID
		// ����ҳ��
		List<Tuitionsetdetail> sList = new ArrayList<Tuitionsetdetail>();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = tuitionsetdetailService.selectAllDetail(detail);
		// ȡ��ҳ��Ϣ
		PageInfo<Tuitionsetdetail> pageInfo = new PageInfo<Tuitionsetdetail>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping(value="paymentList",  method = { RequestMethod.POST })
	@SystemControllerLog(description = "��ѯѧӦ�ɷѱ�(json)")
	public @ResponseBody ResultMessage paymentList(int limit, int offset,Model model,FinanceShouldcharge pay){
		ResultMessage rm = new ResultMessage();
		List<FinanceShouldcharge> sList = new ArrayList<FinanceShouldcharge>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = shouldChangeService.selectByDynamic(pay);
		// ȡ��ҳ��Ϣ
		PageInfo<FinanceShouldcharge> pageInfo = new PageInfo<FinanceShouldcharge>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	@RequestMapping(value="findMajor/{fallid}",  method = { RequestMethod.PUT })
	@SystemControllerLog(description = "���ݽ��--��ѯרҵ")
	public @ResponseBody ResultMessage findMajor(@PathVariable("fallid") Integer fallid){
		ResultMessage rm = new ResultMessage();
		EduTerm term = new EduTerm();
		term.setFall_id(fallid);
		List<EduTerm> sList = termService.selectByAllMajor(term);
		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping(value="findClass/{majorid}",  method = { RequestMethod.PUT })
	@SystemControllerLog(description = "����רҵ--��ѯ�༶")
	public @ResponseBody ResultMessage findCalss(@PathVariable("majorid") Integer majorid){
		ResultMessage rm = new ResultMessage();
		StudentClass term = new StudentClass();
		term.setMajorId(majorid);
		List<StudentClass> sList = studentInfoService.selectStudentclass(term);
		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}
	
}