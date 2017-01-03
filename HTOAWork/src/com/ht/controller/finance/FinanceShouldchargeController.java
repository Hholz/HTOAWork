package com.ht.controller.finance;

import java.util.ArrayList;
import java.util.Date;
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
import com.ht.popj.finance.FinanceTuitionset;
import com.ht.popj.finance.FinanceType;
import com.ht.popj.finance.Tuitionset;
import com.ht.popj.finance.Tuitionsetdetail;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.education.MajorService;
import com.ht.service.education.TermService;
import com.ht.service.finance.FinanceShouldchargeService;
import com.ht.service.finance.FinanceTuitionSetService;
import com.ht.service.finance.FinanceTypeService;
import com.ht.service.finance.TuitionsetService;
import com.ht.service.finance.TuitionsetdetailService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/finance/Shouldcharge")
public class FinanceShouldchargeController {

	@Autowired 
	FinanceShouldchargeService shouldchargeservice;
	
	@Autowired
	StudentInfoService studentInfoService;
	
	@Autowired 
	StudentService studentService;
	
	@Autowired 
	TermService termService;
	
	@Autowired 
	MajorService majorService;
	
	@Autowired 
	FinanceTypeService financeTypeService;
	
	@Autowired 
	TuitionsetdetailService tuitionsetdetailService;
	
	@Autowired
	TuitionsetService tuitionsetService;
	
	@RequestMapping("/tuitionIndex")
	@SystemControllerLog(description = "��������ѧ��ҳ��")
	public String tuitionIndex(Model model) throws Exception{
		//��ѯ���еĽ��
		List<StudentFall> falllist = studentInfoService.selectStudentFall();
		model.addAttribute("fallList", falllist);
		//��ѯ���е�רҵ
		List<EduMajor> majorlist = majorService.selectMajorAll();
		model.addAttribute("majorlist", majorlist);
		return "/finance/tuition";
	}
	
	
	@RequestMapping(value="/Json",method= { RequestMethod.POST })
	public @ResponseBody ResultMessage TuitionJson(int limit, int offset,Tuitionset stu){
		ResultMessage rm = new ResultMessage();
		List<Tuitionset> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(stu !=null){
			sList = tuitionsetService.selectByDynamicStatus(stu);
		}else{
			sList = tuitionsetService.selectByDynamicStatus(stu);
		}
		
		 // ȡ��ҳ��Ϣ
        PageInfo<Tuitionset> pageInfo = new PageInfo<Tuitionset>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//����ѧ��Ӧ�շ�
	@RequestMapping(value = "/addTuition",method= { RequestMethod.POST }) 
	@SystemControllerLog(description = "����ѧ��Ӧ�շ���Ϣ")
	public @ResponseBody int addTuition(Tuitionset tui) { 
		int count=0 ;
		//��ѯ��רҵ�������еİ༶
		StudentClass cla = new StudentClass();
		cla.setLevelId(tui.getFallid().toString());
		cla.setMajorId(tui.getEdudeptid());
		List<StudentClass> classlist = studentInfoService.selectStudentclass(cla);
		int classSize = classlist.size();
		if(classSize!=0){//�����רҵ�����а༶���༶��Ϊ0��
			for(int i=0;i<classSize;i++){
				//����ÿ���༶��ţ���ѯ�ð༶�������е�ѧ��
				int classid = classlist.get(i).getId();
				Student student = new Student();
				student.setClassid(classid);
				List<Student> studlist = studentService.selectByDynamic(student);
				int studSize = studlist.size();
				for(int j=0;j<studSize;j++){
					//��ÿ��ѧ�����һ��Ӧ�շѼ�¼
					FinanceShouldcharge fee = new FinanceShouldcharge();
					fee.setStuid(studlist.get(j).getId());//ѧ��ID
					fee.setClassid(studlist.get(i).getClassid());//ѧ���༶
					fee.setFallid(tui.getFallid());//ѧ�����
					fee.setTermid(tui.getTerm());//ѧ��
					fee.setMoney(Float.parseFloat(tui.getSum().toString()));//�ɷѽ��
					fee.setCreateTime(new Date());
					//����Ӧ�շ���Ϣ
					shouldchargeservice.insert(fee);
				}
			}
			//ͬʱ�޸�ѧ�����ú���ϸ��״̬��Ϊ2����������¼
			tui.setStatus(2);
			count = tuitionsetService.updateByPrimaryKeySelective(tui);
			
			//Tuitionsetdetail det = new Tuitionsetdetail();
			//det.setStatus(2);
			//det.setTuitionid(tui.getId());
			//count = tuitionsetdetailService.updateByTuitionKeySelective(det);
			
		}else{
			count=-1;//202�����רҵ��û��ѧ��
		}
		return count;
	}
	
}
